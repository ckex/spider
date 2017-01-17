/**
 *
 */
package com.mljr.spider.processor;

import com.google.common.collect.Lists;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Auto163Processor extends AbstractPageProcessor {

    private static Site site = Site.me().setDomain("auto.163.com").setSleepTime(300).setRetrySleepTime(2000).setRetryTimes(3).setUserAgent(
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

    public final static String START_URL = "http://product.auto.163.com/brand/";
    public final static String SECOND_URL_REGEX = "http://product\\.auto\\.163\\.com/brand/[a-z]/";
    public final static String THIRD_URL_REGEX = "http://product\\.auto\\.163\\.com/series/[0-9]{1,6}\\.html";


    @Override
    boolean onProcess(Page page) {
        String currentUrl = page.getUrl().get();

        if (currentUrl.equals(START_URL)) {
            page.setSkip(true);
            for (String url : getUrls()) {
                page.addTargetRequest(url);
            }
        } else if (currentUrl.matches(SECOND_URL_REGEX)) {
            page.setSkip(true);
            List<String> urls = page.getHtml().links().all();
            for (String url : urls) {
                if (url.contains("#")) {
                    url = url.substring(0, url.lastIndexOf("#"));
                }

                if (url.matches(THIRD_URL_REGEX)) {
                    page.addTargetRequest(url);
                }
            }
        } else if (currentUrl.matches(THIRD_URL_REGEX)) {
            page.setSkip(true);
            String target_url = currentUrl.replace("series", "series/config1");
            page.addTargetRequest(target_url);

        } else if (currentUrl.contains("config1")) {
            page.putField("", page.getHtml());
        }

        return true;
    }

    public Auto163Processor() {
        super(site);
    }

    public static void main(String[] args) throws Exception {
        Spider spider = Spider.create(new Auto163Processor())
                .setPipelines(Lists.newArrayList(new FilePipeline("/data/html")));

        spider.addUrl("http://product.auto.163.com/brand/");
        spider.thread(DEFAULT_THREAD_POOL, 50);
        spider.run();

    }

    public List<String> getUrls() {
        List<String> list = Lists.newArrayList();
        for (int i = (int) 'a'; i < ((int) 'a') + 26; i++) {
            list.add(("http://product.auto.163.com/brand/" + (char) i + "/"));
        }
        return list;
    }

    public static final ThreadPoolExecutor DEFAULT_THREAD_POOL = new ThreadPoolExecutor(25, 25, 100,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10), new ThreadFactory() {

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }
    }, new ThreadPoolExecutor.CallerRunsPolicy());


}
