package com.mljr.spider.scheduler;

import com.mljr.spider.mq.UMQMessage;
import com.mljr.spider.scheduler.manager.AbstractMessage;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;

import java.util.concurrent.BlockingQueue;

/**
 * Created by fulin on 2017/2/20.
 */
public class CarHomeScheduler extends AbstractScheduler {

  public CarHomeScheduler(Spider spider, AbstractMessage.PullMsgTask task) throws Exception {
    super(spider, task);
  }

  public CarHomeScheduler(Spider spider, String qid) throws Exception {
    super(spider, qid);
  }

  public CarHomeScheduler(Spider spider, BlockingQueue<UMQMessage> queue) throws Exception {
    super(spider, queue);
  }


  @Override
  boolean pushTask(Spider spider, UMQMessage message) {
    push(buildRequst(message.message), spider);
    return true;
  }

  @Override
  Request buildRequst(String message) {
    return new Request(message);
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
  public void push(Request request, Task task) {
    put(request);
  }

  @Override
  public Request poll(Task task) {
    return take();
  }
}
