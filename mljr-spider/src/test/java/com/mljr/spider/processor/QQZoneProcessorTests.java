package com.mljr.spider.processor;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.mljr.common.ServiceConfig;
import com.mljr.entity.CCookie;
import com.mljr.entity.QQCookie;
import com.mljr.redis.RedisClient;
import com.mljr.spider.downloader.QQPhantomJSDownloader;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import redis.clients.jedis.Jedis;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by gaoxi on 2017/1/6.
 */
public class QQZoneProcessorTests {

    //qqzone index
    private static final String INDEX_URL = "http://user.qzone.qq.com/%s";

    private static final String QQ_NUMBER = "543109152";

    private static final String QQ_PASSWORD = "";

    private static RedisClient redisClient = ServiceConfig.getSpiderRedisClient();

    public static void main(String[] args) {
        testIndex();
    }

    public static void testIndex() {
        String url = String.format(INDEX_URL, QQ_NUMBER);
        Spider spider = Spider.create(new QQZoneIndexProcessor())
                .addUrl(url).addPipeline(new ConsolePipeline())
                .setDownloader(new QQPhantomJSDownloader());
        spider.runAsync();

    }

}
