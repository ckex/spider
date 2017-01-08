package com.mljr.spider.downloader;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.mljr.common.ServiceConfig;
import com.mljr.constant.RedisConstant;
import com.mljr.entity.CCookie;
import com.mljr.entity.QQCookie;
import com.mljr.redis.RedisClient;
import com.mljr.utils.QQUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.AbstractDownloader;
import us.codecraft.webmagic.selector.PlainText;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by gaoxi on 2017/1/6.
 */
public class QQPhantomJSDownloader extends AbstractDownloader {

    private static final Logger LOGGER = LoggerFactory.getLogger(QQPhantomJSDownloader.class);

    private static RedisClient redisClient = ServiceConfig.getSpiderRedisClient();

    private int retryCount = 3;

    public QQPhantomJSDownloader() {
    }

    @Override
    public Page download(Request request, Task task) {
        List<String> randomKeyList = QQUtils.getRandomKey(1);
        if (null == randomKeyList || randomKeyList.size() == 0) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.warn("redis qq-groups key is null.");
                return null;
            }
        }
        String[] qqStr = QQUtils.spiltQQ(randomKeyList.get(0));
        QQCookie qqCookie = QQUtils.getRedisCookie(qqStr[0]);
        if (null == qqCookie) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.warn("qq {} not find cookie.request {}", qqStr[0], request.getUrl());
                return null;
            }
        }
        WebDriver webDriver = new PhantomJSDriver();
        qqCookie.getCookies().forEach(cookie -> webDriver.manage().addCookie(QQUtils.convert(cookie)));
        webDriver.get(request.getUrl());
        Page page = new Page();
        String content = webDriver.getPageSource();
        if (content.contains("HTTP request failed")) {
            page.setRequest(request);
            return page;
        }
        page.setRawText(content);
        page.setUrl(new PlainText(request.getUrl()));
        page.setRequest(request);
        page.setStatusCode(200);
        webDriver.quit();
        return page;
    }

    @Override
    public void setThread(int threadNum) {
        this.retryCount = threadNum;
    }
}
