/**
 *
 */
package com.mljr.spider.storage;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.mljr.constant.BasicConstant;
import com.mljr.rabbitmq.RabbitmqClient;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.util.Date;
import java.util.Map;

public class JdItemPricePipeline implements Pipeline {

  private static final Logger logger = LoggerFactory.getLogger(JdItemPricePipeline.class);

  @Override
  public void process(ResultItems resultItems, Task task) {

    try {
      String data = resultItems.get("data");
      int size = new JsonPathSelector("$[*].id").selectList(data).size();
      Map<String, Object> map = Maps.newHashMap();
      map.put("data", data);
      map.put("time", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
      map.put("size", size);
      sendRmq(JSON.toJSONString(map));
    } catch (Exception e) {
      logger.error("send jd error!!!", e);
    }

  }

  public void sendRmq(String data) throws Exception {
    final Channel channel = RabbitmqClient.newChannel();
    AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
    builder.contentEncoding(BasicConstant.UTF8).contentType(BasicConstant.TEXT_PLAIN).deliveryMode(1).priority(0);
    try {
      RabbitmqClient.publishMessage(channel, "", "jd-item-price-result", builder.build(), data.getBytes(Charsets.UTF_8));
    } catch (Exception e) {
      logger.error("send jd error!", e);
    } finally {
      if (channel != null) {
        channel.close();
      }
    }
  }

}
