/**
 * 
 */
package com.mljr.spider.processor;

import com.mljr.spider.downloader.RestfulDownloader;
import com.mljr.spider.request.AbstractRequest;
import com.mljr.spider.request.RestfulReqeust;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.scheduler.Scheduler;

/**
 * @author Ckex zha </br>
 *         2016年11月7日,下午5:27:56
 *
 */
public class SaiGeGPSProcessorTests {

	public static final String URL = "http://218.17.3.228:8008/mljrserver/vehicle/queryGpsInfo";

	private static final String params = "{\"callLetter\":\"\",\"flag\":true,\"sign\":\"335BB919C5476417E424FF6F0BC5AD6F\"}";

	public SaiGeGPSProcessorTests() {
		super();
	}

	public static void main(String[] args) {
		String url = URL;
		Spider spider = Spider.create(new SaiGeGPSProcessor()).setDownloader(new RestfulDownloader()).thread(5)
				.addPipeline(new ConsolePipeline());
		Scheduler scheduler = spider.getScheduler();
		RestfulReqeust request = new RestfulReqeust(url, params);
		request.setMethod(AbstractRequest.POST);
		scheduler.push(request, spider);
		spider.runAsync();
	}

}
