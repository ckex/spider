/**
 *
 */
package com.mljr.spider.scheduler;

import com.google.common.base.CharMatcher;
import com.mljr.spider.mq.UMQMessage;
import com.mljr.spider.scheduler.manager.AbstractMessage.PullMsgTask;
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
    push(buildRequst(message.message), spider);
    return true;
  }

  @Override
  Request buildRequst(String message) {
    String url = CharMatcher.whitespace().replaceFrom(CharMatcher.anyOf("\r\n\t").replaceFrom(message, ""), "");
    return new Request(url);
  }

}
