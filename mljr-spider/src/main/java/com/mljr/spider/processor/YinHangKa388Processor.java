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
public class YinHangKa388Processor extends AbstractPageProcessor {

    private Site site = Site.me().setDomain("yinhangka.388g.com")
            .setSleepTime(1200).setRetrySleepTime(4500).setRetryTimes(3)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        if (null == html) {
            logger.warn("yinhangka.388g.com result is empty." + page.getRequest().getUrl());
            return;
        }
        Selectable selectable = html.xpath("//div[@class='knr']/center/table[2]/tbody/tr/td/table/tbody");
        if (selectable.match()) {
            List<Selectable> selectableList = selectable.xpath("//tr").nodes();
            if (null == selectableList || selectableList.size() == 0) {
                logger.warn("yinhangka.388g.com nodes is not exists." + page.getRequest().getUrl());
                return;
            }
            for (Selectable st : selectableList) {
                page.putField(st.xpath("//tr/td[1]/tidyText()").get(), st.xpath("//tr/td[2]/tidyText()").get());
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
