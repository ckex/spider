/**
 *
 */
package com.mljr.sync.service;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.mljr.constant.BasicConstant;
import com.mljr.rabbitmq.RabbitmqClient;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutohomeFlagService {

  protected static transient Logger logger = LoggerFactory.getLogger(AutohomeFlagService.class);

  public final static String QUEUE_NAME  = "autohome_flag";


  public void sendUrls() throws Exception {
    final Channel channel = RabbitmqClient.newChannel();
    BasicProperties.Builder builder = new BasicProperties.Builder();
    builder.contentEncoding(BasicConstant.UTF8).contentType(BasicConstant.TEXT_PLAIN).deliveryMode(1).priority(0);

    try {
      for (String url : getUrls()) {
        RabbitmqClient.publishMessage(channel, "", QUEUE_NAME, builder.build(), url.getBytes(Charsets.UTF_8));
      }
    } catch (Exception e) {
      logger.error("sync autohome start url error!", e);
    } finally {
      if (channel != null) {
        channel.close();
      }
    }
  }

  public static List<String> getUrls() {
    List<String> list = Lists.newArrayList();
    char[] arr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    String urlPattern = "http://www.autohome.com.cn/grade/carhtml/%s.html";
    for (char c : arr) {
      list.add(String.format(urlPattern, c));
    }
    return list;
  }

  public static void main(String[] args) throws Exception{
    new AutohomeFlagService().sendUrls();
  }

}
