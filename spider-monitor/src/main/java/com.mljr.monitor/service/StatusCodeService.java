package com.mljr.monitor.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.mljr.entity.MonitorData;
import com.mljr.redis.RedisClient;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by songchi on 16/12/23.
 */
@Service
public class StatusCodeService {
    private RedisClient redisClient = new RedisClient("127.0.0.1", 6379, 2000, 100, 10, 1000);

    /**
     * 每家网站取最新的一条监控数据,然后汇总起来
     */
    public List<String> getLatestRecord(){
        return redisClient.use(new Function<Jedis, List<String>>() {

            @Override
            public List<String> apply(Jedis jedis) {
                List<String> allList = new ArrayList<>();
                Set<String> set = jedis.keys("status-code-*");
                for (String key : set) {
                    allList.addAll(jedis.lrange(key, 0, 0));
                }
                return allList;
            }
        });
    }

    /**
     * 取某家网站的最新的100天记录
     */
    public List<String> getRecordByDomain(String domain){
        return redisClient.use(new Function<Jedis, List<String>>() {
            @Override
            public List<String> apply(Jedis jedis) {
                return jedis.lrange("status-code-" + domain, 0, 99);
            }
        });
    }

    public List<MonitorData> transferToObject(List<String> jsonList){
        List<MonitorData> dataList = new ArrayList<>();
        for (String jsonStr : jsonList) {
            MonitorData data = JSON.parseObject(jsonStr, MonitorData.class);
            dataList.add(data);
        }
        return dataList;
    }
}
