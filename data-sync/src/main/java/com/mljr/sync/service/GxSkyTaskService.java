

package com.mljr.sync.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.mljr.constant.BasicConstant;
import com.mljr.rabbitmq.RabbitmqClient;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.ucloud.umq.common.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fulin on 2016/12/20.
 */

@Service
public class GxSkyTaskService {
  protected static transient Logger logger = LoggerFactory.getLogger(GxSkyTaskService.class);

  public boolean sendFlag() throws Exception {
    final Channel channel = RabbitmqClient.newChannel();
    try {
      AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
      builder.contentEncoding(BasicConstant.UTF8).contentType(BasicConstant.TEXT_PLAIN).deliveryMode(1).priority(0);
      // 往rabbitMQ中写入数据
      Map<String, Integer> map = new HashMap<String, Integer>();
      map.put("page", 1);
      String result = JSON.toJSON(map).toString();
      RabbitmqClient.publishMessage(channel, ServiceConfig.getBlackIdCardExchange(), ServiceConfig.getBlackIdCardRoutingKey(), builder.build(),
          result.getBytes(Charsets.UTF_8));
      return true;
    } catch (Exception e) {
      logger.error("sync GxSky error!", e);

    } finally {
      if (channel != null) {
        channel.close();
      }
    }
    return false;
  }
}

