package com.mljr.spider.processor;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * Created by xi.gao
 * Date:2016/12/5
 */
public class GuabuBankCardProcessor extends AbstractPageProcessor {

    private Site site = Site.me().setDomain("www.guabu.com")
            .setSleepTime(1200).setRetrySleepTime(4500).setRetryTimes(3)
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

            ImmutableMap<Object, Object> jsonMap = ImmutableMap.builder()
                    .put("title", element.elementText("title"))
                    .put("guishu", element.elementText("guishu"))
                    .put("miaoshu", element.elementText("miaoshu"))
                    .put("image", element.elementText("image"))
                    .put("banktel", element.elementText("banktel"))
                    .put("bankurl", element.elementText("bankurl"))
                    .build();

            page.putField(page.getUrl().get(), JSON.toJSON(jsonMap));

        } catch (DocumentException e) {
            logger.error("guaba xml parse error." + page.toString(), e);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
