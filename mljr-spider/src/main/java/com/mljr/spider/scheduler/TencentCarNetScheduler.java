package com.mljr.spider.scheduler;

import com.mljr.spider.mq.UMQMessage;
import com.mljr.spider.scheduler.manager.AbstractMessage;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;

import java.util.concurrent.BlockingQueue;

/**
 * Created by fulin on 2017/3/6.
 */
public class TencentCarNetScheduler  extends AbstractScheduler {

  //每隔一段时间，将首地址发给spider
  public static final String START_URL ="http://js.data.auto.qq.com/car_public/1/manufacturer_list_json.js?_=1488525597592";

  public TencentCarNetScheduler(Spider spider, AbstractMessage.PullMsgTask task) throws Exception {
    super(spider, task);
  }
  public TencentCarNetScheduler(Spider spider, String queueId) throws Exception {
    super(spider, queueId);
  }
  public TencentCarNetScheduler(Spider spider, BlockingQueue<UMQMessage> queue) throws Exception {
    super(spider, queue);
  }

  @Override
  boolean pushTask(Spider spider, UMQMessage message) {
    //接收以qq开头的flag标记
    boolean begin = StringUtils.startsWithIgnoreCase(message.message, "qq");
    if (begin) {
      push(buildRequst(message.message), spider);
    }
    if (logger.isDebugEnabled()) {
      logger.debug("process msg  {}", message);
    }
    return true;
  }

  @Override
  Request buildRequst(String message) {
    return new Request(START_URL);
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
