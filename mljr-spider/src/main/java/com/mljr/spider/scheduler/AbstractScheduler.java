/**
 * 
 */
package com.mljr.spider.scheduler;

import com.google.common.base.Joiner;
import com.mljr.rabbitmq.RabbitmqClient;
import com.mljr.spider.mq.UMQClient;
import com.mljr.spider.mq.UMQMessage;
import com.mljr.spider.scheduler.manager.AbstractMessage.PullMsgTask;
import com.mljr.utils.RandomUtils;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.*;
import com.ucloud.umq.action.MessageData;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.MonitorableScheduler;
import us.codecraft.webmagic.scheduler.Scheduler;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Ckex zha </br>
 *         2016年11月7日,上午11:37:00
 *
 */
public abstract class AbstractScheduler implements Scheduler, MonitorableScheduler {

  protected transient Logger logger = LoggerFactory.getLogger(getClass());

  private static final int QUEUE_SIZE = 15;

  private static final AtomicLong count = new AtomicLong();

  private final int THREAD_SIZE = 1; // must 1
  protected final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(THREAD_SIZE, new ThreadFactory() {
    final String threadName = "sp-" + logger.getName() + "-work-" + count.incrementAndGet();

    @Override
    public Thread newThread(Runnable r) {
      return new Thread(r, threadName);
    }
  });

  abstract boolean pushTask(Spider spider, UMQMessage message);

  abstract Request buildRequst(String message);

  private BlockingQueue<Request> blockQueue = new LinkedBlockingQueue<Request>();// 默认为最大(blank
                                                                                 // list用到)

  public AbstractScheduler(final Spider spider, BlockingQueue<UMQMessage> mqMsgQueue) throws Exception {
    super();
    pullLocalQueue(spider, mqMsgQueue);
  }

  public AbstractScheduler(final Spider spider, final PullMsgTask task) throws Exception {
    super();
    pullMsgTask(spider, task);
  }

  private String qid;
  private Channel channel;

  public AbstractScheduler(final Spider spider, final String qid) throws Exception {
    super();
    // subscribeMsg(spider, qid);
    this.qid = qid;
    this.channel = RabbitmqClient.newChannel();
  }

  private void pullLocalQueue(final Spider spider, final BlockingQueue<UMQMessage> localQueue) {
    startTask(new Runnable() {

      @Override
      public void run() {
        try {
          consumeMessage(spider, localQueue);
        } catch (Exception ex) {
          if (logger.isDebugEnabled()) {
            ex.printStackTrace();
          }
          logger.error("consume error" + ExceptionUtils.getStackTrace(ex));
        }
      }

    });
  }

  private void consumeMessage(Spider spider, BlockingQueue<UMQMessage> localQueue) {
    for (;;) {
      UMQMessage message = localQueue.poll();
      if (message == null) {
        logger.debug("message is empty . ");
        Thread.yield();
        break;
      }
      sentMsg(spider, message);
    }
  }

  private void pullMsgTask(final Spider spider, final PullMsgTask task) {
    final AtomicInteger id = new AtomicInteger(0);
    startTask(new Runnable() {

      @Override
      public void run() {
        for (;;) {
          String message = task.pullMsg();
          if (StringUtils.isBlank(message)) {
            break;
          }
          UMQMessage msg = new UMQMessage(String.valueOf(Math.abs(id.incrementAndGet())), message);
          sentMsg(spider, msg);
        }
      }
    });
  }

  private void startTask(Runnable r) {
    executor.scheduleWithFixedDelay(r, 0, 1, TimeUnit.SECONDS);
  }

  private void subscribeMsg(final Spider spider, final String qid) throws InterruptedException {
    final CountDownLatch downLatch = new CountDownLatch(1);
    final AtomicBoolean subscribe = new AtomicBoolean(false);
    executor.execute(new Runnable() {

      @Override
      public void run() {
        try {
          logger.debug("Start subscribe queue, " + qid);
          subscribeQueue(spider, qid); // 订阅UMQ
          boolean ret = subscribe.getAndSet(true);
          logger.debug(ret + " Started subscribe queue, " + qid);
        } catch (Throwable e) {
          logger.error("Subscribe error. " + ExceptionUtils.getStackTrace(e));
        } finally {
          downLatch.countDown();
        }
      }

    });
    downLatch.await();
    if (!subscribe.get()) {
      throw new RuntimeException("Subscribe queue " + subscribe.get());
    }
  }

