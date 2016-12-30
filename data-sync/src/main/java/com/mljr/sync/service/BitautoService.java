/**
 *
 */
package com.mljr.sync.service;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.mljr.constant.BasicConstant;
import com.mljr.rabbitmq.RabbitmqClient;
import com.mljr.redis.RedisClient;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.ucloud.umq.common.ServiceConfig;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BitautoService {

    protected static transient Logger logger = LoggerFactory.getLogger(BitautoService.class);

    private static final String BITAUTO_EXIST_IDS_KEY = Joiner.on("-").join("bitauto", BasicConstant.EXIST_IDS);

    @Autowired
    RedisClient redisClient;

    public void syncCarinfo() throws Exception {

        final Channel channel = RabbitmqClient.newChannel();
        try {
            for (String carCode : getCarInfo()) {
                if(CommonService.isExist(redisClient,BITAUTO_EXIST_IDS_KEY,carCode)){
                    logger.warn("exist bitauto id =============> {}",carCode);
                    continue;
                }
                sentCarinfo(channel, carCode);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (channel != null) {
                channel.close();
            }
        }
    }

    private boolean sentCarinfo(Channel channel, String carCode) {
        BasicProperties.Builder builder = new BasicProperties.Builder();
        builder.contentEncoding(BasicConstant.UTF8).contentType(BasicConstant.TEXT_PLAIN).deliveryMode(1).priority(0);
        try {
            RabbitmqClient.publishMessage(channel, ServiceConfig.getBitautoExchange(),
                    ServiceConfig.getBitautoRoutingKey(), builder.build(), carCode.getBytes(Charsets.UTF_8));
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

    public final static String API_URL = "http://api.car.bitauto.com/CarInfo/getlefttreejson.ashx?tagtype=chexing";

    private Set<String> getCarInfo() {
        Document doc = null;
        Set<String> carCodes = Sets.newHashSet();
        try {
            doc = Jsoup.connect(API_URL).ignoreContentType(true).get();
            Matcher matcher = Pattern.compile("mb_\\d+").matcher(doc.text());
            while (matcher.find()) {
                carCodes.add(matcher.group().replace("_", ""));
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("易车网接口无法获取数据", e);
        }

        return carCodes;
    }


}
