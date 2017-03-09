/**
 *
 */
package com.mljr.spider.processor;

import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

public class AutohomeTargetUrlsProcessor implements PageProcessor {

    private static Site site = Site.me().setDomain("autohome.com.cn").setSleepTime(300).
            setTimeOut(60 * 1000).setRetrySleepTime(2000).setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

    public final static String FIRST_URL = "http://www.autohome\\.com\\.cn/grade/carhtml/[A-Z]\\.html";

    public final static String SERIES_URL = "http://car\\.autohome\\.com\\.cn/price/series\\-[0-9]{1,7}\\.html";

    public final static String ON_SALE = "http://car\\.autohome\\.com\\.cn/config/series/.*";

    public final static String STOP_SALE_PEIZHI = "http://car\\.autohome\\.com\\.cn/config/spec/[0-9]{1,7}\\.html.*";

    @Override
    public void process(Page page) {
        String currentUrl = page.getUrl().get();
        if (currentUrl.matches(FIRST_URL)) {
            List<String> seriedUrls = page.getHtml().links().regex(SERIES_URL).all();
            page.addTargetRequests(seriedUrls);
        } else if (currentUrl.matches(SERIES_URL)) {
            // 在售页面
            String onSaleUrl = page.getHtml().links().regex(ON_SALE).get();
            if (StringUtils.isNotBlank(onSaleUrl)) {
                page.putField("dataSet", Sets.newHashSet(onSaleUrl));
            }

            // 停售
            List<String> allUrls = page.getHtml().links().all();
            for (String url : allUrls) {
                if (url.contains("-0-")) {
                    page.addTargetRequest(url);
                }
            }

        } else if (currentUrl.contains("-0-")) {
            // 抓取停售页面的配置
            List<String> stopSales = page.getHtml().links().regex(STOP_SALE_PEIZHI).all();

            if (CollectionUtils.isNotEmpty(stopSales)) {
                page.putField("dataSet", Sets.newHashSet(stopSales));
            }

            // 分页
            String href = page.getHtml().css("#brandtab-1 > div.price-page02 > div > a.page-item-next", "href").get();
            if (StringUtils.isNotBlank(href) && href.contains("price")) {
                page.addTargetRequest(href);
            }
        }

    }

    @Override
    public Site getSite() {
        return site;
    }


}
