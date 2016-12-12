package com.mljr.spider.processor;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * Created by xi.gao
 * Date:2016/12/5
 */
public class HuoChePiaoProcessor extends AbstractPageProcessor {

    private Site site = Site.me().setDomain("huochepiao.com")
            .setSleepTime(1200).setRetrySleepTime(4500).setRetryTimes(3)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        if (null == html) {
            logger.warn("huochepiao result is empty." + page.getRequest().toString());
            return;
        }
        Selectable selectable = html.xpath("//div[@class='lefttbox']/div/table/tbody");
        if (!selectable.match()) {
            logger.warn("huochepiao response data is not match " + page.getRequest().toString());
            return;
        }

        List<Selectable> selectableList = selectable.xpath("//tr").nodes();

        if (null == selectableList || selectableList.size() == 0) {
            logger.warn("huochepiao nodes is not exists." + page.getRequest().toString());
            return;
        }

        ImmutableMap.Builder<Object, Object> builder = ImmutableMap.builder();
        for (Selectable st : selectableList) {
            builder.put(st.xpath("//tr/td[1]/tidyText()").get(), st.xpath("//tr/td[2]/tidyText()").get());
        }
        page.putField(page.getUrl().get(), JSON.toJSON(builder.build()));
    }

    @Override
    public Site getSite() {
        return site;
    }
}
