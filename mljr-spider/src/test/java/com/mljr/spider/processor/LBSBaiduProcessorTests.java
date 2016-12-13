package com.mljr.spider.processor;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 * Created by xi.gao
 * Date:2016/12/7
 */
public class LBSBaiduProcessorTests {

    private static final String GEO_URL = "http://api.map.baidu.com/geocoder/v2/?output=json&ak=%s&address=%s&city=%s";

    private static final String REGEO_URL = "http://api.map.baidu.com/geocoder/v2/?output=json&ak=%s&location=%s";

    private static final String BAIDU_KEY = "GZp1LdwmdklAOydYWetPlhRmagCM8Y5y";


    public static void main(String[] args) {

        testReGeo();
    }

    private static void testGeo() {
        String url = String.format(GEO_URL, BAIDU_KEY, "上海浦东新区商城路506号28楼（新梅二期）","上海市");
        Spider spider = Spider.create(new LBSBaiduGeoProcessor())
                .addUrl(url)
                .addPipeline(new ConsolePipeline());
        spider.runAsync();
    }

    private static void testReGeo() {
        String url = String.format(REGEO_URL, BAIDU_KEY, "121.516350,31.229872");
        Spider spider = Spider.create(new LBSBaiduReGeoProcessor())
                .addUrl(url)
                .addPipeline(new ConsolePipeline());
        spider.runAsync();
    }
}
