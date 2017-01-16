/**
 *
 */
package com.mljr.sync.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.mljr.constant.BasicConstant;
import com.mljr.rabbitmq.RabbitmqClient;
import com.mljr.redis.RedisClient;
import com.mljr.spider.dao.CustomerInfoDao;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class QqNumberService {

    protected static transient Logger logger = LoggerFactory.getLogger(QqNumberService.class);

    private static final int LIMIT = 500;


    private static final String QQ_NUMBER_EXIST_IDS_KEY = Joiner.on("-").join(BasicConstant.QQ_NUMBER, BasicConstant.EXIST_IDS);

    @Autowired
    private RedisClient client;

    @Autowired
    CustomerInfoDao customerInfoDao;

    public void syncQqNumber() throws Exception {

        final Channel channel = RabbitmqClient.newChannel();
        try {
            Function<Map, Boolean> function = new Function<Map, Boolean>() {

                @Override
                public Boolean apply(Map map) {
                    return sentQqNumber(channel, map);
                }
            };
            syncQqNumber(function);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (channel != null) {
                channel.close();
            }
        }
    }

    private boolean sentQqNumber(Channel channel, Map qqMap) {
        if (qqMap == null || StringUtils.isBlank((String) qqMap.get("qq"))) {
            return true;
        }
        String jsonString = JSON.toJSONString(qqMap);
        BasicProperties.Builder builder = new BasicProperties.Builder();
        builder.contentEncoding(BasicConstant.UTF8).contentType(BasicConstant.TEXT_PLAIN).deliveryMode(1).priority(0);
        try {
            RabbitmqClient.publishMessage(channel, ServiceConfig.getQqNumberExchange(),
                    ServiceConfig.getQqNumberRoutingKey(), builder.build(), jsonString.getBytes(Charsets.UTF_8));
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

    private void syncQqNumber(Function<Map, Boolean> function) {
        String key = Joiner.on("-").join(BasicConstant.QQ_NUMBER, BasicConstant.LAST_ID);
        List<Map> qqMaps = listData(key);
        if (qqMaps != null && !qqMaps.isEmpty()) {
            for (Map qqMap : qqMaps) {
                String qqNumber = (String) qqMap.get("qq");
                String id = (String) qqMap.get("id");
                if (CommonService.isExist(client, QQ_NUMBER_EXIST_IDS_KEY, qqNumber)) {
                    logger.warn("exist qq number  ========>  " + qqNumber);
                    setLastId(key, id);
                    continue;
                }
                if (function.apply(qqMap)) {
                    setLastId(key, id);
                    continue;
                }
                logger.error("sync qq number error!");
                break;
            }
        }

    }

    private List<Map> listData(String key) {
        String lastId = getLastId(key);
        return customerInfoDao.listById(lastId, LIMIT);
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
