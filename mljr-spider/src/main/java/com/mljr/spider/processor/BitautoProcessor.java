package com.mljr.spider.processor;

import com.mljr.constant.DomainConstant;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * Created by songchi
 * Date:2016/12/29
 */
public class BitautoProcessor extends AbstractPageProcessor {

    private static final Site site = Site.me()
            .setDomain(DomainConstant.DOMAIN_BITAUTO) //此字段在生成文件时用到
            .setSleepTime(5000)
            .setRetrySleepTime(4200)
            .setRetryTimes(3)
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
        Selectable url = page.getUrl();
        if (url.regex(FIRST_HELP_URL).match()) {
            List<String> secondList = page.getHtml().links().regex(SECOND_HELP_URL).all();
            page.addTargetRequests(secondList);
        } else if (url.regex(SECOND_HELP_URL).match()) {
            List<String> targetList = page.getHtml().links().regex(TARGET_URL).all();
            page.addTargetRequests(targetList);
        } else if (url.regex(TARGET_URL).match()) {
            page.putField("", page.getHtml());
        }

        return true;
    }
}
