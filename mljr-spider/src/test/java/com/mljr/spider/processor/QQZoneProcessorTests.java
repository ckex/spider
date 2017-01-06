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
       // getCookies();
    }

    public static void testIndex() {
        String url = String.format(INDEX_URL, QQ_NUMBER);
        Spider spider = Spider.create(new QQZoneIndexProcessor())
                .addUrl(url).addPipeline(new ConsolePipeline())
                .setDownloader(new QQPhantomJSDownloader());
        spider.runAsync();

    }

    private static void getCookies() {
        WebDriver webDriver = new PhantomJSDriver();

        webDriver.get("http://i.qq.com");

        webDriver.switchTo().frame("login_frame");

        webDriver.findElement(By.id("switcher_plogin")).click();

        WebElement account_input = webDriver.findElement(By.id("u"));//账号输入狂

        WebElement password_input = webDriver.findElement(By.id("p"));//密码输入框

        WebElement login_button = webDriver.findElement(By.id("login_button"));//登陆按钮

        account_input.clear();

        password_input.clear();

        account_input.sendKeys(QQ_NUMBER);//QQ账号

        password_input.sendKeys(QQ_PASSWORD);//QQ密码

        login_button.click();

        Set<Cookie> cookieSet = webDriver.manage().getCookies();//获取cookies信息
        List<CCookie> list = Lists.newArrayList();
        cookieSet.forEach(new Consumer<Cookie>() {
            @Override
            public void accept(Cookie cookie) {
                list.add(convert(cookie));
            }
        });

        redisClient.use(new Function<Jedis, String>() {
            @Override
            public String apply(Jedis jedis) {
                return jedis.set(QQ_NUMBER, JSON.toJSONString(new QQCookie(QQ_NUMBER, QQ_PASSWORD, list)));
            }
        });

        webDriver.quit();
    }

    private static CCookie convert(Cookie cookie) {
        CCookie entity = new CCookie();
        entity.setDomain(cookie.getDomain());
        entity.setExpiry(cookie.getExpiry());
        entity.setHttpOnly(cookie.isHttpOnly());
        entity.setName(cookie.getName());
        entity.setValue(cookie.getValue());
        entity.setPath(cookie.getPath());
        entity.setSecure(cookie.isSecure());
        return entity;
    }


}
