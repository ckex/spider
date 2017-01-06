package com.mljr.spider.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by gaoxi on 2017/1/5.
 * QQ空间首页解析
 */
public class QQZoneIndexProcessor extends AbstractPageProcessor {

    private static Site site = Site.me().setDomain("qqzone.index")
            .setSleepTime(3000).setRetrySleepTime(3000).setRetryTimes(3);

    public QQZoneIndexProcessor() {
        super(site);
    }

    @Override
    boolean onProcess(Page page) {
        Html html = page.getHtml();
        if (null == html || !html.xpath("//ul[@id=\"feed_friend_list\"]").match()) {
            if (logger.isDebugEnabled()) {
                logger.warn("qq zone index html parse failure.request:" + page.getRequest().toString());
                return false;
            }
        }
        List<Selectable> selectableList = html.xpath("//ul[@id=\"feed_friend_list\"]/li").nodes();
        if (null == selectableList || selectableList.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.warn("qq zone index no shuoshuo,request:" + page.getRequest().getUrl());
                return false;
            }
        }
        selectableList.forEach(new Consumer<Selectable>() {
            @Override
            public void accept(Selectable selectable) {
                System.out.println(selectable.toString());
            }
        });
        page.putField("", page.getHtml());
        return true;
    }
}
