package com.mljr.spider.processor;

import com.mljr.constant.DomainConstant;
import com.mljr.spider.storage.LocalFilePipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * Created by songchi
 * Date:2016/12/29
 */
public class AutoIfengProcessor extends AbstractPageProcessor {

    private static final Site site = Site.me()
            .setDomain(DomainConstant.DOMAIN_AUTO_IFENG) //此字段在生成文件时用到
            .setSleepTime(1000)
            .setRetrySleepTime(4200)
            .setRetryTimes(2)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");


    public final static String TARGET_URL = "/spec/$";

    public final static String START_URL = "http://car.auto.ifeng.com/";



    public AutoIfengProcessor() {
        super(site);
    }

    @Override
    public boolean onProcess(Page page) {
        Selectable currentUrl = page.getUrl();
       if(START_URL.equals(currentUrl.get())){
           List<String> urls = page.getHtml().links().all();
           for (String url : urls) {
               if(url.contains("series")){
                   url+="spec/";
                   page.addTargetRequest(url);
               }
           }
       }else if(currentUrl.regex(TARGET_URL).match()){
           page.putField("",page.getHtml());
           logger.debug("autoifeng success---------------------------");
       }

        return true;
    }

    public static void main(String[] args) throws Exception {
       Spider.create(new AutoIfengProcessor()).addPipeline(new LocalFilePipeline("/data/autoifeng"))
               .addUrl(START_URL).start();
    }
}
