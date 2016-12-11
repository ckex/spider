package com.mljr.spider.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * Created by xi.gao
 * Date:2016/12/5
 */
public class ChaYHKDataProcessor extends AbstractPageProcessor {

    private Site site = Site.me().setDomain("cha.yinhangkadata.com")
            .setSleepTime(1200).setRetrySleepTime(4500).setRetryTimes(3)
            .addCookie("ASPSESSIONIDCAATTCQA", "LHEGLFIDENPGIJOLIFNHOIFB")//此字段必填
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");


    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        if (null == html) {
            logger.warn("cha.yinghangkadata.com result is empty " + page.getRequest().getUrl());
            return;
        }

        Selectable divSelectable = html.xpath("//div[@class='home_show']/p");

        if (!divSelectable.match()) {
            logger.warn("cha.yinghangkadata.com response data is not match." + page.getRequest().getUrl());
            return;
        }

        List<Selectable> selectableList = divSelectable.nodes();

        if (null != selectableList && selectableList.size() > 0) {

            for (Selectable selectable : selectableList) {

                page.putField(selectable.xpath("//p/tidyText()").get(), selectable.xpath("//p/tidyText()").get());

            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
