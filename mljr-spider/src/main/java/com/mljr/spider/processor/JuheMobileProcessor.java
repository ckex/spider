/**
 * 
 */
package com.mljr.spider.processor;

import java.nio.charset.Charset;
import java.util.List;

import con.mljr.spider.config.SiteManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * @author Ckex zha </br>
 *         2016年11月10日,上午11:33:08
 *
 */
public class JuheMobileProcessor extends AbstractPageProcessor {

	private static Site site = Site.me().setDomain("juhe.cn").setSleepTime(10).setRetryTimes(2).setRetrySleepTime(500)
			.setCharset(UTF_8).addHeader("Content-Type", "application/json;charset=" + UTF_8);

	public JuheMobileProcessor() {
		super(site);
	}

	@Override
	public void process(Page page) {
		if (Math.random() * 100 < 1) {
			if (logger.isDebugEnabled()) {
				logger.debug("process--> " + page.getUrl());
			}
		}
		String text = page.getJson().get();
		JSONObject jsonObject = JSON.parseObject(text);
		Integer retCode = jsonObject.getInteger("error_code");
		if (retCode != null && retCode.intValue() == 0) {
			page.putField(page.getUrl().get(), text);
			return;
		}
		if (logger.isDebugEnabled()) {
			logger.debug(retCode + "==>" + page.getUrl());
		}
		List<NameValuePair> params = URLEncodedUtils.parse(page.getUrl().get(), Charset.forName(UTF_8));
		for (NameValuePair nameValuePair : params) {
			if (StringUtils.equalsIgnoreCase(nameValuePair.getName(), "tel")) {
				logger.error("mobile=%s, result=%s", nameValuePair.getValue(), text);
				return;
			}
		}
	}

	@Override
	public Site getSite() {
		return SiteManager.getSiteByDomain("juhe.cn");
	}

}
