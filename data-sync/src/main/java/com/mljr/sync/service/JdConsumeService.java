/**
 *
 */
package com.mljr.sync.service;

import com.google.gson.Gson;
import com.mljr.rabbitmq.RabbitmqClient;
import com.mljr.spider.dao.DmPhonePriceDao;
import com.mljr.spider.dao.SrcDmPhonePriceDao;
import com.mljr.spider.model.DmPhonePriceDo;
import com.mljr.spider.model.SrcDmPhonePriceDo;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
public class JdConsumeService {
  protected static transient Logger logger = LoggerFactory.getLogger(JdConsumeService.class);

  private final static String QUEUE_NAME = "jd-item-price-result";

  @Autowired
  private SrcDmPhonePriceDao srcDmPhonePriceDao;

  @Autowired
  private DmPhonePriceDao dmPhonePriceDao;

  public void consume() throws Exception {
    final Channel channel = RabbitmqClient.newChannel();
    try {

      GetResponse response = RabbitmqClient.pollMessage(channel, QUEUE_NAME, false);
      if (response == null) {
        logger.debug("qid=" + QUEUE_NAME + " queue is empty.waitting message");
        return;
      }
      String message = new String(response.getBody(), "UTF-8");
      writeToDb(message);
      channel.basicAck(response.getEnvelope().getDeliveryTag(), false);
    } catch (Exception e) {
      logger.error("JdConsumeService error: " + ", " + ExceptionUtils.getStackTrace(e));
    } finally {
      if (channel != null) {
        channel.close();
      }
    }

  }

  Gson gson = new Gson();

  public void writeToDb(String jsonStr) {
    String time = new JsonPathSelector("time").select(jsonStr);
    String data = new JsonPathSelector("data").select(jsonStr);
    List<String> dataList = new JsonPathSelector("$[*]").selectList(data);
    for (String str : dataList) {
      Map map = gson.fromJson(str, Map.class);
      String id = (String) map.get("id");
      String itemUrl = String.format("https://item.jd.com/%s.html", id.replace("J_", "").trim());
      SrcDmPhonePriceDo src = srcDmPhonePriceDao.load(itemUrl);
      DmPhonePriceDo record = new DmPhonePriceDo();
      record.setBrand(src.getBrand());
      record.setTitle(src.getTitle());
      record.setWebsite("JD");
      try {
        record.setCreateDate(DateUtils.parseDate(time, "yyyy-MM-dd HH:mm:ss"));
      } catch (ParseException e) {
        e.printStackTrace();
      }
      String p = (String) map.get("p");
      record.setPrice(Float.parseFloat(p));
      dmPhonePriceDao.create(record);
    }

  }


}
