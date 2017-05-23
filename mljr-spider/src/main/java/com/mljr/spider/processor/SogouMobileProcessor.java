package com.mljr.spider.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * Created by xi.gao
 * Date:2016/12/2
 */
public class SogouMobileProcessor extends AbstractPageProcessor {

    private Site site = Site.me().setSleepTime(5).setRetrySleepTime(1500).setRetryTimes(3)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

    @Override
    public void process(Page page) {

        page.putField("",page.getHtml());

    }

    @Override
    public Site getSite() {
        return site;
    }
}
