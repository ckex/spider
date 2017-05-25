/**
 * 
 */
package com.mljr.spider.listener;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

import com.sun.org.apache.bcel.internal.generic.NEW;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.SpiderListener;

/**
 * @author Ckex zha </br>
 *         2016年11月13日,上午11:02:55
 *
 */
public class DownloaderSpiderListener implements SpiderListener {

  private static final AtomicLong last = new AtomicLong(0);

  protected transient final Logger logger;

  public DownloaderSpiderListener(String logName) {
    super();
    this.logger = LoggerFactory.getLogger(logName);
  }

  @Override
  public void onSuccess(Request request) {
    // nothing.
  }

  @Override
  public void onError(Request request) {
    logger.error(request.toString());
    synchronized (logger) {
      String name = logger.getName();
      long time = System.currentTimeMillis() - last.get();
      if (time > 1000 * 60 * 15) { // 15 min
        last.getAndSet(System.currentTimeMillis());
        logger.error(MarkerFactory.getMarker("warn-email"), "{},抓取数据失败 {}", name, request.toString());
      }
    }
  }
}
