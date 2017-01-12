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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class BankCardLocationService {

    protected static transient Logger logger = LoggerFactory.getLogger(BankCardLocationService.class);

    private static final int LIMIT = 50;

    private static final String SPIDER_KEY = Joiner.on("-").join(BasicConstant.SPIDER_BANK_CARD_LOCATION,BasicConstant.LAST_ID);

    private static final String DM_KEY = Joiner.on("-").join(BasicConstant.DM_TOTAL_APPLICATIONS,BasicConstant.LAST_ID);

    private static final String BANK_CARD_EXIST_IDS_KEY = Joiner.on("-").join(BasicConstant.SPIDER_BANK_CARD_LOCATION,BasicConstant.EXIST_IDS);

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
            syncFrom(SPIDER_KEY,function);
            syncFrom(DM_KEY,function);
        } catch (Exception e){
            logger.error("sync bankcard error!",e);
        } finally{
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

    private void syncFrom(String key, Function<String,Boolean> function){
        List<String> cardNos = new ArrayList<>();
        if(SPIDER_KEY.equals(key)){
            cardNos = listData(key);
        }else if(DM_KEY.equals(key)){
            cardNos = listDM(key);
        }
        if(cardNos!=null&&cardNos.size()>0){
            for (String cardNo : cardNos) {
                if(CommonService.isExist(client, BANK_CARD_EXIST_IDS_KEY,cardNo)){
                    logger.warn("exist cardNo -------------   " + cardNo);
                    setLastId(key, cardNo);
                    continue;
                }
                if (function.apply(cardNo)) {
                    setLastId(key, cardNo);
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

    private List<String> listDM(String key) {
        String lastId = getLastId(key);
        return spiderBankCardLocationDao.listFromDMById(lastId,LIMIT);
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

    private String getLastId(final String key) {
        String result = client.use(new Function<Jedis, String>() {

            @Override
            public String apply(Jedis jedis) {
                return jedis.get(key);
            }
        });
        if (StringUtils.isBlank(result)) {
            return "0";
        }
        return result;
    }

}
