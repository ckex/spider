package com.mljr.spider.processor;

import com.mljr.spider.downloader.QQSeleniumDownloader;
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
    String url = String.format(QQUtils.QQ_INDEX_URL, "467732755", 0);
    Spider spider =
        Spider.create(new QQZoneIndexProcessor()).setDownloader(new QQSeleniumDownloader()).addUrl(url).addPipeline(new ConsolePipeline());
    spider.runAsync();
  }


}
