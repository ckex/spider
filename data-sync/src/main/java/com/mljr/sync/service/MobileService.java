/**
 * 
 */
package com.mljr.sync.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.mljr.spider.dao.YyUserAddressBookHistoryDao;
import com.mljr.spider.dao.YyUserCallRecordHistoryDao;
import com.mljr.spider.model.YyUserAddressBookHistoryDo;
import com.mljr.spider.model.YyUserCallRecordHistoryDo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Charsets;
import java.util.function.Function;

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

	private static final String RECORD_KEY = Joiner.on("-").join(BasicConstant.MOBILE_YY_USER_CALL_RECORD,
			BasicConstant.LAST_ID);
	private static final String RECORD_HISTORY_KEY = Joiner.on("-")
			.join(BasicConstant.MOBILE_YY_USER_CALL_RECORD_HISTORY, BasicConstant.LAST_ID);
	private static final String BOOK_KEY = Joiner.on("-").join(BasicConstant.MOBILE_YY_USER_ADDRESS_BOOK,
			BasicConstant.LAST_ID);
	private static final String BOOK_HISTORY_KEY = Joiner.on("-")
			.join(BasicConstant.MOBILE_YY_USER_ADDRESS_BOOK_HISTORY, BasicConstant.LAST_ID);

	@Autowired
	private YyUserAddressBookDao yyUserAddressBookDao;

	@Autowired
	private YyUserAddressBookHistoryDao yyUserAddressBookHistoryDao;

	@Autowired
	private YyUserCallRecordDao yyUserCallRecordDao;

	@Autowired
	private YyUserCallRecordHistoryDao yyUserCallRecordHistoryDao;

	@Autowired
	private RedisClient client;

	public void syncMobile() throws Exception {
		final Channel channel = RabbitmqClient.newChannel();
		try {
			Function<String, Boolean> function = (mobile) -> sentMobile(channel, mobile);

			// Function<String, Boolean> function = new Function<String,
			// Boolean>() {
			//
			// @Override
			// public Boolean apply(String mobile) {
			// return sentMobile(channel, mobile);
			// }
			// };
			syncYyUserAddressBook(function);
			syncYyUserCallRecord(function);
			syncBookHistory(function);
			syncRecordHistory(function);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (channel != null) {
				channel.close();
			}
		}
	}

	private void syncYyUserCallRecord(Function<String, Boolean> function) {
		List<YyUserCallRecordDo> result = listYyUserCallRecord(RECORD_KEY);
		if (result == null || result.isEmpty()) {
			logger.info("result empty .");
			return;
		}
		result.stream().filter(ele -> {
			boolean ret = sent(function, RECORD_KEY, ele.getNumber(), ele.getId());
			if (!ret) {
				logger.warn("sent to mq error ." + ele.toString());
			}
			return !ret;
		}).findFirst();
	}

	private void syncRecordHistory(Function<String, Boolean> function) {
		List<YyUserCallRecordHistoryDo> result = listRecordHistory(RECORD_HISTORY_KEY);
		if (result == null || result.isEmpty()) {
			logger.info("result empty .");
			return;
		}
		result.stream().filter(ele -> {
			boolean ret = sent(function, RECORD_HISTORY_KEY, ele.getNumber(), ele.getId());
			if (!ret) {
				logger.warn("sent to mq error ." + ele.toString());
			}
			return !ret;
		}).findFirst();
	}

	private List<YyUserCallRecordDo> listYyUserCallRecord(String key) {
		long lastId = getLastId(key);
		return yyUserCallRecordDao.listById(lastId, LIMIT);
	}

	private List<YyUserCallRecordHistoryDo> listRecordHistory(String key) {
		long lastId = getLastId(key);
		return yyUserCallRecordHistoryDao.listById(lastId, LIMIT);
	}

	private List<YyUserAddressBookDo> listYyUserAddressBook(String key) {
		long lastId = getLastId(key);
		return yyUserAddressBookDao.listById(lastId, LIMIT);
	}

	private List<YyUserAddressBookHistoryDo> listBookHistory(String key) {
		long lastId = getLastId(key);
		return yyUserAddressBookHistoryDao.listById(lastId, LIMIT);
	}

	private void syncYyUserAddressBook(Function<String, Boolean> function) {
		List<YyUserAddressBookDo> result = listYyUserAddressBook(BOOK_KEY);
		if (result == null || result.isEmpty()) {
			logger.info("result empty .");
			return;
		}
		result.stream().filter(ele -> {
			boolean ret = sent(function, BOOK_KEY, ele.getNumber(), ele.getId());
			if (!ret) {
				logger.warn("sent to mq error ." + ele.toString());
			}
			return !ret;
		}).findFirst();
	}

	private void syncBookHistory(Function<String, Boolean> function) {
		List<YyUserAddressBookHistoryDo> result = listBookHistory(BOOK_HISTORY_KEY);
		if (result == null || result.isEmpty()) {
			logger.info("result empty .");
			return;
		}
		result.stream().filter(ele -> {
			boolean ret = sent(function, BOOK_HISTORY_KEY, ele.getNumber(), ele.getId());
			if (!ret) {
				logger.warn("sent to mq error ." + ele.toString());
			}
			return !ret;
		}).findFirst();

	}

	private boolean sent(Function<String, Boolean> function, String key, String mobile, Long lastId) {
		boolean ret = function.apply(mobile);
		if (ret) {
			setLastId(key, lastId);
		}
		return ret;
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
		client.use(new com.google.common.base.Function<Jedis, String>() {

			@Override
			public String apply(Jedis jedis) {
				jedis.set(key, String.valueOf(id));
				return null;
			}
		});
	}

	private long getLastId(final String table) {
		String result = client.use(new com.google.common.base.Function<Jedis, String>() {

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
