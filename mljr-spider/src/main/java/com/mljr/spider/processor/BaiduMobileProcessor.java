/**
 * 
 */
package com.mljr.spider.processor;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author Ckex zha </br>
 *         2016年11月6日,下午8:34:51
 *
 */
public class BaiduMobileProcessor extends AbstractPageProcessor {

	private Site site = Site.me().setDomain("baidu-mobile").setSleepTime(5).setRetrySleepTime(1500).setRetryTimes(3)
			.setUserAgent(
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

	public BaiduMobileProcessor() {
		super();
	}

	@Override
	public void process(Page page) {
		if (Math.random() * 100 < 1) {
			if (logger.isDebugEnabled()) {
				logger.debug("process--> " + page.getUrl());
			}
		}
		// processContent(page);
		String field = "";
		Html html = page.getHtml();
		page.putField(field, html);
	}

	private void processContent(Page page) {
		logger.info(page.getUrl() + " \n " + page.getHtml());
		Html html = page.getHtml();
		if (html == null) {
			logger.warn("result is empty " + page.getUrl());
			return;
		}
		Selectable div = html.xpath("//div[@class='op_mobilephone_r']");
		if (div.match()) {
			page.putField("mobile", div.xpath("//span[1]/text()"));
			String location = div.xpath("//span[2]/text()").get();
			List<String> res = Splitter.onPattern("  ").omitEmptyStrings().trimResults().splitToList(location);
			page.putField("type", res.get(0));
			page.putField("city", res.get(1));
			return;
		}
		div = html.xpath("//div[@class='op_liarphone2_word']");
		if (div.match()) {
			page.putField("content", div.get());
			return;
		}
		JSONObject jsonObject = JSON.parseObject(page.getJson().get());
		JSONArray dataArray = jsonObject.getJSONArray("data");
		if (dataArray != null && !dataArray.isEmpty()) {
			page.putField(JSON_FIELD, page.getJson().get());
			// JSONObject dataObj = (JSONObject) dataArray.get(0);
			// page.putField("mobile", dataObj.get("phoneno"));
			// page.putField("type", dataObj.get("type"));
			// page.putField("city", dataObj.get("city"));
		}
	}

	@Override
	public Site getSite() {
		return this.site;
	}

}
