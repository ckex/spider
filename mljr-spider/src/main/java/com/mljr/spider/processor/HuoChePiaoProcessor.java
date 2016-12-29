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
public class HuoChePiaoProcessor extends AbstractPageProcessor {

    private static Site site = Site.me().setDomain("huochepiao.com")
            .setSleepTime(1200).setRetrySleepTime(4500).setRetryTimes(3)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

    @Override
    boolean onProcess(Page page) {
        Html html = page.getHtml();
        if (null == html) {
            logger.warn("huochepiao result is empty." + page.getRequest().toString());
            return true;
        }
        Selectable selectable = html.xpath("//div[@class='lefttbox']/div/table/tbody");
        if (!selectable.match()) {
            logger.warn("huochepiao response data is not match " + page.getRequest().toString());
            return true;
        }

        List<Selectable> selectableList = selectable.xpath("//tr").nodes();

        if (null == selectableList || selectableList.size() == 0) {
            logger.warn("huochepiao nodes is not exists." + page.getRequest().toString());
            return true;
        }

        Map<Object, Object> jsonMap = Maps.newHashMap();
        for (Selectable st : selectableList) {
            String key = st.xpath("//tr/td[1]/tidyText()").get();
            String value = st.xpath("//tr/td[2]/tidyText()").get();
            jsonMap.put(key, value);
        }
        JSONTransferVO transferVO = new JSONTransferVO();
        transferVO.setUrl(page.getUrl().get());
        transferVO.setContext(jsonMap);
        page.putField("", JSON.toJSON(transferVO));
        return true;
    }

    public HuoChePiaoProcessor() {
        super(site);
    }

    @Override
    public void process(Page page) {

    }

}
