/**
 * 
 */
package com.mljr.spider.processor;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 * @author Ckex zha </br>
 *         2016年11月6日,上午9:35:05
 *
 */
public class Huoche114ProcessorTests {

  private static final String URL = "http://www.114huoche.com/shouji/%s";

  public Huoche114ProcessorTests() {
    super();
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    String url = String.format(URL, "15601662655");
    Spider spider = Spider.create(new Huoche114Processor()).addUrl(url).addPipeline(new ConsolePipeline());
    spider.runAsync();
  }

}
