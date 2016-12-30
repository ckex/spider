package com.mljr.spider.processor;

import com.mljr.constant.DomainConstant;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.PhantomJSDownloader;
import us.codecraft.webmagic.pipeline.CollectorPipeline;
import us.codecraft.webmagic.pipeline.ResultItemsCollectorPipeline;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * Created by songchi
 * Date:2016/12/29
 */
public class BitautoProcessor extends AbstractPageProcessor {

    private static final Site site = Site.me()
            .setDomain(DomainConstant.DOMAIN_BITAUTO) //此字段在生成文件时用到
            .setSleepTime(1000)
            .setRetrySleepTime(4200)
            .setRetryTimes(2)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");


    public final static String FIRST_HELP_URL = "http://price\\.bitauto\\.com/mb.*";
    public final static String SECOND_HELP_URL = "http://price\\.bitauto\\.com/nb.*";
    public final static String TARGET_URL = "/peizhi/$";

    public BitautoProcessor() {
        super(site);
    }

    @Override
    public boolean onProcess(Page page) {
        Selectable currentUrl = page.getUrl();
        if (currentUrl.regex(FIRST_HELP_URL).match()) {
            List<String> secondList = page.getHtml().links().regex(SECOND_HELP_URL).all();
            if(secondList!=null&&secondList.size()>0){

                page.addTargetRequests(secondList);
            }
        } else if (currentUrl.regex(SECOND_HELP_URL).match()) {
            List<String> targetUrls = page.getHtml().links().all();
            for (String url : targetUrls) {
                if(url.contains("peizhi")){
                    page.addTargetRequest(url);
                }
            }
        } else if (currentUrl.regex(TARGET_URL).match()) {
            page.putField("", page.getHtml());
        }

        return true;
    }

    public static void main(String[] args) throws Exception {
        PhantomJSDownloader phantomDownloader = new PhantomJSDownloader().setRetryNum(3);

        CollectorPipeline<ResultItems> collectorPipeline = new ResultItemsCollectorPipeline();

        Spider.create(new BitautoProcessor())
                .addUrl("http://price.bitauto.com/mb9/")
                .setDownloader(new PhantomJSDownloader())
                .addPipeline(collectorPipeline)
                .thread((Runtime.getRuntime().availableProcessors() - 1) << 1)
                .run();

        List<ResultItems> resultItemsList = collectorPipeline.getCollected();
        System.out.println(resultItemsList.get(0).get("html").toString());
    }
}
