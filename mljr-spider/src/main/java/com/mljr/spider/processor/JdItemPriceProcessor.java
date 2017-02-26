/**
 *
 */
package com.mljr.spider.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;


public class JdItemPriceProcessor implements PageProcessor {
  protected transient Logger logger = LoggerFactory.getLogger(AbstractPageProcessor.class);

  private static Site site = Site.me().setDomain("item.jd.com").setCharset("utf-8").setSleepTime(30 * 1000).setRetrySleepTime(2000).setRetryTimes(3)
      .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

  boolean onProcess(Page page) {
    String jsonStr = page.getJson().get();
    if (!jsonStr.contains("error")) {
      page.putField("data", jsonStr);
    } else {
      page.setSkip(true);
      logger.error("jd-item-price error {}", jsonStr);
    }
    return true;
  }

  @Override
  public void process(Page page) {
    onProcess(page);
  }

  @Override
  public Site getSite() {
    return site;
  }
}
