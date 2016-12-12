package com.mljr.spider.processor;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.mljr.spider.vo.JSONTransferVO;
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
            logger.warn("cha.yinghangkadata.com result is empty " + page.getRequest().toString());
            return;
        }

        Selectable divSelectable = html.xpath("//div[@class='home_show']/p");

        if (!divSelectable.match()) {
            logger.warn("cha.yinghangkadata.com response data is not match." + page.getRequest().toString());
            return;
        }

        List<Selectable> selectableList = divSelectable.nodes();

        if (null == selectableList || selectableList.size() == 0) {
            logger.warn("cha.yinghangkadata.com parse nodes is not exists." + page.getRequest().toString());
            return;
        }

        Map<String, Object> map = Maps.newHashMap();
        for (Selectable selectable : selectableList) {
            map.put(selectable.xpath("//p/tidyText()").get(), selectable.xpath("//p/tidyText()").get());
        }
        JSONTransferVO transferVO = new JSONTransferVO();
        transferVO.setUrl(page.getUrl().get());
        transferVO.setContext(map);
        page.putField("", JSON.toJSON(transferVO));
    }

    @Override
    public Site getSite() {
        return site;
    }
}
