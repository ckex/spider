/**
 *
 */
package com.mljr.spider.processor;

import com.google.gson.Gson;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

import java.util.Map;


public class JdItemPriceProcessor extends AbstractPageProcessor {

    private static Site site = Site.me().setDomain("item.jd.com").setSleepTime(10 * 1000).setRetrySleepTime(2000).setRetryTimes(3).setUserAgent(
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

    @Override
    boolean onProcess(Page page) {
        String htmlStr = page.getHtml().get();
        try {
            if (htmlStr.contains("{") && htmlStr.contains("}")) {
                htmlStr = htmlStr.substring(htmlStr.indexOf("{"), (htmlStr.indexOf("}") + 1));
            }
            Map map = new Gson().fromJson(htmlStr, Map.class);
            if (map.get("error") == null) {
                page.putField("price", map.get("p"));

            } else {
                page.setSkip(true);
                logger.error("jd-item-price error {}", htmlStr);
            }

        } catch (Exception e) {
            page.setSkip(true);
            logger.error("jd-item-price error {}  {} ", e, htmlStr);
        }

        return true;
    }

    public JdItemPriceProcessor() {
        super(site);
    }
}
