package com.mljr.spider.processor;

import com.mljr.spider.storage.LocalFilePipeline;
import con.mljr.spider.config.SiteManager;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author songchi
 */
public class QichachaProcessor extends AbstractPageProcessor {

    private static Site site = Site.me().setDomain("qichacha.com").setRetrySleepTime(1500).setRetryTimes(3).setUserAgent(
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

    public QichachaProcessor() {
        super(site);
    }

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        page.putField("公司名称", html.xpath("//span[@class='text-big font-bold']/text()"));
        page.putField("电话", html.xpath("//*[@id=\"company-top\"]/div/div[1]/span[2]/small[1]/text()"));
        page.putField("邮箱", html.xpath("//*[@id=\"company-top\"]/div/div[1]/span[2]/small[1]/a[1]/text()"));

        Selectable div = html.xpath("//[@class='panel-body text-sm']/ul[@class='company-base']");

        String firstItem = div.xpath("//li[1]/label/text()").toString();
        if (StringUtils.startsWith(firstItem, "统一社会信用代码")) {
            page.putField(div.xpath("//li[1]/label/text()").toString(), div.xpath("//li[1]/text()"));
            page.putField(div.xpath("//li[2]/label/text()").toString(), div.xpath("//li[2]/text()"));
            page.putField(div.xpath("//li[3]/label/text()").toString(), div.xpath("//li[3]/text()"));
            page.putField(div.xpath("//li[4]/label/text()").toString(), div.xpath("//li[4]/text()"));
            page.putField(div.xpath("//li[5]/label/text()").toString(), div.xpath("//li[5]/a/text()"));
            page.putField(div.xpath("//li[6]/label/text()").toString(), div.xpath("//li[6]/text()"));
            page.putField(div.xpath("//li[7]/label/text()").toString(), div.xpath("//li[7]/text()"));
            page.putField(div.xpath("//li[8]/label/text()").toString(), div.xpath("//li[8]/text()"));
            page.putField(div.xpath("//li[9]/label/text()").toString(), div.xpath("//li[9]/text()"));
        } else {
            page.putField(div.xpath("//li[1]/label/text()").toString(), div.xpath("//li[1]/text()"));
            page.putField(div.xpath("//li[2]/label/text()").toString(), div.xpath("//li[2]/text()"));
            page.putField(div.xpath("//li[3]/label/text()").toString(), div.xpath("//li[3]/text()"));
            page.putField(div.xpath("//li[4]/label/text()").toString(), div.xpath("//li[4]/text()"));
            page.putField(div.xpath("//li[5]/label/text()").toString(), div.xpath("//li[5]/text()"));
            page.putField(div.xpath("//li[6]/label/text()").toString(), div.xpath("//li[6]/a/text()"));
            page.putField(div.xpath("//li[7]/label/text()").toString(), div.xpath("//li[7]/text()"));
            page.putField(div.xpath("//li[8]/label/text()").toString(), div.xpath("//li[8]/text()"));
            page.putField(div.xpath("//li[9]/label/text()").toString(), div.xpath("//li[9]/text()"));
            page.putField(div.xpath("//li[10]/label/text()").toString(), div.xpath("//li[10]/text()"));
            page.putField(div.xpath("//li[11]/label/text()").toString(), div.xpath("//li[11]/text()"));
            page.putField(div.xpath("//li[12]/label/text()").toString(), div.xpath("//li[12]/text()"));
        }

        page.putField("公司简介",html.xpath("//*[@id=\"textShowMore\"]/text()"));


    }

    public static void main(String[] args) {
        Spider.create(new QichachaProcessor()).addUrl("http://www.qichacha.com/firm_2abadeb7044eed1a17f3202940308109.shtml")
                .addUrl("http://www.qichacha.com/firm_a0bd2d9eb184996fcb42c722534c6993")
                .addPipeline(new ConsolePipeline()).addPipeline(new LocalFilePipeline("/data/html")).run();
    }
}
