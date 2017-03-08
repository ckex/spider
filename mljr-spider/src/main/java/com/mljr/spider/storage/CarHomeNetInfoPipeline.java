package com.mljr.spider.storage;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.mljr.constant.BasicConstant;
import com.mljr.rabbitmq.RabbitmqClient;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Map;


/**
 * Created by fulin on 2017/2/20.
 */
public class CarHomeNetInfoPipeline implements Pipeline {

  private static final Logger logger = LoggerFactory.getLogger(CarHomeNetInfoPipeline.class);

  @Override
  public void process(ResultItems resultItems, Task task) {

    List<Map<String, String>> listmap = resultItems.get("data");
    try {
      sendRmq(JSON.toJSONString(listmap));
    } catch (Exception e) {
      logger.error("send jd error!!!", e);
    }
  }

  public void sendRmq(String data) throws Exception {
    final Channel channel = RabbitmqClient.newChannel();
    AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
    builder.contentEncoding(BasicConstant.UTF8).contentType(BasicConstant.TEXT_PLAIN).deliveryMode(1).priority(0);
    try {
      RabbitmqClient.publishMessage(channel, "", "car_test", builder.build(), data.getBytes(Charsets.UTF_8));
    } catch (Exception e) {
      logger.error("send jd error!", e);
    } finally {
      if (channel != null) {
        channel.close();
      }
    }
  }
}
