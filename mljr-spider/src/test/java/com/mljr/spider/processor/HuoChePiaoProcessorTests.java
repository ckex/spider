package com.mljr.spider.processor;

import com.mljr.spider.storage.LocalFilePipeline;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 * Created by xi.gao Date:2016/12/5
 */
public class HuoChePiaoProcessorTests {

  private static final String URL = "http://www.huochepiao.com/search/bank/?bankid=%s";

  private static final String PATH = "/data/test_html";

  public static void main(String[] args) {

    String url = String.format(URL, "6225881282879179");

    Spider spider = Spider.create(new HuoChePiaoProcessor()).addUrl(url).addPipeline(new LocalFilePipeline(PATH)).addPipeline(new ConsolePipeline());
    spider.runAsync();


  }

}
