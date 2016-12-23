package com.mljr.spider.processor;

import con.mljr.spider.config.SiteManager;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * Created by xi.gao
 * Date:2016/12/2
 */
public class SogouMobileProcessor extends AbstractPageProcessor {

    private static Site site = Site.me()
            .setDomain("sogou.com") //此字段在生成文件时用到
            .setSleepTime(1000)
            .setRetrySleepTime(4200)
            .setRetryTimes(3)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

    public SogouMobileProcessor() {
        super(site);
    }

    @Override
    public void process(Page page) {
        page.putField("",page.getHtml());

    }

    @Override
    public Site getSite() {
        return SiteManager.getSiteByDomain("sogou.com");
    }
}
