/**
 * 
 */
package com.mljr.sync.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.mljr.constant.BasicConstant;
import com.mljr.rabbitmq.RabbitmqClient;
import com.mljr.redis.RedisClient;
import com.mljr.spider.dao.YyUserAddressBookDao;
import com.mljr.spider.dao.YyUserCallRecordDao;
import com.mljr.spider.model.YyUserAddressBookDo;
import com.mljr.spider.model.YyUserCallRecordDo;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.ucloud.umq.common.ServiceConfig;

import redis.clients.jedis.Jedis;

/**
 * @author Ckex zha </br>
 *         2016年11月29日,下午4:08:29
 *
 */
@Service
public class MobileService {

	protected static transient Logger logger = LoggerFactory.getLogger(MobileService.class);

	private static final int LIMIT = 50;

	@Autowired
	private YyUserAddressBookDao yyUserAddressBookDao;

	@Autowired
	private YyUserCallRecordDao yyUserCallRecordDao;

	@Autowired
	private RedisClient client;

	public void syncMobile() throws Exception {
		final Channel channel = RabbitmqClient.newChannel();
		try {
			Function<String, Boolean> function = new Function<String, Boolean>() {

				@Override
				public Boolean apply(String mobile) {
					return sentMobile(channel, mobile);
				}
			};
			syncYyUserAddressBook(function);
			syncYyUserCallRecord(function);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (channel != null) {
				channel.close();
			}
		}
	}

	private void syncYyUserCallRecord(Function<String, Boolean> function) {
		String key = Joiner.on("-").join(BasicConstant.MOBILE_YY_USER_CALL_RECORD, BasicConstant.LAST_ID);
		List<YyUserCallRecordDo> result = listYyUserCallRecord(key);
		if (result == null || result.isEmpty()) {
			logger.info("result empty .");
			return;
		}

		for (YyUserCallRecordDo yyUserCallRecordDo : result) {
			if (function.apply(yyUserCallRecordDo.getNumber())) {
				setLastId(key, yyUserCallRecordDo.getId());
				continue;
			}
			logger.warn("sent to mq error ." + yyUserCallRecordDo.toString());
			break;
		}

	}

	private List<YyUserCallRecordDo> listYyUserCallRecord(String key) {
		long lastId = getLastId(key);
		return yyUserCallRecordDao.listById(lastId, LIMIT);
	}

	private List<YyUserAddressBookDo> listYyUserAddressBook(String key) {
		long lastId = getLastId(key);
		return yyUserAddressBookDao.listById(lastId, LIMIT);
	}

	private void syncYyUserAddressBook(Function<String, Boolean> function) {
		String key = Joiner.on("-").join(BasicConstant.MOBILE_YY_USER_ADDRESS_BOOK, BasicConstant.LAST_ID);
		List<YyUserAddressBookDo> result = listYyUserAddressBook(key);
		if (result == null || result.isEmpty()) {
			logger.info("result empty .");
			return;
		}

		for (YyUserAddressBookDo yyUserAddressBookDo : result) {
			if (function.apply(yyUserAddressBookDo.getNumber())) {
				setLastId(key, yyUserAddressBookDo.getId());
				continue;
			}
			logger.warn("sent to mq error ." + yyUserAddressBookDo.toString());
			break;
		}

	}

	private boolean sentMobile(Channel channel, String mobile) {
		if (StringUtils.isBlank(mobile)) {
			return true;
		}
		if (!StringUtils.isNumeric(mobile)) {
			return true;
		}
		if (mobile.length() < 7) {
			return true;
		}
		BasicProperties.Builder builder = new BasicProperties.Builder();
		builder.contentEncoding(BasicConstant.UTF8).contentType(BasicConstant.TEXT_PLAIN).deliveryMode(1).priority(0);
		try {
			RabbitmqClient.publishMessage(channel, ServiceConfig.getMobileExchange(),
					ServiceConfig.getMobilerRoutingKey(), builder.build(), mobile.getBytes(Charsets.UTF_8));
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
			}
			return true;
		} catch (IOException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace();
			}
			logger.error(ExceptionUtils.getStackTrace(e));
			return false;
		}
	}

	private void setLastId(final String key, final Long id) {
		client.use(new Function<Jedis, String>() {

			@Override
			public String apply(Jedis jedis) {
				jedis.set(key, String.valueOf(id));
				return null;
			}
		});
	}

	private long getLastId(final String table) {
		String result = client.use(new Function<Jedis, String>() {

			@Override
			public String apply(Jedis jedis) {
				return jedis.get(table);
			}
		});
		if (StringUtils.isBlank(result)) {
			return 0l;
		}
		return Long.parseLong(result);
	}
}
