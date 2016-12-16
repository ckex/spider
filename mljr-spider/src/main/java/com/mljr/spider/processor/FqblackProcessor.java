package com.mljr.spider.processor;

import com.mljr.spider.storage.LocalFilePipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import java.util.List;

public class FqblackProcessor extends AbstractPageProcessor {
    private final static String HELP_URL_REGEX = "http://www\\.fqblack\\.com/index/index\\.html?blackQuery.keyword.*";

    private final static String TARGET_URL_REGEX = "http://www\\.fqblack\\.com/detail/.*";

    private Site site = Site.me().setDomain("fqblack.com")
            .addHeader("Accept", "application/json, text/plain, */*").setRetrySleepTime(3000).setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:50.0) Gecko/20100101 Firefox/50.0");

    public FqblackProcessor() {
        super();
    }

    @Override
    public void process(Page page) {
        if (page.getUrl().regex(HELP_URL_REGEX).match()) {
            // handle helpUrl
            List<String> requests =page.getHtml().links().all();
            if (requests != null && requests.size() > 0) {
                for (String request : requests) {
                    System.out.println(request);
                    if (request.contains("detail")) {
                        String str = request.substring(request.lastIndexOf("/") + 1);
                        page.addTargetRequest("http://www.fqblack.com/detail/" + str);
                    }
                }
            }
        } else {
            // handle targetUrl
            page.putField("", page.getHtml());
        }
    }

    @Override
    public Site getSite() {
        return this.site;
    }

    public static void main(String[] args) {
        Spider.create(new FqblackProcessor())
                .addUrl("http://www.fqblack.com/index/index.html?blackQuery.keyword=%E5%88%98%E9%9B%AA%E8%8E%B2&blackQuery.pageNo=1&blackQuery.pageTotal=2&blackQuery.type=1")
                .addPipeline(new ConsolePipeline()).addPipeline(new LocalFilePipeline("/data/html")).run();

    }

}
