/**
 *
 */
package com.mljr.spider.listener;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.Gson;
import com.mljr.redis.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.SpiderListener;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StatusCodeListener implements SpiderListener, Serializable {
    protected transient final Logger logger = LoggerFactory.getLogger(SpiderListener.class);

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

    private String domain;

    private Long beginTime;
    // row:domain   column:状态码    value: 状态码出现次数
    private HashBasedTable<String, Integer, Integer> table = HashBasedTable.create();

    JedisPoolConfig config = new JedisPoolConfig();
    private RedisClient redisClient = new RedisClient("127.0.0.1",6379,2000,100,10,1000);

    public StatusCodeListener(String domain) {
        this.domain = domain;
    }

    @Override
    public void onSuccess(Request request) {
        countStatusCodeByDomain(request);
    }

    @Override
    public void onError(Request request) {
        countStatusCodeByDomain(request);

    }

    private void countStatusCodeByDomain(Request request) {
        synchronized (this) {
            if (beginTime == null) {
                beginTime = System.currentTimeMillis();
            }
            Integer statusCode = (Integer) request.getExtras().get("statusCode");
            if (table.contains(domain, statusCode)) {
                int times = table.get(domain, statusCode);
                table.put(domain, statusCode, ++times);
            } else {
                table.put(domain, statusCode, 1);
            }
            Long timeDiff = System.currentTimeMillis() - beginTime;
            // 一分钟写一次库
            if (timeDiff / 1000 / 60 >= 1) {
                String currentTime = sdf.format(new Date());
                String key = Joiner.on("-").join("status-code",domain);

                Map<String, Table> map = new HashMap<>();
                map.put(currentTime, table);
                logger.debug("### " + new Gson().toJson(map));
                // 写库
                redisClient.use(new Function<Jedis, String>() {

                    @Override
                    public String apply(Jedis jedis) {
                        jedis.lpush(key,new Gson().toJson(map));
                        return null;
                    }
                });

                beginTime = null;
                table.clear();
            }
        }
    }

}
