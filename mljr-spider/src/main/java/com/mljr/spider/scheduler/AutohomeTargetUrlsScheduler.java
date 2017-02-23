/**
 *
 */
package com.mljr.spider.scheduler;

import com.mljr.spider.mq.UMQMessage;
import com.mljr.spider.scheduler.manager.AbstractMessage.PullMsgTask;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;

/**
 * @author Ckex zha </br>
 *         2016年11月8日,下午12:05:27
 */
public class AutohomeTargetUrlsScheduler extends AbstractScheduler {

  public static final String START_URL = "http://car.autohome.com.cn/AsLeftMenu/As_LeftListNew.ashx?typeId=2&brandId=0&fctId=0&seriesId=0";

  public AutohomeTargetUrlsScheduler(Spider spider, PullMsgTask task) throws Exception {
    super(spider, task);
  }

  public AutohomeTargetUrlsScheduler(Spider spider, String queueId) throws Exception {
    super(spider, queueId);
  }

  @Override
  public void push(Request request, Task task) {
    put(request);
  }

  @Override
  public Request poll(Task task) {
    return take();
  }

  @Override
  public int getLeftRequestsCount(Task task) {
    return 0;
  }

  @Override
  public int getTotalRequestsCount(Task task) {
    return 0;
  }

  @Override
  public boolean pushTask(Spider spider, UMQMessage message) {
    boolean begin = StringUtils.startsWithIgnoreCase(message.message, "autohome");
    if (begin) {
      push(buildRequst(message.message), spider);
    }
    if (logger.isDebugEnabled()) {
      logger.debug("process msg  {}", message);
    }
    // skip
    return true;
  }

  @Override
  Request buildRequst(String message) {
    return new Request(START_URL);
  }

}
