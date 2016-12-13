package com.mljr.spider.processor;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 * Created by xi.gao
 * Date:2016/12/6
 */
public class LBSAMapProcessorTests {

    private static final String GEO_URL = "http://restapi.amap.com/v3/geocode/geo?key=%s&address=%s&output=JSON";

    private static final String REGEO_URL = "http://restapi.amap.com/v3/geocode/regeo?key=%s&location=%s&output=JSON";

    private static final String AMAP_KEY = "ba019e6442a016268f28e28e4a297bfe";

    public static void main(String[] args) {
        testReGeo();
    }

    private static void testGeo() {
        String url=String.format(GEO_URL,AMAP_KEY,"上海浦东新区商城路506号28楼（新梅二期）");
        Spider spider=Spider.create(new LBSAMapGeoProcessor())
                .addUrl(url)
                .addPipeline(new ConsolePipeline());
        spider.runAsync();
    }

    private static void testReGeo() {
        String url=String.format(REGEO_URL,AMAP_KEY,"121.516350,31.229872");
        Spider spider=Spider.create(new LBSAMapReGeoProcessor())
                .addUrl(url)
                .addPipeline(new ConsolePipeline());
        spider.runAsync();
    }

}
