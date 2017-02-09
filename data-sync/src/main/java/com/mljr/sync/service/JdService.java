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
import com.ucloud.umq.common.ServiceConfig;
import common.page.util.PageList;
import common.page.util.PageQuery;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ckex zha </br>
 *         2016年11月29日,下午4:08:29
 */
@Service
public class JdService {

    protected static transient Logger logger = LoggerFactory.getLogger(JdService.class);

    @Autowired
    SrcDmPhonePriceDao srcDmPhonePriceDao;

    public void sendUrls() throws Exception {
        final Channel channel = RabbitmqClient.newChannel();
        BasicProperties.Builder builder = new BasicProperties.Builder();
        builder.contentEncoding(BasicConstant.UTF8).contentType(BasicConstant.TEXT_PLAIN).deliveryMode(1).priority(0);

        try {
            for (String url : getItemIds()) {
                RabbitmqClient.publishMessage(channel, ServiceConfig.getJdExchange(),
                        null, builder.build(), url.getBytes(Charsets.UTF_8));
            }
        } catch (Exception e) {
            logger.error("sync jindong item url error!", e);
        } finally {
            if (channel != null) {
                channel.close();
            }
        }
    }

    public List<String> getItemIds() {
        PageList<SrcDmPhonePriceDo> list = srcDmPhonePriceDao.listByPage(new PageQuery(0, 1000), null);
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        List<String> itemIds = Lists.newArrayList();
        for (SrcDmPhonePriceDo priceDo : list) {
            itemIds.add(priceDo.getJdId());
        }
        return itemIds;
    }

}
