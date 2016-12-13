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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class LbsService {

    protected static transient Logger logger = LoggerFactory.getLogger(LbsService.class);

    private static final int LIMIT = 50;

    private static final String PRIMARY_KEY = "contract_id";

    private static final String LBS_KEY = Joiner.on("-").join(BasicConstant.LBS_INFO, BasicConstant.LAST_ID);

    @Autowired
    private RedisClient client;

    @Autowired
    MerchantInfoDao merchantInfoDao;

    public void syncLbsInfo() throws Exception {

        final Channel channel = RabbitmqClient.newChannel();
        try {
            Function<HashMap, Boolean> function = new Function<HashMap, Boolean>() {

                @Override
                public Boolean apply(HashMap map) {
                    return sentMercentInfo(channel, map);
                }
            };
            syncLbsInfo(function);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (channel != null) {
                channel.close();
            }
        }
    }

    private boolean sentMercentInfo(Channel channel, HashMap map) {
        if (map == null || StringUtils.isBlank((String)map.get(PRIMARY_KEY))) {
            return true;
        }
        String[] jsonArr = handleJson(map);
        BasicProperties.Builder builder = new BasicProperties.Builder();
        builder.contentEncoding(BasicConstant.UTF8).contentType(BasicConstant.TEXT_PLAIN).deliveryMode(1).priority(0);
        try {
            RabbitmqClient.publishMessage(channel, ServiceConfig.getLbsExchange(),
                    ServiceConfig.getLbsRoutingKey(), builder.build(), jsonArr[0].getBytes(Charsets.UTF_8));
            RabbitmqClient.publishMessage(channel, ServiceConfig.getLbsExchange(),
                    ServiceConfig.getLbsRoutingKey(), builder.build(), jsonArr[1].getBytes(Charsets.UTF_8));
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

    private String[] handleJson(HashMap map){
        String[] arr = new String[2];
        String id = (String)map.get("contract_id");
        String city = (String)map.get("company_city");
        String home_address = (String)map.get("home_address");
        String company_address = (String)map.get("company_name");

        HashMap<String,String> homeMap = new HashMap<>();
        homeMap.put("id",id);
        homeMap.put("city",city);
        homeMap.put("address",home_address);
        homeMap.put("addressFlag","home");

        HashMap<String,String> companyMap = new HashMap<>();
        companyMap.put("id",id);
        companyMap.put("city",city);
        companyMap.put("address",company_address);
        companyMap.put("addressFlag","company");

        arr[0]=JSON.toJSONString(homeMap);
        arr[1]=JSON.toJSONString(companyMap);
        return arr;
    }

    private void syncLbsInfo(Function<HashMap, Boolean> function) {

        List<HashMap> infos = listData(LBS_KEY);
        if (infos != null && !infos.isEmpty()) {
            for (HashMap map : infos) {
                if(function.apply(map)){
                    setLastId(LBS_KEY,(String)map.get(PRIMARY_KEY));
                    continue;
                }
                logger.error("sync merchant_info error!");
                break;
            }
        }

    }

    private List<HashMap> listData(String key) {
        String lastId = getLastId(key);
        return merchantInfoDao.listAddressById(lastId, LIMIT);
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
