/**
 * 
 */
package com.mljr.spider.scheduler.manager;

import java.io.File;

import com.mljr.spider.processor.*;
import com.mljr.spider.scheduler.*;
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
import us.codecraft.webmagic.pipeline.ConsolePipeline;
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
		System.out.println("Runing ...");
//		startSaiGeGPS();
//		startGuishuShowji();
//		startIP138();
//		startHuoche114();
//		startJuheMobile();
//		startBaiduMobile();
//		startSogouMobile();
		startTianyancha();

	}

	// 赛格GPS数据
	private void startSaiGeGPS() throws Exception {
		final Spider spider = Spider.create(new SaiGeGPSProcessor()).setDownloader(new RestfulDownloader())
				.addPipeline(new LogPipeline(GPS_LOG_NAME)).setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(SAIGE_GPS_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener));
		spider.setExecutorService(DEFAULT_THREAD_POOL);
		// final AbstractScheduler scheduler = new SaiGeGPSScheduler(spider,
		// getPullMsgTask(GPS_RPC_QUEUE_ID));
		final AbstractScheduler scheduler = new SaiGeGPSScheduler(spider, GPS_RPC_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start SaiGeGPSProcessor finished. " + spider.toString());
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
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_JUHE_MOBILE_QUEUE_ID));
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
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_BAIDU_MOBILE_QUEUE_ID));
		final AbstractScheduler scheduler = new BaiduMobileScheduler(spider, RMQ_BAIDU_MOBILE_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start BaiduMobileProcessor finished. " + spider.toString());
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
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_SOGOU_MOBILE_QUEUE_ID));
		final AbstractScheduler scheduler = new SogouMobileScheduler(spider, RMQ_SOGOU_MOBILE_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start SogouMobileProcessor finished. " + spider.toString());

	}

	// IP138
	private void startIP138() throws Exception {
		LocalFilePipeline pipeline = new LocalFilePipeline(FILE_PATH);
		String targetUrl = Joiner.on(File.separator).join(url, ServiceConfig.getIP138Path());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(new IP138Processor()).addPipeline(htmlPipeline).thread(MAX_SIZE + CORE_SIZE)
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(IP138_MOBILE_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_IP138_MOBILE_QUEUE_ID));
		final AbstractScheduler scheduler = new IP138Scheduler(spider, RMQ_IP138_MOBILE_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start IP138Processor finished. " + spider.toString());

	}

	// http://www.114huoche.com/shouji/1840406
	private void startHuoche114() throws Exception {
		LocalFilePipeline pipeline = new LocalFilePipeline(FILE_PATH);
		String targetUrl = Joiner.on(File.separator).join(url, ServiceConfig.getHuoche114Path());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(new Huoche114Processor()).addPipeline(htmlPipeline)
				.thread(MAX_SIZE + CORE_SIZE).setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(HUOCHE114_MOBILE_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_HUOCHE114_MOBILE_QUEUE_ID));
		final AbstractScheduler scheduler = new Huoche114Scheduler(spider, RMQ_HUOCHE114_MOBILE_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start Huoche114Processor finished. " + spider.toString());

	}

	// http://guishu.showji.com/search.htm?m=1390000
	private void startGuishuShowji() throws Exception {
		LocalFilePipeline pipeline = new LocalFilePipeline(FILE_PATH);
		String targetUrl = Joiner.on(File.separator).join(url, ServiceConfig.getGuishuShowjiPath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(new GuishuShowjiProcessor()).addPipeline(htmlPipeline)
				.thread(MAX_SIZE + CORE_SIZE).setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(GUISHU_MOBILE_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_GUISHUSHOWJI_MOBILE_QUEUE_ID));
		final AbstractScheduler scheduler = new GuishuShowjiScheduler(spider, RMQ_GUISHUSHOWJI_MOBILE_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start GuishuShowjiProcessor finished. " + spider.toString());

	}

	// http://www.tianyancha.com/search/%s.json
	private void startTianyancha() throws Exception {
		LocalFilePipeline pipeline = new LocalFilePipeline(FILE_PATH);
//		String targetUrl = Joiner.on(File.separator).join(url, ServiceConfig.getTianyanchaPath());
//		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
//		final Spider spider = Spider.create(new TianyanchaProcessor()).addPipeline(htmlPipeline)
//				.thread(MAX_SIZE + CORE_SIZE).setExitWhenComplete(false);
 		final Spider spider = Spider.create(new TianyanchaProcessor()).addPipeline(pipeline).addPipeline(new ConsolePipeline())
				.thread(MAX_SIZE + CORE_SIZE).setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(TIANYANCHA_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_TIANYANCHA_QUEUE_ID));
		final AbstractScheduler scheduler = new TianyanchaScheduler(spider, RMQ_TIANYANCHA_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start TianyanchaProcessor finished. " + spider.toString());

	}

}
