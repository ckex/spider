/**
 * DistributionMessage.java by Ckex.zha
 */
package com.mljr.spider.mq;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mljr.spider.scheduler.manager.AbstractMessage.PullMsgTask;

/**
 * @author Ckex.zha <br/>
 *         Nov 17, 20164:44:09 PM
 */
public class DistributionMessage {

  protected transient Logger logger = LoggerFactory.getLogger(getClass());

  private final int THREAD_SIZE = 1; // must 1
  protected final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(THREAD_SIZE, new ThreadFactory() {

    @Override
    public Thread newThread(Runnable r) {
      return new Thread(r, "distribution-message");
    }
  });

  private final Map<String, BlockingQueue<UMQMessage>> queues = new HashMap<>();

  private final PullMsgTask pullMsgTask;

  public synchronized void addQueue(String key, BlockingQueue<UMQMessage> queue) {
    if (queues.containsKey(key)) {
      throw new IllegalArgumentException("key already :" + key);
    }
    queues.put(key, queue);
  }

  public DistributionMessage(PullMsgTask pullMsgTask) {
    super();
    this.pullMsgTask = pullMsgTask;
  }

  public void start() {
    startTask(pullMsgTask);
  }

  private void startTask(final PullMsgTask task) {
    final AtomicInteger id = new AtomicInteger(0);
    startTask(new Runnable() {

      @Override
      public void run() {
        logger.debug("start pull msg task. ");
        if (queues.isEmpty()) {
          logger.info(" queues is empty. ");
          return;
        }
        for (;;) {
          String message = task.pullMsg();
          if (StringUtils.isBlank(message)) {
            if (logger.isDebugEnabled()) {
              logger.debug("message is empty.");
            }
            break;
          }
          UMQMessage msg = new UMQMessage(String.valueOf(Math.abs(id.incrementAndGet())), message);
          sentMsg(msg);
        }
      }
    });

  }

  private void sentMsg(UMQMessage msg) {
    for (String key : queues.keySet()) {
      try {
        queues.get(key).put(msg);
        if (logger.isDebugEnabled()) {
          if (Math.random() * 100 < 1) {
            logger.debug(key + " ==> " + msg.toString());
          }
        }
      } catch (InterruptedException ex) {
        logger.error(key + " put message error. " + ExceptionUtils.getStackTrace(ex));
      }
    }
  }

  private AtomicBoolean stat = new AtomicBoolean(false);

  private synchronized void startTask(Runnable r) {
    if (stat.get()) {
      throw new RuntimeException("Task is running.");
    }
    executor.scheduleWithFixedDelay(r, 0, 1, TimeUnit.SECONDS);
    stat.set(true);
  }

}
