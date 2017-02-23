/**
 * 
 */
package com.mljr.spider.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import us.codecraft.webmagic.Request;

/**
 * @author Ckex zha </br>
 *         2016年11月10日,上午11:41:39
 *
 */
public class BlockingQueueTests {

  // static BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
  static ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();

  protected static final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5, new ThreadFactory() {

    @Override
    public Thread newThread(Runnable r) {
      return new Thread(r, "Test");
    }
  });

  public BlockingQueueTests() {}

  public static void main(String[] args) {
    for (int i = 0; i < 5; i++) {
      executor.scheduleWithFixedDelay(new Runnable() {

        @Override
        public void run() {
          System.out.println(Thread.currentThread().getName() + " - start.");
          take();
        }

      }, 0, 1, TimeUnit.SECONDS);
    }
  }

  private static void take() {
    String value = queue.poll();
    System.out.println(Thread.currentThread().getName() + " - " + value);

  }

}
