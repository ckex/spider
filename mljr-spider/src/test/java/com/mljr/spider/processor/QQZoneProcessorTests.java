package com.mljr.spider.processor;

import com.mljr.spider.downloader.QQHttpClientDownloader;
import com.mljr.utils.QQUtils;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 * Created by gaoxi on 2017/1/6.
 */
public class QQZoneProcessorTests {

    public static void main(String[] args) {
        testShuoShuo();
    }

    private static void testShuoShuo() {
        String url = String.format(QQUtils.QQ_INDEX_URL, "467732755");
        Spider spider = Spider.create(new QQZoneIndexProcessor()).setDownloader(new QQHttpClientDownloader()).thread(3)
                .addUrl(url).addPipeline(new ConsolePipeline());
        spider.runAsync();
    }


}
