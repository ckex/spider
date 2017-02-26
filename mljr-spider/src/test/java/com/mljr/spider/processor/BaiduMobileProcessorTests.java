/**
 * 
 */
package com.mljr.spider.processor;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.google.common.base.CharMatcher;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 * @author Ckex zha </br>
 *         2016年11月6日,下午8:36:13
 *
 */
public class BaiduMobileProcessorTests {

  private static final String URL = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=2&tn=baiduhome_pg&wd=%s&rsv_spt=1&oq=%s";
  private static final String URL2 =
      "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?query=%s&co=&resource_id=6004&t=%s&ie=utf8&oe=gbk&cb=&format=json&tn=baidu&cb=";

  public BaiduMobileProcessorTests() {}

  /**
   * @param args
   * @throws InterruptedException
   */
  public static void main(String[] args) throws InterruptedException {
    String url = String.format(URL, "03178117334", "03178117334");
    String url2 = String.format(URL2, "03178117334+%E6%89%8B%E6%9C%BA%E5%8F%B7%E6%AE%B5", new Date().getTime() + "");
    Spider spider = Spider.create(new BaiduMobileProcessor()).addUrl(url).addPipeline(new ConsolePipeline());
    TimeUnit.SECONDS.sleep(1);
    spider.getScheduler().push(new Request(url2), spider);
    spider.runAsync();
  }

}
