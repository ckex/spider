package com.mljr.spider.downloader;

import com.google.common.collect.Maps;
import com.mljr.entity.QQCookie;
import com.mljr.utils.QQUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

import java.util.List;
import java.util.Map;

/**
 * Created by gaoxi on 2017/1/18.
 */
public class QQHttpClientDownloader extends HttpClientDownloader {


    private Logger logger = LoggerFactory.getLogger(getClass());

    protected void preDownload(Request request, Task task) {
        Map<String, String> map = getParams();
        String url = request.getUrl();
        if (map.containsKey(QQUtils.QQ_LOGIN)) {
            url = url.replace(QQUtils.QQ_LOGIN, map.get(QQUtils.QQ_LOGIN));
        }
        if (map.containsKey(QQUtils.QQ_P_SKY)) {
            url = url.replace(QQUtils.QQ_P_SKY, map.get(QQUtils.QQ_P_SKY));
        }
        if (map.containsKey(QQUtils.QQ_COOKIE)) {
            task.getSite().addHeader("Cookie", map.get(QQUtils.QQ_COOKIE));
        }
        request.setUrl(url);
    }

    @Override
    public Page download(Request request, Task task) {
        preDownload(request, task);
        return super.download(request, task);
    }

    private Map<String, String> getParams() {
        Map<String, String> map = Maps.newHashMap();
        List<String> list = QQUtils.getRandomKey(1);
        StringBuilder builder = new StringBuilder();
        if (null != list && list.size() > 0) {
            String qqKey=QQUtils.spiltQQ(list.get(0))[0];
            map.put(QQUtils.QQ_LOGIN,qqKey);
            QQCookie qqCookie = QQUtils.getRedisCookie(qqKey);
            if (null != qqCookie) {
                qqCookie.getCookies().forEach(cookie -> {
                    builder.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
                    if (cookie.getName().equalsIgnoreCase(QQUtils.QQ_P_SKY)) {
                        map.put(QQUtils.QQ_P_SKY, QQUtils.getG_TK(cookie.getValue()));
                    }
                });
            } else {
                logger.warn("======qq not login.qq:{}", list.get(0));
            }
        } else {
            logger.warn("=======redis qq groups key is empty.");
        }
        String builders = builder.toString();
        if (StringUtils.isNotBlank(builders)) {
            map.put(QQUtils.QQ_COOKIE, builders.substring(0, builders.length() - 1));
        }
        return map;
    }

}
