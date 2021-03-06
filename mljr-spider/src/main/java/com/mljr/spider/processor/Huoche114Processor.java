/**
 * 
 */
package com.mljr.spider.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author Ckex zha </br>
 *         2016年11月6日,上午12:09:32
 *
 */
public class Huoche114Processor extends AbstractPageProcessor {

	private Site site = Site.me().setRetrySleepTime(1500).setRetryTimes(3).setUserAgent(
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

	public Huoche114Processor() {
		super();
	}

	@Override
	public void process(Page page) {
		Selectable div = page.getHtml().xpath("//div[@class='xinxi_list']");
		page.putField("卡号归属地", div.xpath("//dl[1]/dd/text()"));
		page.putField("手机卡类型", div.xpath("//dl[2]/dd/text()"));
		page.putField("手机运营商", div.xpath("//dl[3]/dd/text()"));
		page.putField("内置卡类型", div.xpath("//dl[4]/dd/text()"));
		page.putField("通信标准", div.xpath("//dl[5]/dd/text()"));
		page.putField("所在地区号", div.xpath("//dl[6]/dd/text()"));
	}

	@Override
	public Site getSite() {
		return this.site;
	}

}
