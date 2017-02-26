/**
 * 
 */
package com.mljr.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.GetResponse;

/**
 * @author Ckex zha </br>
 *         2016年11月25日,上午9:44:56
 *
 */
public class RabbitmqClient {

  protected static transient Logger logger = LoggerFactory.getLogger(RabbitmqClient.class);

  private final Connection rabbitConnection;

  public RabbitmqClient() {
    super();
    try {
      this.rabbitConnection = ConnectionFactory.newConnection();
    } catch (IOException | TimeoutException e) {
      e.printStackTrace();
      logger.error(ExceptionUtils.getStackTrace(e));
      throw new RuntimeException(e);
    }
  }

  public static void subscribeMessage(Channel channel, String queueId, String consumerTag, boolean autoAck, Consumer consumer) throws IOException {
    String ret = channel.basicConsume(queueId, autoAck, consumerTag, consumer);
    logger.info(queueId + " subscribe message " + ret);
  }

  public static GetResponse pollMessage(Channel channel, String queueId, boolean autoAck) throws IOException {
    return channel.basicGet(queueId, autoAck);
  }

  public static Channel newChannel() throws IOException {
    return RabbitmqClientHolder.CLIENT.rabbitConnection.createChannel();
  }

  public static void publishMessage(Channel channel, String exchange, String routingKey, BasicProperties props, byte[] body) throws IOException {
    channel.basicPublish(exchange, (routingKey == null ? "" : routingKey), props, body);
  }

  private static final class RabbitmqClientHolder {
    private static final RabbitmqClient CLIENT = new RabbitmqClient();
  }

}
