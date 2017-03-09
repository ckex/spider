/**
 *
 */
package com.mljr.spider.storage;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.mljr.common.ServiceConfig;
import com.mljr.constant.BasicConstant;
import com.mljr.rabbitmq.RabbitmqClient;
import com.mljr.redis.RedisClient;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Set;

public class AutohomeTargetUrlsPipeline implements Pipeline {


    public final static Logger logger = LoggerFactory.getLogger(AutohomeTargetUrlsPipeline.class);

    public AutohomeTargetUrlsPipeline() {
    }

    RedisClient redisClient = ServiceConfig.getSpiderRedisClient();

    private static final String AUTOHOME_EXIST_URLS_KEY = Joiner.on("-").join("autohome", BasicConstant.EXIST_IDS);

    public final static String QUEUE_NAME = "autohome_target_urls";

    @Override
    public void process(ResultItems resultItems, Task task) {

        try {
            Set<String> dataSet = resultItems.get("dataSet");
            if (!CollectionUtils.isEmpty(dataSet)) {
                sendRmq(dataSet);
            }

        } catch (Exception e) {
            logger.error("send autohome error!!!", e);
        }

    }

    public void sendRmq(Set<String> dataSet) throws Exception {
        final Channel channel = RabbitmqClient.newChannel();
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.contentEncoding(BasicConstant.UTF8).contentType(BasicConstant.TEXT_PLAIN).deliveryMode(1).priority(0);
        try {
            for (String url : dataSet) {
                url = this.handleUrl(url);
                if (!isExist(redisClient, AUTOHOME_EXIST_URLS_KEY, url)) {
                    RabbitmqClient.publishMessage(channel, "", QUEUE_NAME, builder.build(), url.getBytes(Charsets.UTF_8));
                }
            }
        } catch (Exception e) {
            logger.error("send autohome error!", e);
        } finally {
            if (channel != null) {
                channel.close();
            }
        }
    }

    public static Boolean isExist(RedisClient client, final String key, final String id) {
        return client.use(jedis -> jedis.sadd(key, id) == 0L);
    }

    private String handleUrl(String url) {
        if (StringUtils.isNotBlank(url) && url.contains("#")) {
            url = url.substring(0, url.indexOf("#"));
        }
        return url;
    }

}
