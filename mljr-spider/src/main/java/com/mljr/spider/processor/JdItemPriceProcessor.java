/**
 *
 */
package com.mljr.spider.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;


public class JdItemPriceProcessor extends AbstractPageProcessor {

    private static Site site = Site.me().setDomain("item.jd.com").setSleepTime(5000).setRetrySleepTime(2000).setRetryTimes(3).setUserAgent(
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

    @Override
    boolean onProcess(Page page) {
        try{
            String price = page.getJson().jsonPath("$[0].p").get();
            page.putField("price",price);
        }catch (Exception e){
            logger.error("jd-item-price error ",e);
            logger.error("jd-item-price error "+page.getJson().get());
        }

        return true;
    }

    public JdItemPriceProcessor() {
        super(site);
    }
}
