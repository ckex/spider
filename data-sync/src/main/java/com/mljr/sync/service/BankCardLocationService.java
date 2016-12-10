/**
 *
 */
package com.mljr.sync.service;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.mljr.constant.BasicConstant;
import com.mljr.rabbitmq.RabbitmqClient;
import com.mljr.redis.RedisClient;
import com.mljr.spider.dao.SpiderBankCardLocationDao;
import com.mljr.spider.dao.YyUserAddressBookDao;
import com.mljr.spider.dao.YyUserCallRecordDao;
import com.mljr.spider.model.YyUserCallRecordDo;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.ucloud.umq.common.ServiceConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class BankCardLocationService {

    protected static transient Logger logger = LoggerFactory.getLogger(BankCardLocationService.class);

    private static final int LIMIT = 50;

    @Autowired
    private RedisClient client;

    @Autowired
    SpiderBankCardLocationDao spiderBankCardLocationDao;

    public void syncBankCard() throws Exception {

        final Channel channel = RabbitmqClient.newChannel();
        try {
            Function<String, Boolean> function = new Function<String, Boolean>() {

                @Override
                public Boolean apply(String cardNo) {
                    return sentCardNo(channel, cardNo);
                }
            };
            syncBankcardLocation(function);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (channel != null) {
                channel.close();
            }
        }
    }

    private boolean sentCardNo(Channel channel, String cardNo) {
        if (StringUtils.isBlank(cardNo)) {
            return true;
        }
        if (!StringUtils.isNumeric(cardNo)) {
            return true;
        }

        BasicProperties.Builder builder = new BasicProperties.Builder();
        builder.contentEncoding(BasicConstant.UTF8).contentType(BasicConstant.TEXT_PLAIN).deliveryMode(1).priority(0);
        try {
            RabbitmqClient.publishMessage(channel, ServiceConfig.getBankCardExchange(),
                    ServiceConfig.getBankcardRoutingKey(), builder.build(), cardNo.getBytes(Charsets.UTF_8));
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

    private void syncBankcardLocation(Function<String,Boolean> function){
        String key = Joiner.on("-").join(BasicConstant.SPIDER_BANK_CARD_LOCATION,BasicConstant.SPIDER_BANK_CARD_LOCATION_TABLE_LAST_ID);
        List<String> locations = listData(key);
        if(locations!=null&&locations.size()>0){
            for (String location : locations) {
                if (function.apply(location)) {
                    setLastId(key, location);
                    continue;
                }
                logger.warn("sent to mq error ." );
                break;
            }
        }

    }

    private List<String> listData(String key) {
        String lastId = getLastId(key);
        return spiderBankCardLocationDao.listById(lastId, LIMIT);
    }

    private void setLastId(final String key, final String id) {
        client.use(new Function<Jedis, String>() {

            @Override
            public String apply(Jedis jedis) {
                jedis.set(key, id);
                return null;
            }
        });
    }

    private String getLastId(final String table) {
        String result = client.use(new Function<Jedis, String>() {

            @Override
            public String apply(Jedis jedis) {
                return jedis.get(table);
            }
        });
        if (StringUtils.isBlank(result)) {
            return "0";
        }
        return result;
    }

}
