package com.mljr.spider.scheduler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CharMatcher;
import com.mljr.spider.mq.UMQMessage;
import com.mljr.spider.scheduler.manager.AbstractMessage;
import com.mljr.spider.util.KeyCacheUtils;
import com.ucloud.umq.common.ServiceConfig;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;

import java.util.concurrent.BlockingQueue;

/**
 * Created by xi.gao Date:2016/12/7
 */
public class LBSBaiduGeoScheduler extends AbstractScheduler {

  private static final String URL = "http://api.map.baidu.com/geocoder/v2/?output=json&ak=%s&address=%s&city=%s";

  public LBSBaiduGeoScheduler(Spider spider, BlockingQueue<UMQMessage> mqMsgQueue) throws Exception {
    super(spider, mqMsgQueue);
  }

  public LBSBaiduGeoScheduler(Spider spider, AbstractMessage.PullMsgTask task) throws Exception {
    super(spider, task);
  }

  public LBSBaiduGeoScheduler(Spider spider, String qid) throws Exception {
    super(spider, qid);
  }

  @Override
  public boolean pushTask(Spider spider, UMQMessage message) {
    if (null == message || StringUtils.isBlank(message.message)) {
      logger.warn("lbs baidu geo mq message is empty");
      return false;
    }
    push(buildRequst(message.message), spider);
    return true;
  }

  @Override
  Request buildRequst(String message) {
    final String key = KeyCacheUtils.getValidKey(KeyCacheUtils.LBSKEY.BAIDU);
    if (StringUtils.isBlank(key)) {
      logger.warn("baidu invalid key " + key + " message=" + message);
      return null;
    }
    JSONObject jsonObject = JSON.parseObject(message);
    String url = String.format(URL, ServiceConfig.getLBSBaiduKey(), "", "");
    if (jsonObject.containsKey("city") && jsonObject.containsKey("address")) {
      String[] cityArray = jsonObject.getString("city").split(" ");
      String address = jsonObject.getString("address");
      String city = "";
      switch (cityArray.length) {
        case 1:
          city = cityArray[0];
          break;
        case 2:
          if (cityArray[0].endsWith("省")) {
            city = cityArray[1];
          } else if (cityArray[0].endsWith("市")) {
            city = cityArray[0];
          } else {
            city = cityArray[1];
          }
          break;
        default:
          city = cityArray[1];
          break;
      }
      url = String.format(URL, key, address, city);
      url = CharMatcher.WHITESPACE.replaceFrom(CharMatcher.anyOf("\r\n\t").replaceFrom(url, ""), "");

      if (logger.isDebugEnabled()) {
        logger.debug("lbs baidu request info.url:{} message:{}", url, message);
      }
    }
    return new Request(url);
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
    String value = KeyCacheUtils.getValidKey(KeyCacheUtils.LBSKEY.BAIDU);
    if (StringUtils.isBlank(value)) {
      return null;
    }
    return take();
  }
}
