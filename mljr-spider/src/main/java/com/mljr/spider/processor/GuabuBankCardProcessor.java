package com.mljr.spider.processor;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.mljr.spider.vo.JSONTransferVO;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

import java.util.Map;

/**
 * Created by xi.gao
 * Date:2016/12/5
 */
public class GuabuBankCardProcessor extends AbstractPageProcessor {

    private Site site = Site.me().setDomain("www.guabu.com")
            .setSleepTime(15000).setRetrySleepTime(7500).setRetryTimes(3)
            .setCharset("GB2312") //返回xml格式为gb2312
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

    @Override
    public void process(Page page) {
        try {
            if (StringUtils.isEmpty(page.getRawText())) {
                logger.warn("result is empty." + page.getRequest().toString());
                return;
            }
            Document document = DocumentHelper.parseText(page.getRawText());

            Element element = document.getRootElement().element("item");

            Map<String, Object> jsonMap = Maps.newHashMap();
            jsonMap.put("title", element.elementText("title"));
            jsonMap.put("guishu", element.elementText("guishu"));
            jsonMap.put("miaoshu", element.elementText("miaoshu"));
            jsonMap.put("image", element.elementText("image"));
            jsonMap.put("banktel", element.elementText("banktel"));
            jsonMap.put("bankurl", element.elementText("bankurl"));

            JSONTransferVO json = new JSONTransferVO();
            json.setUrl(page.getUrl().get());
            json.setContext(jsonMap);
            page.putField("", JSON.toJSON(json));

        } catch (DocumentException e) {
            logger.error("guaba xml parse error." + page.toString(), e);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
