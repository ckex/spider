/**
 * 
 */
package com.mljr.spider.scheduler.manager;

import java.io.File;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.nio.reactor.IOReactorException;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.mljr.spider.downloader.RestfulDownloader;
import com.mljr.spider.http.AsyncHttpClient;
import com.mljr.spider.listener.DownloaderSpiderListener;
import com.mljr.spider.processor.AbstractPageProcessor;
import com.mljr.spider.processor.BaiduMobileProcessor;
import com.mljr.spider.processor.JuheMobileProcessor;
import com.mljr.spider.processor.SaiGeGPSProcessor;
import com.mljr.spider.processor.SogouMobileProcessor;
import com.mljr.spider.scheduler.AbstractScheduler;
import com.mljr.spider.scheduler.BaiduMobileScheduler;
import com.mljr.spider.scheduler.JuheMobileScheduler;
import com.mljr.spider.scheduler.SaiGeGPSScheduler;
import com.mljr.spider.scheduler.SogouMobileScheduler;
import com.mljr.spider.storage.HttpPipeline;
import com.mljr.spider.storage.LocalFilePipeline;
import com.mljr.spider.storage.LogPipeline;
import com.ucloud.umq.common.ServiceConfig;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author Ckex zha </br>
 *         2016年11月9日,下午3:27:45
 *
 */
public class Manager extends AbstractMessage {

	private final AsyncHttpClient httpClient;
	private final String url;

	public Manager() {
		super();
		this.url = ServiceConfig.getSentUrl();
		try {
			this.httpClient = new AsyncHttpClient();
		} catch (IOReactorException e) {
			e.printStackTrace();
			throw new RuntimeException("Instantiation bean exception." + ExceptionUtils.getStackTrace(e));
		}
	}

	public void run() throws Exception {
		// startSaiGeGPS();
		startJuheMobile();
		startBaiduMobile();
		startSogouMobile();
	}

	// 聚合手机标签
	private void startJuheMobile() throws Exception {
		AbstractPageProcessor processor = new JuheMobileProcessor();
		LogPipeline pipeline = new LogPipeline(JUHE_MOBILE_LOG_NAME);
		String targetUrl = Joiner.on(File.separator).join(url, ServiceConfig.getJuheMobilePath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline).setDownloader(new RestfulDownloader())
				.thread(MAX_SIZE + CORE_SIZE).setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(JUHE_MOBILE_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE));
		final AbstractScheduler scheduler = new JuheMobileScheduler(spider, RMQ_JUHE_MOBILE_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start JuheMobileProcessor finished. " + spider.toString());
	}

	// 百度手机号标签
	private void startBaiduMobile() throws Exception {
		AbstractPageProcessor processor = new BaiduMobileProcessor();
		FilePipeline pipeline = new LocalFilePipeline(FILE_PATH);
		String targetUrl = Joiner.on(File.separator).join(url, ServiceConfig.getBaiduMobilePath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline).thread(MAX_SIZE + CORE_SIZE)
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(BAIDU_MOBILE_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE));
		final AbstractScheduler scheduler = new BaiduMobileScheduler(spider, RMQ_BAIDU_MOBILE_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start BaiduMobileProcessor finished. " + spider.toString());
	}

	// 赛格GPS数据
	private void startSaiGeGPS() throws Exception {
		final Spider spider = Spider.create(new SaiGeGPSProcessor()).setDownloader(new RestfulDownloader())
				.addPipeline(new LogPipeline(GPS_LOG_NAME)).setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(SAIGE_GPS_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener));
		spider.setExecutorService(DEFAULT_THREAD_POOL);
		final AbstractScheduler scheduler = new SaiGeGPSScheduler(spider, getPullMsgTask(GPS_RPC_QUEUE_ID));
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start SaiGeGPSProcessor finished. " + spider.toString());
	}

	// sogou 手机
	private void startSogouMobile() throws Exception {
		LocalFilePipeline pipeline = new LocalFilePipeline(FILE_PATH);
		String targetUrl = Joiner.on(File.separator).join(url, ServiceConfig.getSogouMobilePath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(new SogouMobileProcessor()).addPipeline(htmlPipeline)
				.thread(MAX_SIZE + CORE_SIZE).setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(SOGOU_MOBILE_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE));
		final AbstractScheduler scheduler = new SogouMobileScheduler(spider, RMQ_SOGOU_MOBILE_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start SogouMobileProcessor finished. " + spider.toString());

	}

}
