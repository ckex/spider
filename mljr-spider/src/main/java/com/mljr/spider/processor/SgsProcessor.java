package com.mljr.spider.processor;

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

    public SgsProcessor() {
        super();
    }

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        Selectable baseInfo = html.xpath("//div[@rel='layout-01_01']");
//        System.out.println(baseInfo.toString());
        System.out.println(baseInfo.xpath("//td/text(0)"));
//        page.putField("注册号",html.xpath("//html/body/div[4]/div/div/div[2]/div[3]/div[8]/table/tbody/tr[2]/td[1]/text()"));

    }

    @Override
    public Site getSite() {
        Site site = Site.me().setDomain("www.sgs.gov.cn").setRetrySleepTime(1500).setRetryTimes(3)
                .setUserAgent(
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
        try {
            setCookie(site);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return site;
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
