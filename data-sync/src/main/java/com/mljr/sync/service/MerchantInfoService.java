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
import com.mljr.spider.dao.MerchantInfoDao;
import com.mljr.spider.model.MerchantInfoDo;
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
import java.util.concurrent.TimeUnit;

@Service
public class MerchantInfoService {

    protected static transient Logger logger = LoggerFactory.getLogger(MerchantInfoService.class);

    private static final int LIMIT = 50;


    private static final String MERCHANT_INFO_EXIST_IDS_KEY = Joiner.on("-").join(BasicConstant.MERCENT_INFO,BasicConstant.EXIST_IDS);

    @Autowired
    private RedisClient client;

    @Autowired
    MerchantInfoDao merchantInfoDao;

    public void syncMerchantInfo() throws Exception {

        final Channel channel = RabbitmqClient.newChannel();
        try {
            Function<MerchantInfoDo, Boolean> function = new Function<MerchantInfoDo, Boolean>() {

                @Override
                public Boolean apply(MerchantInfoDo merchantInfoDo) {
                    return sentMercentInfo(channel, merchantInfoDo);
                }
            };
            syncMerchantInfo(function);
        } catch (Exception e){
            logger.error("sync MerchantInfo error!",e);
        } finally {
            if (channel != null) {
                channel.close();
            }
        }
    }

    private boolean sentMercentInfo(Channel channel, MerchantInfoDo merchantInfoDo) {
        if (merchantInfoDo == null || StringUtils.isBlank(merchantInfoDo.getMerchantName())) {
            return true;
        }
        String jsonString = JSON.toJSONString(merchantInfoDo);
        BasicProperties.Builder builder = new BasicProperties.Builder();
        builder.contentEncoding(BasicConstant.UTF8).contentType(BasicConstant.TEXT_PLAIN).deliveryMode(1).priority(0);
        try {
            RabbitmqClient.publishMessage(channel, ServiceConfig.getMerchantInfoExchange(),
                    ServiceConfig.getMerchantInfoRoutingKey(), builder.build(), jsonString.getBytes(Charsets.UTF_8));
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

    private void syncMerchantInfo(Function<MerchantInfoDo, Boolean> function) {
        String key = Joiner.on("-").join(BasicConstant.MERCENT_INFO, BasicConstant.LAST_ID);
        List<MerchantInfoDo> infos = listData(key);
        if (infos != null && !infos.isEmpty()) {
            for (MerchantInfoDo info : infos) {
                if(CommonService.isExist(client,MERCHANT_INFO_EXIST_IDS_KEY,info.getMmId())){
                    logger.warn("exist merchantInfo id  ========  " + info.getMmId());
                    setLastId(key,info.getMmId());
                    continue;
                }
                if(function.apply(info)){
                    setLastId(key,info.getMmId());
                    continue;
                }
                logger.error("sync merchant_info error!");
                break;
            }
        }

    }

    private List<MerchantInfoDo> listData(String key) {
        String lastId = getLastId(key);
        return merchantInfoDao.listById(lastId, LIMIT);
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
