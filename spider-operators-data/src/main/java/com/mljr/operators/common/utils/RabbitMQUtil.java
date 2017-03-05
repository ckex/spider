package com.mljr.operators.common.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.mljr.constant.BasicConstant;
import com.mljr.operators.common.constant.MQConstant;
import com.mljr.operators.common.constant.OperatorsEnum;
import com.mljr.operators.entity.model.operators.RequestInfo;
import com.mljr.rabbitmq.RabbitmqClient;
import com.mljr.rabbitmq.Rmq;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author gaoxi
 * @time 2017/3/3
 */
public class RabbitMQUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQUtil.class);

  private RabbitMQUtil() {}

  public static void sendMessage(String exchange, String routingKey, List<RequestInfo> list) {
    final Rmq rmq = new Rmq();
    try {
      list.forEach(requestInfo -> {
        sendMessage(rmq, exchange, routingKey, requestInfo);
      });
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      rmq.closed();
    }
  }

  private static boolean sendMessage(Rmq rmq, String exchange, String routingKey,
      RequestInfo requestInfo) {
    String jsonString = JSON.toJSONString(requestInfo);
    AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
    builder.contentEncoding(BasicConstant.UTF8).contentType(BasicConstant.TEXT_PLAIN)
        .deliveryMode(1).priority(0);
    return rmq.publish(new Function<Channel, Boolean>() {
      @Override
      public Boolean apply(Channel channel) {
        try {
          RabbitmqClient.publishMessage(channel, exchange, routingKey, builder.build(),
              jsonString.getBytes(Charsets.UTF_8));
          try {
            TimeUnit.MILLISECONDS.sleep(10);
          } catch (InterruptedException e) {
          }
          return true;
        } catch (IOException e) {
          if (LOGGER.isDebugEnabled()) {
            e.printStackTrace();
          }
          LOGGER.error(ExceptionUtils.getStackTrace(e));
          return false;
        }
      }
    });
  }

  public static String getRoutingKey(OperatorsEnum operatorsEnum) {
    String key = null;
    switch (operatorsEnum) {
      case CHINAMOBILE:
        key = MQConstant.OPERATOR_MQ_CHINAMOBILE_ROUTING_KEY;
        break;
      case CHINATELECOM:
        key = MQConstant.OPERATOR_MQ_CHINATELECOM_ROUTING_KEY;
        break;
      case CHINAUNICOM:
        key = MQConstant.OPERATOR_MQ_CHINAUNICOM_ROUTING_KEY;
        break;
    }
    return key;
  }
}