  private void subscribeQueue(final Spider spider, final String qid) throws Exception {
    logger.debug("subscribeQueue = " + qid);
    // subscribeUmq(spider, qid);
    subscribeRmq(spider, qid);
  }

  private void subscribeRmq(final Spider spider, final String qid) throws IOException {
    final boolean autoAck = true;
    final Channel channel = RabbitmqClient.newChannel();
    RabbitmqClient.subscribeMessage(channel, qid, Joiner.on("-").join("consumer", qid), autoAck, new Consumer() {

      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
        try {
          if (sentMsg(spider, new UMQMessage(properties.getMessageId(), new String(body))) & !autoAck) {
            channel.basicAck(envelope.getDeliveryTag(), false);
          }
        } catch (Exception e) {
          if (logger.isDebugEnabled()) {
            e.printStackTrace();
          }
          logger.error(ExceptionUtils.getStackTrace(e));
        }
      }

      @Override
      public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
        logger.warn(qid + ", consumer:" + consumerTag + "," + ExceptionUtils.getStackTrace(sig));
      }

      @Override
      public void handleRecoverOk(String consumerTag) {
        logger.info(qid + ", consumer:" + consumerTag);
      }

      @Override
      public void handleConsumeOk(String consumerTag) {
        logger.info(qid + ", consumer:" + consumerTag);
      }

      @Override
      public void handleCancelOk(String consumerTag) {
        logger.info(qid + ", consumer:" + consumerTag);
      }

      @Override
      public void handleCancel(String consumerTag) throws IOException {
        logger.info(qid + ", consumer:" + consumerTag);
      }
    });
  }

  private void subscribeUmq(final Spider spider, String qid) throws Exception {
    UMQClient.getInstence().subscribeQueue(UMQClient.getInstence().new MessageHandler(qid) {

      @Override
      public boolean processMsg(MessageData message) {
        return sentMsg(spider, new UMQMessage(message));
      }

    });
  }

  private boolean sentMsg(Spider spider, UMQMessage message) {

    if (pushTask(spider, message)) {
      if (Math.random() * 100 < 1) {
        if (logger.isDebugEnabled()) {
          logger.debug("Sent message sucdess ==> " + message.toString());
        }
      }
      return true;
    }
    logger.info("push task false, " + spider.getUUID() + "," + message.toString());
    return false;
  }

  // 阻塞队列
  protected void put(Request request) {
    try {
      blockQueue.put(request);
    } catch (Exception e) {
      if (logger.isDebugEnabled()) {
        e.printStackTrace();
      }
      logger.error("Push task error. " + ExceptionUtils.getStackTrace(e));
    }
  }

  protected String pollMessage() {
    for (int i=0;i<5;i++) {
      boolean autoAck = true;
      try {
        GetResponse response = RabbitmqClient.pollMessage(channel, qid, autoAck);
        if (response == null) {
          if (RandomUtils.randomPrint(100)) {
            logger.debug("qid=" + qid + "queue is empty.waitting message");
          }
          TimeUnit.SECONDS.sleep(1);
          continue;
        }
        if (!autoAck) {
          channel.basicAck(response.getEnvelope().getDeliveryTag(), false);
        }
        return new String(response.getBody(), "UTF-8");
      } catch (Exception e) {
        if (logger.isDebugEnabled()) {
          e.printStackTrace();
        }
        logger.error("Push task error. qid:" + qid + ", " + ExceptionUtils.getStackTrace(e));
      }
    }
    return null;
  }

  // 阻塞队列
  protected Request take() {
    for (;;) {
      try {
        Request request = blockQueue.poll();
        if (logger.isDebugEnabled()) {
          logger.debug("block queue poll data.time:{},request:{}", System.currentTimeMillis(), null == request ? null : request.toString());
        }
        if (null != request)
          return request;
        String msg = pollMessage();
        return buildRequst(msg);
      } catch (Exception e) {
        if (logger.isDebugEnabled()) {
          e.printStackTrace();
        }
        logger.error("Push task error. " + ExceptionUtils.getStackTrace(e));
      }
    }
  }
}
