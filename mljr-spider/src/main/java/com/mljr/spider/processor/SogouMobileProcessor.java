package com.mljr.spider.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

/**
 * Created by xi.gao
 * Date:2016/12/2
 */
public class SogouMobileProcessor extends AbstractPageProcessor {

    private static  final Site site = Site.me()
            .setDomain("sogou.com") //此字段在生成文件时用到
            .setSleepTime(5000)
            .setRetrySleepTime(4200)
            .setRetryTimes(3)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

    public SogouMobileProcessor() {
        super(site);
    }

    @Override
    public void process(Page page) {
        Html html=page.getHtml();
        //*[@id="seccodeInput"]
        Selectable selectable=html.xpath("//*[@id=\"seccodeInput\"]");
        if(selectable.match()){
            logger.warn("sogou请求频繁,提示验证码,url:"+page.getUrl().get());
            return;
        }
        page.putField("",page.getHtml());
    }

}
