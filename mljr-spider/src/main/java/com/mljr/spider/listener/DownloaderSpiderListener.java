/**
 * 
 */
package com.mljr.spider.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.SpiderListener;

/**
 * @author Ckex zha </br>
 *         2016年11月13日,上午11:02:55
 *
 */
public class DownloaderSpiderListener implements SpiderListener {

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
  }
}
