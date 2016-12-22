package com.mljr.spider.processor;

import con.mljr.spider.config.SiteManager;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author songchi
 */
public class SgsProcessor extends AbstractPageProcessor {

    public final String sgs_url = "http://www.sgs.gov.cn/notice/notice/view?uuid=ovY9VXcgn6d.szSRK8Tt32poa30MYwaM&tab=01";

    public static Site site = Site.me().setDomain("sgs.gov.cn").setRetrySleepTime(1500).setRetryTimes(3)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
    public SgsProcessor() {
        super(site);
    }

    @Override
    public void process(Page page) {
        page.putField("",page.getHtml());
    }

    public void getBasicInfo(Page page){
        Html html = page.getHtml();
        Selectable baseInfo = html.xpath("//div[@rel='layout-01_01']/table/tbody");

        page.putField(baseInfo.xpath("//tr[2]/th/text()").toString(),baseInfo.xpath("//tr[2]/td/text()"));
        page.putField(baseInfo.xpath("//tr[2]/th[2]/text()").toString(),baseInfo.xpath("//tr[2]/td[2]/text()"));

        page.putField(baseInfo.xpath("//tr[3]/th/text()").toString(),baseInfo.xpath("//tr[3]/td/text()"));
        page.putField(baseInfo.xpath("//tr[3]/th[2]/text()").toString(),baseInfo.xpath("//tr[3]/td[2]/text()"));

        page.putField(baseInfo.xpath("//tr[4]/th/text()").toString(),baseInfo.xpath("//tr[4]/td/text()"));
        page.putField(baseInfo.xpath("//tr[4]/th[2]/text()").toString(),baseInfo.xpath("//tr[4]/td[2]/text()"));

        page.putField(baseInfo.xpath("//tr[5]/th/text()").toString(),baseInfo.xpath("//tr[5]/td/text()"));

        page.putField(baseInfo.xpath("//tr[6]/th/text()").toString(),baseInfo.xpath("//tr[6]/td/text()"));
        page.putField(baseInfo.xpath("//tr[6]/th[2]/text()").toString(),baseInfo.xpath("//tr[6]/td[2]/text()"));

        page.putField(baseInfo.xpath("//tr[7]/th/text()").toString(),baseInfo.xpath("//tr[7]/td/text()"));

        page.putField(baseInfo.xpath("//tr[8]/th/text()").toString(),baseInfo.xpath("//tr[8]/td/text()"));
        page.putField(baseInfo.xpath("//tr[8]/th[2]/text()").toString(),baseInfo.xpath("//tr[8]/td[2]/text()"));

        page.putField(baseInfo.xpath("//tr[9]/th/text()").toString(),baseInfo.xpath("//tr[9]/td/text()"));

        // 股东信息
        page.putField("股东信息",html.xpath("//table[@id='investorTable']/tbody/tidyText()"));
        // 变更信息
        page.putField("变更信息",html.xpath("//table[@id='alterTable']/tbody/tidyText()"));
    }

    @Override
    public Site getSite() {
        return SiteManager.getSiteByDomain("sgs.gov.cn");
    }

    public void setCookie(Site site) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext context = HttpClientContext.create();
        CloseableHttpResponse response = httpclient.execute(new HttpGet(sgs_url), context);
        Header[] headers = response.getHeaders("Set-Cookie");
        for (Header h : headers) {
            site.addCookie(h.getName(), h.getValue());
            System.out.println(h.getName() + "   " + h.getValue());
        }
    }

    public static void main(String[] args) throws Exception {
        Spider.create(new SgsProcessor())
                .addUrl("http://www.sgs.gov.cn/notice/notice/view?uuid=ovY9VXcgn6d.szSRK8Tt32poa30MYwaM&tab=01")
                .addPipeline(new ConsolePipeline())
                .run();

    }


}
