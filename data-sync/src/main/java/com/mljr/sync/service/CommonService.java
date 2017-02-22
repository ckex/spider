package com.mljr.sync.service;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.mljr.constant.BasicConstant;
import com.mljr.rabbitmq.RabbitmqClient;
import com.mljr.redis.RedisClient;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * Created by songchi on 16/12/15.
 */
public class CommonService {
    protected static transient Logger logger = LoggerFactory.getLogger(BankCardLocationService.class);

    /**
     * @param client
     * @param key
     * @param id
     * @return true skip.
     */
    public static Boolean isExist(RedisClient client, final String key, final String id) {
        Boolean result = client.use(new Function<Jedis, Boolean>() {

            @Override
            public Boolean apply(Jedis jedis) {
                if (jedis.sismember(key, id)) {
                    return true;
                }
                jedis.sadd(key, id);
                return false;
            }
        });
        return result;
    }

    public static boolean sendFlag(String exchange, String routingKey, String flag) throws Exception {
        final Channel channel = RabbitmqClient.newChannel();
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.contentEncoding(BasicConstant.UTF8).contentType(BasicConstant.TEXT_PLAIN).deliveryMode(1).priority(0);
        try {
            RabbitmqClient.publishMessage(channel, exchange,
                    routingKey, builder.build(), flag.getBytes(Charsets.UTF_8));
            return true;
        } catch (Exception e) {
            logger.error("sendFlag error",e);
        } finally {
            if (channel != null) {
                channel.close();
            }
        }
        return false;
    }
}
