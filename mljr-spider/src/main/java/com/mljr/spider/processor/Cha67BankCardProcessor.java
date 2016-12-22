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
public class Cha67BankCardProcessor extends AbstractPageProcessor {

    private static Site site = Site.me().setDomain("67cha.com")
            .setSleepTime(60000).setRetrySleepTime(30000).setRetryTimes(3)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

    public Cha67BankCardProcessor() {
        super(site);
    }

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        if (null == html) {
            logger.warn("67cha result is empty." + page.getRequest().toString());
            return;
        }

        Selectable selectable = html.xpath("//ol[@class='result']");
        if (!selectable.match()) {
            logger.warn("www.67cha.com response data is not match " + page.getRequest().toString());
            return;
        }

        List<Selectable> selectableList = selectable.xpath("//ul").nodes();
        if (null == selectableList || selectableList.size() == 0) {
            logger.warn("67cha result is empty." + page.getRequest().toString());
            return;
        }
        Map<String, Object> map = Maps.newHashMap();
        for (Selectable st : selectableList) {
            map.put(st.xpath("//ul/li[1]/text()").get(), st.xpath("//ul/li[2]/text()").get());
        }
        JSONTransferVO transferVO = new JSONTransferVO();
        transferVO.setUrl(page.getUrl().get());
        transferVO.setContext(map);
        page.putField("", JSON.toJSON(transferVO));

    }

    @Override
    public Site getSite() {
        return  SiteManager.getSiteByDomain("67cha.com");
    }
}
