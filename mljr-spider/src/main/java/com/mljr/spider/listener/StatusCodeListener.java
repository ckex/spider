/**
 *
 */
package com.mljr.spider.listener;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.HashBasedTable;
import com.mljr.entity.MonitorData;
import com.mljr.redis.RedisClient;
import com.mljr.utils.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.SpiderListener;

import java.io.Serializable;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class StatusCodeListener implements SpiderListener, Serializable {
    protected transient final Logger logger = LoggerFactory.getLogger(SpiderListener.class);

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

    private AtomicInteger totalRequests = new AtomicInteger(0);

    private String domain;

    private Long beginTime;
    // row:domain   column:状态码    value: 状态码出现次数
    public static HashBasedTable<String, Integer, Integer> table = HashBasedTable.create();

    private RedisClient redisClient = new RedisClient("10.9.175.147", 6379, 2000, 100, 10, 1000);

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
            totalRequests.addAndGet(1);
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
            int timeDiff = (int) (System.currentTimeMillis() - beginTime) / 1000;
            // 一分钟写一次库
            if (timeDiff >= 60) {
                String currentTime = sdf.format(new Date());
                try {
                    String ip = IpUtils.getHostName();
                    String key = Joiner.on("-").join("status-code", ip, domain);
                    MonitorData data = createObjectFromTable();
                    data.setTime(currentTime);
                    data.setServerIp(ip);
                    data.setDomain(domain);
                    data.setTotalRequests(totalRequests.get());
                    data.setStatusCodes(table.row(domain).keySet().toString());

                    String jsonStr = JSON.toJSONString(data);

                    logger.debug("### " + jsonStr);
                    // 写库
                    redisClient.use(new Function<Jedis, String>() {

                        @Override
                        public String apply(Jedis jedis) {
                            jedis.lpush(key, jsonStr);
                            return null;
                        }
                    });
                    totalRequests.set(0);
                    beginTime = null;
                    table.clear();
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("状态码监控报错", e);
                }

            }
        }
    }

    private MonitorData createObjectFromTable() {
        MonitorData data = new MonitorData();
        Map<Integer, Integer> codeMap = table.row(domain);
        for (Map.Entry<Integer, Integer> entry : codeMap.entrySet()) {
            switch (entry.getKey()) {
                case 200:
                    data.setFreq200(entry.getValue());
                    break;
                case 301:
                    data.setFreq301(entry.getValue());
                    break;
                case 302:
                    data.setFreq302(entry.getValue());
                    break;
                case 304:
                    data.setFreq304(entry.getValue());
                    break;
                case 307:
                    data.setFreq307(entry.getValue());
                    break;
                case 401:
                    data.setFreq401(entry.getValue());
                    break;
                case 403:
                    data.setFreq403(entry.getValue());
                    break;
                case 404:
                    data.setFreq404(entry.getValue());
                    break;
                case 500:
                    data.setFreq500(entry.getValue());
                    break;
                case 501:
                    data.setFreq501(entry.getValue());
                    break;
                case 504:
                    data.setFreq504(entry.getValue());
                    break;
                case 9999:
                    data.setFreqParseFail(entry.getValue());
                    break;
            }
        }
        return data;
    }

}
