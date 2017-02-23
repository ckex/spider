package com.mljr.spider.scheduler;

import com.google.common.base.CharMatcher;
import com.mljr.spider.mq.UMQMessage;
import com.mljr.spider.scheduler.manager.AbstractMessage;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;

import java.util.concurrent.BlockingQueue;

/**
 * Created by songchi on 16/12/29.
 */
public class BitautoScheduler extends AbstractScheduler {
  private static final String URL = "http://price.bitauto.com/%s/";

  public BitautoScheduler(Spider spider, AbstractMessage.PullMsgTask task) throws Exception {
    super(spider, task);
  }

  public BitautoScheduler(Spider spider, String qid) throws Exception {
    super(spider, qid);
  }

  public BitautoScheduler(Spider spider, BlockingQueue<UMQMessage> queue) throws Exception {
    super(spider, queue);
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
    String url = String.format(URL, message);
    url = CharMatcher.WHITESPACE.replaceFrom(CharMatcher.anyOf("\r\n\t").replaceFrom(url, ""), "");
    return new Request(url);
  }
}
