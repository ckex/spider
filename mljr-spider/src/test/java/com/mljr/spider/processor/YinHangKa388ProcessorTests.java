package com.mljr.spider.processor;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 * Created by xi.gao
 * Date:2016/12/5
 */
public class YinHangKa388ProcessorTests {

    private static final String URL="http://yinhangka.388g.com/?cid=%s";

    public static void main(String[] args) {

        String url=String.format(URL,"6228481198457298975");

        Spider spider=Spider.create(new YinHangKa388Processor())
                .addPipeline(new ConsolePipeline())
                .addUrl(url);
        spider.runAsync();

    }
}
