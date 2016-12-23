package com.mljr.spider.processor;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.mljr.spider.vo.JSONTransferVO;
import con.mljr.spider.config.SiteManager;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;
import java.util.Map;

/**
 * Created by xi.gao
 * Date:2016/12/5
 */
public class YinHangKa388Processor extends AbstractPageProcessor {

    private static Site site = Site.me().setDomain("yinhangka.388g.com")
            .setSleepTime(10000).setRetrySleepTime(7500).setRetryTimes(3)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

    public YinHangKa388Processor() {
        super(site);
    }

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        if (null == html) {
            logger.warn("yinhangka.388g.com result is empty." + page.getRequest().toString());
            return;
        }

        Selectable selectable = html.xpath("//div[@class='knr']/center/table[2]/tbody/tr/td/table/tbody");
        if (!selectable.match()) {
            logger.warn("yinhangka.388g.com response data is not match " + page.getRequest().toString());
            return;
        }

        List<Selectable> selectableList = selectable.xpath("//tr").nodes();
        if (null == selectableList || selectableList.size() == 0) {
            logger.warn("yinhangka.388g.com nodes is not exists." + page.getRequest().toString());
            return;
        }
        Map<String, Object> map = Maps.newHashMap();
        for (Selectable st : selectableList) {
            map.put(st.xpath("//tr/td[1]/tidyText()").get(), st.xpath("//tr/td[2]/tidyText()").get());
        }
        JSONTransferVO transferVO = new JSONTransferVO();
        transferVO.setUrl(page.getUrl().get());
        transferVO.setContext(map);
        page.putField("", JSON.toJSON(transferVO));
    }

    @Override
    public Site getSite() {
        return SiteManager.getSiteByDomain("yinhangka.388g.com");
    }
}
