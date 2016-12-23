package com.mljr.monitor;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.mljr.entity.MonitorData;
import com.mljr.redis.RedisClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@EnableAutoConfiguration
public class MonitorMain {

    private RedisClient redisClient = new RedisClient("127.0.0.1", 6379, 2000, 100, 10, 1000);

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MonitorMain.class, args);
    }

    @RequestMapping("/statusCode")
    ModelAndView statusCode() {
        List<String> jsonList = redisClient.use(new Function<Jedis, List<String>>() {

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
        List<MonitorData> dataList = new ArrayList<>();
        for (String jsonStr : jsonList) {
            MonitorData data = JSON.parseObject(jsonStr, MonitorData.class);
            dataList.add(data);
        }

        ModelAndView mav = new ModelAndView("index");//实例化一个VIew的ModelAndView实例
        mav.addObject("dataList", dataList);
        return mav;
    }

    @RequestMapping("/detail/{domain}/other")
    ModelAndView detail(@PathVariable("domain") String domain) {
        List<String> jsonList = redisClient.use(new Function<Jedis, List<String>>() {
            @Override
            public List<String> apply(Jedis jedis) {
                return jedis.lrange("status-code-" + domain, 0, 100);
            }
        });
        List<MonitorData> dataList = new ArrayList<>();
        for (String jsonStr : jsonList) {
            MonitorData data = JSON.parseObject(jsonStr, MonitorData.class);
            dataList.add(data);
        }

        ModelAndView mav = new ModelAndView("index");//实例化一个VIew的ModelAndView实例
        mav.addObject("dataList", dataList);
        return mav;
    }
}