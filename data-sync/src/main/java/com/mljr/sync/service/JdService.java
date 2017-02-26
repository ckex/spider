/**
 *
 */
package com.mljr.sync.service;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.mljr.constant.BasicConstant;
import com.mljr.rabbitmq.RabbitmqClient;
import com.mljr.spider.dao.SrcDmPhonePriceDao;
import com.mljr.spider.model.SrcDmPhonePriceDo;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import common.page.util.PageQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JdService {

  protected static transient Logger logger = LoggerFactory.getLogger(JdService.class);

  public final static String urlPattern =
      "https://p.3.cn/prices/mgets?type=1&area=1_72_2799_0&pdtk=&pduid=1486445598531372370158&pdpin=&pdbp=0&skuIds=%s&_=1487069258413";

  @Autowired
  SrcDmPhonePriceDao srcDmPhonePriceDao;

  public void sendUrls() throws Exception {
    final Channel channel = RabbitmqClient.newChannel();
    BasicProperties.Builder builder = new BasicProperties.Builder();
    builder.contentEncoding(BasicConstant.UTF8).contentType(BasicConstant.TEXT_PLAIN).deliveryMode(1).priority(0);

    try {
      for (String url : getUrls()) {
        RabbitmqClient.publishMessage(channel, "", "jd-item-price", builder.build(), url.getBytes(Charsets.UTF_8));
      }
    } catch (Exception e) {
      logger.error("sync jindong item url error!", e);
    } finally {
      if (channel != null) {
        channel.close();
      }
    }
  }

  public List<String> getUrls() {
    List<SrcDmPhonePriceDo> list = srcDmPhonePriceDao.listByPage(new PageQuery(0, 1000), null);
    StringBuilder accum = new StringBuilder();
    List<String> urls = Lists.newArrayList();
    int i = 0;
    for (SrcDmPhonePriceDo src : list) {
      accum.append("J_").append(src.getJdId()).append(",");
      ++i;
      if (i % 100 == 0) {
        String skuIds = accum.substring(0, accum.length() - 1);
        urls.add(String.format(urlPattern, skuIds));
        accum = new StringBuilder();
      }
    }
    if (accum.length() > 0) {
      String skuIds = accum.substring(0, accum.length() - 1);
      urls.add(String.format(urlPattern, skuIds));
    }
    return urls;
  }

}
