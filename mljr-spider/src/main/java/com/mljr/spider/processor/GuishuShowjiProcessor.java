/**
 * 
 */
package com.mljr.spider.processor;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.reflect.TypeToken;

import con.mljr.spider.config.SiteManager;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * @author Ckex zha </br>
 *         2016年11月6日,上午10:24:14
 *
 */
public class GuishuShowjiProcessor extends AbstractPageProcessor {

	private static Site site = Site.me().setDomain("guishu.showji.com").setSleepTime(300).setRetrySleepTime(2000).setRetryTimes(3).setUserAgent(
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

	public GuishuShowjiProcessor() {
		super(site);
	}

	@Override
	public void process(Page page) {
		if (Math.random() * 100 < 1) {
			if (logger.isDebugEnabled()) {
				logger.debug("process--> " + page.getUrl());
			}
		}
		page.putField("",page.getHtml());
//		Pattern pattern = Pattern.compile("(?<=\\()(.+?)(?=\\))");
//		String jsonp = page.getHtml().xpath("//body/text()").get();
//		Matcher mat = pattern.matcher(jsonp);
//		if (mat.find()) {
//			String res = mat.group(1);
//			Map<String, String> retMap = gson.fromJson(res, new TypeToken<Map<String, String>>() {
//			}.getType());
//			for (String key : retMap.keySet()) {
//				page.putField(key, retMap.get(key));
//			}
//		} else {
//			logger.error("Matcher error " + jsonp + ", " + page.getUrl());
//		}
	}

	@Override
	public Site getSite() {
		return SiteManager.getSiteByDomain("guishu.showji.com");
	}

}
