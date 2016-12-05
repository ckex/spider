package com.mljr.spider.processor;

import com.mljr.spider.storage.LocalFilePipeline;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;

/**
 * Created by xi.gao
 * Date:2016/12/2
 */
public class SogouMobileProcessorTests {

    private static final String URL = "https://www.sogou.com/web?query=%s";

    public SogouMobileProcessorTests() {
    }

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        String url = String.format(URL, "13910006102");
        FilePipeline pipeline = new LocalFilePipeline("/data/test_html");
        Spider spider = Spider.create(new SogouMobileProcessor())
                .addUrl(url)
                .addPipeline(new ConsolePipeline())
                .addPipeline(pipeline);
        spider.runAsync();
    }


}
