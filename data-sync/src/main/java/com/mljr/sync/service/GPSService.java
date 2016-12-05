/**
 *
 */
package com.mljr.sync.service;

import com.google.common.base.Charsets;
import com.mljr.constant.BasicConstant;
import com.mljr.rabbitmq.RabbitmqClient;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.ucloud.umq.common.ServiceConfig;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Ckex zha </br>
 *         2016年11月29日,下午4:08:29
 */
@Service
public class GPSService {

    protected static transient Logger logger = LoggerFactory.getLogger(GPSService.class);

    public boolean sendFlag() throws Exception {
        final Channel channel = RabbitmqClient.newChannel();
        String flag = "gps-" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        BasicProperties.Builder builder = new BasicProperties.Builder();
        builder.contentEncoding(BasicConstant.UTF8).contentType(BasicConstant.TEXT_PLAIN).deliveryMode(1).priority(0);
        try {
            RabbitmqClient.publishMessage(channel, ServiceConfig.getGPSExchange(),
                    ServiceConfig.getGPSRoutingKey(), builder.build(), flag.getBytes(Charsets.UTF_8));
            logger.info(flag);
            System.out.println(flag);
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

}
