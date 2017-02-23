/**
 * 
 */
package com.mljr.rabbitmq;

import java.io.IOException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * @author Ckex zha </br>
 *         2016年11月22日,上午10:08:18
 *
 */
public class ConnFacotry {
  private static Connection connection;

  public synchronized static void initConnection() throws IOException, TimeoutException {
    if (connection != null) {
      return;
    }
    connection = com.mljr.rabbitmq.ConnectionFactory.newConnection();
  }

  public static Channel newChannel() throws IOException {
    return connection.createChannel();
  }

  private static final AtomicInteger counter = new AtomicInteger(0);

  public static void main(String[] args) throws Exception {
    initConnection();
    int size = 1;
    final CyclicBarrier cyclicBarrier = new CyclicBarrier(size, new Runnable() {

      @Override
      public void run() {
        System.out.println(" start .  .. ");

      }
    });
    for (int i = 0; i < size; i++) {
      final int c = i;
      new Thread(new Runnable() {

        @Override
        public void run() {
          try {
            subscribeMessage("test-consumer-" + c, cyclicBarrier);
          } catch (Exception e) {
          }
        }
      }).start();

    }
    synchronized (ConnFacotry.class) {
      try {
        ConnFacotry.class.wait();
      } catch (InterruptedException e) {
      }
    }
  }

  private static void subscribeMessage(String consumerTag, CyclicBarrier cyclicBarrier) throws Exception {
    boolean autoAck = false;
    final Channel channel = newChannel();

    cyclicBarrier.await();
    channel.basicConsume("queue-fanout-3", autoAck, consumerTag, new DefaultConsumer(channel) {

      @Override
      public void handleConsumeOk(String consumerTag) {
        super.handleConsumeOk(consumerTag);
        System.out.println("handleConsumeOk- " + consumerTag);
      }

      @Override
      public void handleCancelOk(String consumerTag) {
        super.handleCancelOk(consumerTag);
        System.out.println("handleCancelOk- " + consumerTag);
      }

      @Override
      public void handleCancel(String consumerTag) throws IOException {
        super.handleCancel(consumerTag);
        System.out.println("handleCancel- " + consumerTag);
      }

      @Override
      public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
        super.handleShutdownSignal(consumerTag, sig);
        System.out.println("handleShutdownSignal- " + consumerTag);
      }

      @Override
      public void handleRecoverOk(String consumerTag) {
        super.handleRecoverOk(consumerTag);
        System.out.println("handleRecoverOk- " + consumerTag);
      }

      @Override
      public Channel getChannel() {
        System.out.println("getChannel-");
        return super.getChannel();
      }

      @Override
      public String getConsumerTag() {
        System.out.println("getConsumerTag-");
        return super.getConsumerTag();
      }

      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
        try {
          super.handleDelivery(consumerTag, envelope, properties, body);
          String routingKey = envelope.getRoutingKey();
          String contentType = properties.getContentType();
          long deliveryTag = envelope.getDeliveryTag();
          // (process the message components here ...)
          System.out.println(counter.incrementAndGet() + " - " + routingKey + " - " + contentType + " - " + deliveryTag + " - " + new String(body));
          channel.basicAck(deliveryTag, false);

          TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

    });

  }
}
