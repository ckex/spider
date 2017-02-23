package com.mljr.spider.processor;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 * Created by xi.gao Date:2016/12/5
 */
public class Cha67BankCardProcessorTests {

  private static final String URL = "http://www.67cha.com/bank/%s.html";

  public static void main(String[] args) {

    String url = String.format(URL, "6225881282879180");

    Spider spider = Spider.create(new Cha67BankCardProcessor()).addUrl(url).addPipeline(new ConsolePipeline());
    spider.runAsync();

  }
}
