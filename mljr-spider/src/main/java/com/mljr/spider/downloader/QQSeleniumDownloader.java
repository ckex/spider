package com.mljr.spider.downloader;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mljr.entity.QQCookie;
import com.mljr.utils.QQUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.AbstractDownloader;
import us.codecraft.webmagic.selector.PlainText;

import java.util.List;
import java.util.Map;

/**
 * Created by gaoxi on 2017/1/20.
 */
public class QQSeleniumDownloader extends AbstractDownloader {

    private static final Logger LOGGER = LoggerFactory.getLogger(QQSeleniumDownloader.class);

    @Override
    public Page download(Request request, Task task) {
        Map<String, Object> paramsMap = getRequestParams();
        String url = request.getUrl().replace(QQUtils.QQ_LOGIN, paramsMap.get(QQUtils.QQ_LOGIN).toString()).replace(QQUtils.QQ_P_SKY, paramsMap.get(QQUtils.QQ_P_SKY).toString());
        LOGGER.info("downloading page {}", url);
        String pageSource = "";
        WebDriver webDriver = new PhantomJSDriver();
        try {
            List<Cookie> cookieList = (List<Cookie>) paramsMap.get(QQUtils.QQ_COOKIE);
            cookieList.forEach(cookie -> webDriver.manage().addCookie(cookie));
            webDriver.get(url);
            (new WebDriverWait(webDriver, 30)).until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    return driver.getPageSource().indexOf("_Callback(") >= 0;
                }
            });
            pageSource = webDriver.getPageSource();
        } catch (Exception e) {
            LOGGER.error("qq page down exception.", e);
        } finally {
            if (null != webDriver) {
                webDriver.quit();
            }
        }
        request.setUrl(url);
        Page page = new Page();
        page.setRawText(pageSource);
        page.setUrl(new PlainText(request.getUrl()));
        page.setRequest(request);
        page.setStatusCode(200);
        return page;
    }

    @Override
    public void setThread(int threadNum) {

    }

    private Map<String, Object> getRequestParams() {
        String login_qq = "";
        final String[] g_tk = {""};
        List<Cookie> cookieList = Lists.newArrayList();
        List<String> qqList = QQUtils.getRandomKey(1);
        if (qqList != null && qqList.size() > 0) {
            login_qq = QQUtils.spiltQQ(qqList.get(0))[0];
            QQCookie qqCookie = QQUtils.getRedisCookie(login_qq);
            if (null != qqCookie) {
                qqCookie.getCookies().forEach(cCookie -> {
                    if (cCookie.getDomain().startsWith(".")) { //规范
                        cookieList.add(QQUtils.convert(cCookie));
                    }
                    if (cCookie.getName().equalsIgnoreCase(QQUtils.QQ_P_SKY)) {
                        g_tk[0] = QQUtils.getG_TK(cCookie.getValue());
                    }
                });
            } else {
                LOGGER.warn("=====qq {} not login", login_qq);
            }
        } else {
            LOGGER.warn("====== redis qq groups is empty!");
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put(QQUtils.QQ_LOGIN, login_qq);
        map.put(QQUtils.QQ_P_SKY, g_tk[0]);
        map.put(QQUtils.QQ_COOKIE, cookieList);
        return map;
    }

}
