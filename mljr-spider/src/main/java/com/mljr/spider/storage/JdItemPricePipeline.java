/**
 *
 */
package com.mljr.spider.storage;

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

public class JdItemPricePipeline implements Pipeline {

    private static final Logger logger = LoggerFactory.getLogger(JdItemPricePipeline.class);

    @Override
    public void process(ResultItems resultItems, Task task) {

        String data = resultItems.get("data");
        try {
            sendRmq(data);
        } catch (Exception e) {
            logger.error("send jd error!!!", e);
        }

    }

    public void sendRmq(String data) throws Exception {
        final Channel channel = RabbitmqClient.newChannel();
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.contentEncoding(BasicConstant.UTF8).contentType(BasicConstant.TEXT_PLAIN).deliveryMode(1).priority(0);
        try {
            RabbitmqClient.publishMessage(channel, "",
                    "jd-item-price-result", builder.build(), data.getBytes(Charsets.UTF_8));
        } catch (Exception e) {
            logger.error("send jd error!", e);
        } finally {
            if (channel != null) {
                channel.close();
            }
        }
    }

}
