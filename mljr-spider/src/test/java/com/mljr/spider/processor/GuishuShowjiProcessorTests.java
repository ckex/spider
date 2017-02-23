/**
 * 
 */
package com.mljr.spider.processor;

import java.util.Date;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 * @author Ckex zha </br>
 *         2016年11月6日,上午10:25:30
 *
 */
public class GuishuShowjiProcessorTests {

  // private static final String URL =
  // "http://guishu.showji.com/search.htm?m=%s"; // ajax
  // http://v.showji.com/Locating/q.js
  // "http://v.showji.com/Locating/showji.com2016234999234.aspx?m=" +
  // escape($("m").value) + "&output=json&callback=querycallback&timestamp=" +
  // new Date().getTime();
  private static final String URL = "http://v.showji.com/Locating/showji.com2016234999234.aspx?m=%s&output=json&callback=querycallback&timestamp=%s";

  public GuishuShowjiProcessorTests() {
    super();
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    String url = String.format(URL, "15601662655", new Date().getTime());
    Spider spider = Spider.create(new GuishuShowjiProcessor()).addUrl(url).addPipeline(new ConsolePipeline());
    spider.runAsync();
  }

}
