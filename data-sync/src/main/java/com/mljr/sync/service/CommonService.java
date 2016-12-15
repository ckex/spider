package com.mljr.sync.service;

import com.google.common.base.Function;
import com.mljr.redis.RedisClient;
import redis.clients.jedis.Jedis;

/**
 * Created by songchi on 16/12/15.
 */
public class CommonService {

    public static Boolean isExist(RedisClient client,final String key ,final String id) {
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
}
