package com.mljr.spider.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;

import java.util.List;

/**
 * @author songchi
 */
public class QichachaProcessor extends AbstractPageProcessor {

    private Site site;

    @Override
    public void process(Page page) {
        List<String> requests = page.getHtml().links().regex("http://www\\.qichacha\\.com/firm_.*").all();
        page.addTargetRequests(requests);
        page.putField("", page.getHtml());
    }

    @Override
    public Site getSite() {
        if (site == null) {
            site = Site.me().setDomain("www.qichacha.com")
                    .addStartUrl("http://www.qichacha.com/firm_d3c640fcce1a1638b8c8f491e54427d9")
                    .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
        }
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new QichachaProcessor())
                .run();
    }
}
