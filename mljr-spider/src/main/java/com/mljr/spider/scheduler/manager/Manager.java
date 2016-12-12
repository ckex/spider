/**
 * 
 */
package com.mljr.spider.scheduler.manager;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.mljr.spider.downloader.RestfulDownloader;
import com.mljr.spider.http.AsyncHttpClient;
import com.mljr.spider.listener.DownloaderSpiderListener;
import com.mljr.spider.processor.*;
import com.mljr.spider.scheduler.*;
import com.mljr.spider.storage.HttpPipeline;
import com.mljr.spider.storage.LocalFilePipeline;
import com.mljr.spider.storage.LogPipeline;
import com.ucloud.umq.common.ServiceConfig;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.nio.reactor.IOReactorException;
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
		startSaiGeGPS();
		startGuishuShowji();
		startIP138();
		startHuoche114();
		startJuheMobile();
		startBaiduMobile();
		startSogouMobile();
		startTianyancha();
		startGuabuBankCard();
		startHuoChePiaoBankCard();
		startCha67BankCard();
		startYinHangKaBankCard();
		startChaYHKBankCard();
		startLBSAMapReGeo();
		startLBSBaiduReGeo();

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
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getJuheMobilePath());
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
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getBaiduMobilePath());
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
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getSogouMobilePath());
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
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getIP138Path());
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
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getHuoche114Path());
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
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getGuishuShowjiPath());
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

	//http://www.guabu.com/api/bank/?cardid=62284819061
	private void startGuabuBankCard() throws Exception {
		AbstractPageProcessor processor = new GuabuBankCardProcessor();
		LogPipeline pipeline = new LogPipeline(GUABU_BANK_CARD_LOG_NAME);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getGuabuBankCardPath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline).thread(MAX_SIZE + CORE_SIZE)
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(GUABU_BANK_CARD_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_GUABU_BANK_CARD_QUEUE_ID));
		final AbstractScheduler scheduler = new GuabuBankCardScheduler(spider, RMQ_GUABU_BANK_CARD_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start startGuabaBankCard finished. " + spider.toString());
	}

	//http://www.huochepiao.com/search/bank/?bankid=6225881282879179
	private void startHuoChePiaoBankCard() throws Exception {
		AbstractPageProcessor processor = new HuoChePiaoProcessor();
		LogPipeline pipeline = new LogPipeline(HCP_BANK_CARD_LOG_NAME);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getHuoChePiaoBankCardPath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline).thread(MAX_SIZE + CORE_SIZE)
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(HUOCHEPIAO_BANK_CARD_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_HCP_BANK_CARD_QUEUE_ID));
		final AbstractScheduler scheduler = new HuoChePiaoScheduler(spider, RMQ_HCP_BANK_CARD_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start startHuoChePiaoBankCard finished. " + spider.toString());
	}

	//http://www.67cha.com 银行卡
	private void startCha67BankCard() throws Exception {
		AbstractPageProcessor processor = new Cha67BankCardProcessor();
		LogPipeline pipeline = new LogPipeline(CHA67_BANK_CARD_LOG_NAME);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getCha67BankCardPath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline).thread(MAX_SIZE + CORE_SIZE)
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(CHA67_BANK_CARD_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_CHA67_BANK_CARD_QUEUE_ID));
		final AbstractScheduler scheduler = new Cha67BankCardScheduler(spider, RMQ_CHA67_BANK_CARD_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start startCha67BankCard finished. " + spider.toString());
	}

	//http://yinhangka.388g.com 银行卡
	private void startYinHangKaBankCard() throws Exception {
		AbstractPageProcessor processor = new YinHangKa388Processor();
		LogPipeline pipeline = new LogPipeline(YHK388_BANK_CARD_LOG_NAME);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getYinHangKaBankCardPath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline).thread(MAX_SIZE + CORE_SIZE)
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(YHK388_BANK_CARD_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_YHK388_BANK_CARD_QUEUE_ID));
		final AbstractScheduler scheduler = new YinHangKa388Scheduler(spider, RMQ_YHK388_BANK_CARD_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start startYinHangKaBankCard finished. " + spider.toString());
	}

	//cha.yinhangkadata.com 银行卡
	private void startChaYHKBankCard() throws Exception {
		AbstractPageProcessor processor = new ChaYHKDataProcessor();
		LogPipeline pipeline = new LogPipeline(CHAYHK_BANK_CARD_LOG_NAME);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getChaYHKBankCardPath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline).thread(MAX_SIZE + CORE_SIZE)
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(CHAYHK_BANK_CARD_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_CHAYHK_BANK_CARD_QUEUE_ID));
		final AbstractScheduler scheduler = new ChaYHKDataScheduler(spider, RMQ_CHAYHK_BANK_CARD_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start startChaYHKBankCard finished. " + spider.toString());
	}

	//lbs amap
	private void startLBSAMapGeo() throws Exception {
		AbstractPageProcessor processor = new LBSAMapGeoProcessor();
		LogPipeline pipeline = new LogPipeline(LBS_AMAP_GEO_LOG_NAME);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getLBSAMapGeoPath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline).thread(MAX_SIZE + CORE_SIZE)
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(LBS_AMAP_GEO_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_LBS_AMAP_GEO_QUEUE_ID));
		final AbstractScheduler scheduler = new LBSAMapGeoScheduler(spider, RMQ_LBS_AMAP_GEO_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start startLBSAMapGeo finished. " + spider.toString());
	}

	//lbs amap
	private void startLBSAMapReGeo() throws Exception {
		AbstractPageProcessor processor = new LBSAMapReGeoProcessor();
		LogPipeline pipeline = new LogPipeline(LBS_AMAP_REGEO_LOG_NAME);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getLBSAMapReGeoPath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline).thread(MAX_SIZE + CORE_SIZE)
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(LBS_AMAP_REGEO_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_LBS_AMAP_REGEO_QUEUE_ID));
		final AbstractScheduler scheduler = new LBSAMapReGeoScheduler(spider, RMQ_LBS_AMAP_REGEO_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start startLBSAMapReGeo finished. " + spider.toString());
	}

	//lbs baidu geo
	private void startLBSBaiduGeo() throws Exception {
		AbstractPageProcessor processor = new LBSBaiduGeoProcessor();
		LogPipeline pipeline = new LogPipeline(LBS_BAIDU_GEO_LOG_NAME);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getLBSBaiduGeoPath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline).thread(MAX_SIZE + CORE_SIZE)
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(LBS_BAIDU_GEO_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_LBS_BAIDU_GEO_QUEUE_ID));
		final AbstractScheduler scheduler = new LBSBaiduGeoScheduler(spider, RMQ_LBS_BAIDU_GEO_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start startLBSBaiduGeo finished. " + spider.toString());
	}

	//lbs baidu regeo
	private void startLBSBaiduReGeo() throws Exception {
		AbstractPageProcessor processor = new LBSBaiduReGeoProcessor();
		LogPipeline pipeline = new LogPipeline(LBS_BAIDU_REGEO_LOG_NAME);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getLBSBaiduReGeoPath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline).thread(MAX_SIZE + CORE_SIZE)
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(LBS_BAIDU_REGEO_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_LBS_BAIDU_REGEO_QUEUE_ID));
		final AbstractScheduler scheduler = new LBSBaiduReGeoScheduler(spider, RMQ_LBS_BAIDU_REGEO_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start startLBSBaiduReGeo finished. " + spider.toString());
	}
}
