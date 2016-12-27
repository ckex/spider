package com.mljr.spider.processor;

import com.google.common.base.Joiner;
import com.mljr.spider.storage.LocalFilePipeline;
import con.mljr.spider.config.SiteManager;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.selector.Html;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class TianyanchaProcessor extends AbstractPageProcessor {
	public static final String JS_PATH = System.getProperty("user.home") + System.getProperty("file.separator")
			+ "get_page.js";

	private static Site site = Site.me().setDomain("tianyancha.com").addHeader("loop", "null").setSleepTime(1000 * 20)
			.addHeader("Accept", "application/json, text/plain, */*").setRetrySleepTime(1000 * 45).setRetryTimes(3)
			.setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:50.0) Gecko/20100101 Firefox/50.0");

	public TianyanchaProcessor() {
		super(site);
	}

	@Override
	public void process(Page page) {
		try {
			int i=0;
			String htmlStr = getAjaxContent(page.getUrl().toString());
			Html html = new Html(htmlStr);
			List<String> requests = html.links().all();
			if (requests != null && requests.size() > 0) {
				for (String request : requests) {
					if (request.contains("http://www.tianyancha.com/company")) {
						String targetHtml = getAjaxContent(request);
						if (StringUtils.isBlank(targetHtml)) {
							continue;
						}
						page.putField("", targetHtml);
						++i;
						if(i>3){
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("tianyancha error", e);

		}

	}

	@Override
	public Site getSite() {
		return SiteManager.getSiteByDomain("tianyancha.com");
	}

	public static void main(String[] args) {
		Spider.create(new TianyanchaProcessor())
				.addUrl("http://www.tianyancha.com/search?key=%E5%AE%9C%E9%83%BD%E5%B8%82%E6%9E%9D%E5%9F%8E%E9%95%87%E4%B8%AD%E4%B8%BA%E8%81%94%E9%80%9A%E5%90%88%E4%BD%9C%E8%90%A5%E4%B8%9A%E5%8E%85&checkFrom=searchBox")
				.addPipeline(new ConsolePipeline()).addPipeline(new LocalFilePipeline("/data/html")).run();

	}

	public synchronized String getAjaxContent(String url) {
		try {
			Runtime rt = Runtime.getRuntime();
			String command = Joiner.on(" ").join("phantomjs", JS_PATH, url);
			logger.info("command =================" + command);
			Process p = rt.exec(command);
			InputStream is = p.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuffer sbf = new StringBuffer();
			String tmp = "";
			while ((tmp = br.readLine()) != null) {
				sbf.append(tmp);
			}
			return sbf.toString();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace();
			}
			logger.error("phantomjs download error." + ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

}
