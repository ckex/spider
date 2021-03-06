/**
 * IP138Processor.java by Ckex.zha
 */
package com.ckex.spider.webmagic.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author Ckex.zha <br/>
 *         Nov 1, 20164:41:23 PM
 */
public class IP138Processor implements PageProcessor {

	private Site site = Site.me().setRetrySleepTime(1500).setRetryTimes(3).setUserAgent(
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

	public IP138Processor() {
		super();
	}

	@Override
	public void process(Page page) {
		Selectable tbody = page.getHtml().xpath("//body//table[2]/tbody");
		// page.putField("tbody", tbody);
		page.putField("location", tbody.xpath("//tr[3]//td[2]//text()"));
		page.putField("type", tbody.xpath("//tr[4]//td[2]//text()"));
		page.putField("3", tbody.xpath("//tr[5]//td[2]//text()"));
		page.putField("4", tbody.xpath("//tr[6]//td[2]//text()"));
	}

	@Override
	public Site getSite() {
		return this.site;
	}

}
