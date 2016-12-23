/**
 * 
 */
package com.mljr.spider.processor;

import con.mljr.spider.config.SiteManager;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * @author Ckex zha </br> 2016年11月7日,下午5:20:39
 *
 */
public class SaiGeGPSProcessor extends AbstractPageProcessor {

  private static Site site = Site.me().setDomain("saige-gps").setCharset(UTF_8);

  public SaiGeGPSProcessor() {
    super(site);
  }

  @Override
  public void process(Page page) {
    page.putField(page.getUrl().get(), page.getJson().get());
  }

  @Override
  public Site getSite() {
    return SiteManager.getSiteByDomain("saige-gps");
  }

}
