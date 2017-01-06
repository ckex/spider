package com.mljr.spider.downloader;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.mljr.common.ServiceConfig;
import com.mljr.entity.CCookie;
import com.mljr.entity.QQCookie;
import com.mljr.redis.RedisClient;
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

    private static final String QQ_GROUPS_KEY = "qq-groups";

    private static RedisClient redisClient = ServiceConfig.getSpiderRedisClient();

    private int count = 1;

    public QQPhantomJSDownloader() {
    }

    @Override
    public Page download(Request request, Task task) {
        WebDriver webDriver = new PhantomJSDriver();
        List<String> randomKeyList = getRandomKey(this.count);
        if (null == randomKeyList || randomKeyList.size() == 0) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.error("redis qq-groups key is null.");
                return null;
            }
        }
        List<CCookie> cookieList = getCookie(randomKeyList.get(0));
        cookieList.forEach(new Consumer<CCookie>() {
            @Override
            public void accept(CCookie cCookie) {
                webDriver.manage().addCookie(convert(cCookie));
            }
        });

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
        this.count = threadNum;
    }

    /**
     * 随机从QQ中获取Key
     *
     * @param randomCount 获取数量
     * @return
     */
    private List<String> getRandomKey(final int randomCount) {
        return redisClient.use(new Function<Jedis, List<String>>() {
            @Override
            public List<String> apply(Jedis jedis) {
                return jedis.srandmember(QQ_GROUPS_KEY, randomCount);
            }
        });
    }

    /**
     * 获取QQ账号信息
     *
     * @param key key
     * @return QQ Cookie
     */
    private List<CCookie> getCookie(final String key) {
        return redisClient.use(new Function<Jedis, List<CCookie>>() {
            @Override
            public List<CCookie> apply(Jedis jedis) {
                QQCookie qqCookie = JSON.parseObject(jedis.get(key), QQCookie.class);
                return qqCookie.getCookies();
            }
        });
    }

    private Cookie convert(CCookie cCookie) {
        Cookie.Builder builder = new Cookie.Builder(cCookie.getName(), cCookie.getValue())
                .domain(cCookie.getDomain())
                .expiresOn(cCookie.getExpiry())
                .isHttpOnly(cCookie.isHttpOnly())
                .isSecure(cCookie.isSecure())
                .path(cCookie.getPath());
        return builder.build();
    }
}
