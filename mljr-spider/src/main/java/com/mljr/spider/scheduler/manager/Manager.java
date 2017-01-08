/**
 * 
 */
package com.mljr.spider.scheduler.manager;

import java.util.Date;

import com.mljr.spider.processor.*;
import com.mljr.spider.scheduler.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.nio.reactor.IOReactorException;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.mljr.constant.DomainConstant;
import com.mljr.spider.downloader.QQPhantomJSDownloader;
import com.mljr.spider.downloader.RestfulDownloader;
import com.mljr.spider.http.AsyncHttpClient;
import com.mljr.spider.listener.DownloaderSpiderListener;
import com.mljr.spider.listener.ProcessListener;
import com.mljr.spider.listener.StatusCodeListener;
import com.mljr.spider.storage.BitautoLocalFilePipeline;
import com.mljr.spider.storage.HttpPipeline;
import com.mljr.spider.storage.LocalFilePipeline;
import com.mljr.spider.storage.LogPipeline;
import com.rabbitmq.client.AMQP.Basic.Return;
import com.sun.jna.Native.ffi_callback;
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
	
	private final ProcessFactory fac = p -> {
		p.addPageProcessListener(new ProcessListener());
		return p;
	};

	@FunctionalInterface
	interface ProcessFactory {
		AbstractPageProcessor create(AbstractPageProcessor process);
	}

	public void run() throws Exception {
		System.out.println(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss:SSS") + ",Runing ...");
		startSaiGeGPS();
		startGuishuShowji();
		startIP138();
		startHuoche114();
		startJuheMobile();
		startBaiduMobile();
		startSogouMobile();
		startGuabuBankCard();
		startHuoChePiaoBankCard();
		startCha67BankCard();
		startYinHangKaBankCard();
		startChaYHKBankCard();
		startLBSAMapGeo();
		startLBSBaiduGeo();
		startBlackIdCard();

		//判断天眼查是否开启
		if("1".equals(ServiceConfig.isStartTianYanChaOff())){
			startTianyancha();
		}

		startBitauto();
		startQQZoneIndex();
	}

	// 赛格GPS数据
	private void startSaiGeGPS() throws Exception {
		AbstractPageProcessor processor = fac.create(new SaiGeGPSProcessor());
		LogPipeline logPipeline=new LogPipeline(GPS_LOG_NAME);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getSaiGeGPSPath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, logPipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline).thread(1)
				.setDownloader(new RestfulDownloader())
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(SAIGE_GPS_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener, new StatusCodeListener(DomainConstant.DOMAIN_SAIGE_GPS)));
		spider.setExecutorService(DEFAULT_THREAD_POOL);
		final AbstractScheduler scheduler = new SaiGeGPSScheduler(spider, GPS_RPC_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start startSaiGeGPS finished. " + spider.toString());
	}

	// 聚合手机标签
	private void startJuheMobile() throws Exception {
		AbstractPageProcessor processor =fac.create(new JuheMobileProcessor());
		LogPipeline pipeline = new LogPipeline(JUHE_MOBILE_LOG_NAME);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getJuheMobilePath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline).setDownloader(new RestfulDownloader())
				.thread(MAX_SIZE + CORE_SIZE).setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(JUHE_MOBILE_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener, new StatusCodeListener(DomainConstant.DOMAIN_JUHE)));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_JUHE_MOBILE_QUEUE_ID));
		final AbstractScheduler scheduler = new JuheMobileScheduler(spider, RMQ_JUHE_MOBILE_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start JuheMobileProcessor finished. " + spider.toString());
	}

	// 百度手机号标签
	private void startBaiduMobile() throws Exception {
		AbstractPageProcessor processor = fac.create(new BaiduMobileProcessor());
		FilePipeline pipeline = new LocalFilePipeline(FILE_PATH);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getBaiduMobilePath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline).thread(MAX_SIZE + CORE_SIZE)
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(BAIDU_MOBILE_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener, new StatusCodeListener(DomainConstant.DOMAIN_BAIDU_MOBILE)));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_BAIDU_MOBILE_QUEUE_ID));
		final AbstractScheduler scheduler = new BaiduMobileScheduler(spider, RMQ_BAIDU_MOBILE_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start BaiduMobileProcessor finished. " + spider.toString());
	}

	// sogou 手机
	private void startSogouMobile() throws Exception {
		AbstractPageProcessor processor = fac.create(new SogouMobileProcessor());
		LocalFilePipeline pipeline = new LocalFilePipeline(FILE_PATH);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getSogouMobilePath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline)
				.thread(1).setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(SOGOU_MOBILE_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener,new StatusCodeListener(DomainConstant.DOMAIN_SOGOU)));
		spider.setExecutorService(newThreadPool(1, 1, RMQ_SOGOU_MOBILE_QUEUE_ID));
		final AbstractScheduler scheduler = new SogouMobileScheduler(spider, RMQ_SOGOU_MOBILE_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start SogouMobileProcessor finished. " + spider.toString());

	}

	// IP138
	private void startIP138() throws Exception {
		AbstractPageProcessor processor = fac.create(new IP138Processor());
		LocalFilePipeline pipeline = new LocalFilePipeline(FILE_PATH);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getIP138Path());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline).thread(MAX_SIZE + CORE_SIZE)
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(IP138_MOBILE_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener, new StatusCodeListener(DomainConstant.DOMAIN_IP138)));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_IP138_MOBILE_QUEUE_ID));
		final AbstractScheduler scheduler = new IP138Scheduler(spider, RMQ_IP138_MOBILE_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start IP138Processor finished. " + spider.toString());

	}

	// http://www.114huoche.com/shouji/1840406
	private void startHuoche114() throws Exception {
		AbstractPageProcessor processor = fac.create(new Huoche114Processor());
		LocalFilePipeline pipeline = new LocalFilePipeline(FILE_PATH);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getHuoche114Path());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline)
				.thread(MAX_SIZE + CORE_SIZE).setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(HUOCHE114_MOBILE_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener, new StatusCodeListener(DomainConstant.DOMAIN_114HUOCHE)));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_HUOCHE114_MOBILE_QUEUE_ID));
		final AbstractScheduler scheduler = new Huoche114Scheduler(spider, RMQ_HUOCHE114_MOBILE_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start Huoche114Processor finished. " + spider.toString());

	}

	// http://guishu.showji.com/search.htm?m=1390000
	private void startGuishuShowji() throws Exception {
		AbstractPageProcessor processor = fac.create(new GuishuShowjiProcessor());
		LocalFilePipeline pipeline = new LocalFilePipeline(FILE_PATH);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getGuishuShowjiPath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline)
				.thread(MAX_SIZE + CORE_SIZE).setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(GUISHU_MOBILE_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener, new StatusCodeListener(DomainConstant.DOMAIN_GUISHU_SHOWJI)));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_GUISHUSHOWJI_MOBILE_QUEUE_ID));
		final AbstractScheduler scheduler = new GuishuShowjiScheduler(spider, RMQ_GUISHUSHOWJI_MOBILE_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start GuishuShowjiProcessor finished. " + spider.toString());

	}

	// http://www.tianyancha.com/search/%s.json
	private void startTianyancha() throws Exception {
		AbstractPageProcessor processor = fac.create(new TianyanchaProcessor());
		LocalFilePipeline pipeline = new LocalFilePipeline(FILE_PATH);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getTianyanchaPath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline)
				.thread(1).setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(TIANYANCHA_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener, new StatusCodeListener(DomainConstant.DOMAIN_TIANYANCHA)));
		spider.setExecutorService(newThreadPool(1, 1, RMQ_TIANYANCHA_QUEUE_ID));
		final AbstractScheduler scheduler = new TianyanchaScheduler(spider, RMQ_TIANYANCHA_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start TianyanchaProcessor finished. " + spider.toString());

	}

	// http://price.bitauto.com/mb9/
	public void startBitauto() throws Exception {
		BitautoLocalFilePipeline localFilePipeline = new BitautoLocalFilePipeline(FILE_PATH);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getBitautoPath());
		Pipeline httpPipeline = new HttpPipeline(targetUrl, this.httpClient, localFilePipeline);
		final Spider spider = Spider.create(fac.create(new BitautoProcessor()))
				.addPipeline(httpPipeline)
				.addPipeline(localFilePipeline)
				.thread(MAX_SIZE + CORE_SIZE).setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(BITAUTO_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener, new StatusCodeListener(DomainConstant.DOMAIN_BITAUTO)));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_BITAUTO_QUEUE_ID));
		final AbstractScheduler scheduler = new BitautoScheduler(spider, RMQ_BITAUTO_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start BitautoProcessor finished. " + spider.toString());

	}

	//http://www.guabu.com/api/bank/?cardid=62284819061
	private void startGuabuBankCard() throws Exception {
		AbstractPageProcessor processor = fac.create(new GuabuBankCardProcessor());
		LogPipeline pipeline = new LogPipeline(GUABU_BANK_CARD_LOG_NAME);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getGuabuBankCardPath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline).thread(MAX_SIZE + CORE_SIZE)
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(GUABU_BANK_CARD_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener, new StatusCodeListener(DomainConstant.DOMAIN_GUABU)));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_GUABU_BANK_CARD_QUEUE_ID));
		final AbstractScheduler scheduler = new GuabuBankCardScheduler(spider, RMQ_GUABU_BANK_CARD_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start startGuabaBankCard finished. " + spider.toString());
	}

	//http://www.huochepiao.com/search/bank/?bankid=6225881282879179
	private void startHuoChePiaoBankCard() throws Exception {
		AbstractPageProcessor processor = fac.create(new HuoChePiaoProcessor());
		LogPipeline pipeline = new LogPipeline(HCP_BANK_CARD_LOG_NAME);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getHuoChePiaoBankCardPath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline).thread(MAX_SIZE + CORE_SIZE)
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(HUOCHEPIAO_BANK_CARD_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener, new StatusCodeListener(DomainConstant.DOMAIN_HUOCHEPIAO)));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_HCP_BANK_CARD_QUEUE_ID));
		final AbstractScheduler scheduler = new HuoChePiaoScheduler(spider, RMQ_HCP_BANK_CARD_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start startHuoChePiaoBankCard finished. " + spider.toString());
	}

	//http://www.67cha.com 银行卡
	private void startCha67BankCard() throws Exception {
		AbstractPageProcessor processor = fac.create(new Cha67BankCardProcessor());
		LogPipeline pipeline = new LogPipeline(CHA67_BANK_CARD_LOG_NAME);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getCha67BankCardPath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline).thread(MAX_SIZE + CORE_SIZE)
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(CHA67_BANK_CARD_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener, new StatusCodeListener(DomainConstant.DOMAIN_67CHA)));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_CHA67_BANK_CARD_QUEUE_ID));
		final AbstractScheduler scheduler = new Cha67BankCardScheduler(spider, RMQ_CHA67_BANK_CARD_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start startCha67BankCard finished. " + spider.toString());
	}

	//http://yinhangka.388g.com 银行卡
	private void startYinHangKaBankCard() throws Exception {
		AbstractPageProcessor processor = fac.create(new YinHangKa388Processor());
		LogPipeline pipeline = new LogPipeline(YHK388_BANK_CARD_LOG_NAME);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getYinHangKaBankCardPath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline).thread(MAX_SIZE + CORE_SIZE)
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(YHK388_BANK_CARD_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener, new StatusCodeListener(DomainConstant.DOMAIN_YINHANGKA_388G)));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_YHK388_BANK_CARD_QUEUE_ID));
		final AbstractScheduler scheduler = new YinHangKa388Scheduler(spider, RMQ_YHK388_BANK_CARD_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start startYinHangKaBankCard finished. " + spider.toString());
	}

	//cha.yinhangkadata.com 银行卡
	private void startChaYHKBankCard() throws Exception {
		AbstractPageProcessor processor = fac.create( new ChaYHKDataProcessor());
		LogPipeline pipeline = new LogPipeline(CHAYHK_BANK_CARD_LOG_NAME);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getChaYHKBankCardPath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline).thread(MAX_SIZE + CORE_SIZE)
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(CHAYHK_BANK_CARD_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener, new StatusCodeListener(DomainConstant.DOMAIN_CHA_YINHANGKADATA)));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_CHAYHK_BANK_CARD_QUEUE_ID));
		final AbstractScheduler scheduler = new ChaYHKDataScheduler(spider, RMQ_CHAYHK_BANK_CARD_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start startChaYHKBankCard finished. " + spider.toString());
	}

	//lbs amap
	private void startLBSAMapGeo() throws Exception {
		AbstractPageProcessor processor = fac.create(new LBSAMapGeoProcessor());
		LogPipeline pipeline = new LogPipeline(LBS_AMAP_GEO_LOG_NAME);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getLBSAMapGeoPath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline).thread(MAX_SIZE + CORE_SIZE)
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(LBS_AMAP_GEO_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener, new StatusCodeListener(DomainConstant.DOMAIN_LBS_AMAP)));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_LBS_AMAP_GEO_QUEUE_ID));
		final AbstractScheduler scheduler = new LBSAMapGeoScheduler(spider, RMQ_LBS_AMAP_GEO_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start startLBSAMapGeo finished. " + spider.toString());
	}

	//lbs amap
	private void startLBSAMapReGeo() throws Exception {
		AbstractPageProcessor processor = fac.create(new LBSAMapReGeoProcessor());
		LogPipeline pipeline = new LogPipeline(LBS_AMAP_REGEO_LOG_NAME);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getLBSAMapReGeoPath());
//		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(pipeline).thread(MAX_SIZE + CORE_SIZE)
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(LBS_AMAP_REGEO_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener, new StatusCodeListener(DomainConstant.DOMAIN_LBS_AMAP_RE)));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_LBS_AMAP_REGEO_QUEUE_ID));
		final AbstractScheduler scheduler = new LBSAMapReGeoScheduler(spider, RMQ_LBS_AMAP_REGEO_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start startLBSAMapReGeo finished. " + spider.toString());
	}

	//lbs baidu geo
	private void startLBSBaiduGeo() throws Exception {
		AbstractPageProcessor processor = fac.create(new LBSBaiduGeoProcessor());
		LogPipeline pipeline = new LogPipeline(LBS_BAIDU_GEO_LOG_NAME);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getLBSBaiduGeoPath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline).thread(MAX_SIZE + CORE_SIZE)
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(LBS_BAIDU_GEO_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener, new StatusCodeListener(DomainConstant.DOMAIN_LBSYUN_BAIDU)));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_LBS_BAIDU_GEO_QUEUE_ID));
		final AbstractScheduler scheduler = new LBSBaiduGeoScheduler(spider, RMQ_LBS_BAIDU_GEO_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start startLBSBaiduGeo finished. " + spider.toString());
	}

	//lbs baidu regeo
	private void startLBSBaiduReGeo() throws Exception {
		AbstractPageProcessor processor = fac.create(new LBSBaiduReGeoProcessor());
		LogPipeline pipeline = new LogPipeline(LBS_BAIDU_REGEO_LOG_NAME);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getLBSBaiduReGeoPath());
//		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(pipeline).thread(MAX_SIZE + CORE_SIZE)
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(LBS_BAIDU_REGEO_LISTENER_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener, new StatusCodeListener(DomainConstant.DOMAIN_LBSYUN_BAIDU_RE)));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_LBS_BAIDU_REGEO_QUEUE_ID));
		final AbstractScheduler scheduler = new LBSBaiduReGeoScheduler(spider, RMQ_LBS_BAIDU_REGEO_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start startLBSBaiduReGeo finished. " + spider.toString());
	}

	//身份证黑名单数据
	private void startBlackIdCard() throws Exception {
		AbstractPageProcessor processor = fac.create(new GxskyProcessor());
		LogPipeline pipeline = new LogPipeline(BLACK_IDCARD_LOG_NAME);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getBlackIdCardPath());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline).thread(5)
				.setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(GXSKY_BLACKID_CARD_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener));
		spider.setExecutorService(newThreadPool(1, 1, RMQ_LBS_BLACK_IDCARD_ID));
		final AbstractScheduler scheduler = new BlackIdCardScheduler(spider, RMQ_LBS_BLACK_IDCARD_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start GxskyProcessorProcessor finished. " + spider.toString());
	}

	//QQ空间首页　
	private void startQQZoneIndex() throws Exception{
		AbstractPageProcessor processor=fac.create(new QQZoneIndexProcessor());
		LocalFilePipeline pipeline = new LocalFilePipeline(FILE_PATH);
		String targetUrl = Joiner.on("").join(url, ServiceConfig.getQQZoneIndex());
		Pipeline htmlPipeline = new HttpPipeline(targetUrl, this.httpClient, pipeline);
		final Spider spider = Spider.create(processor).addPipeline(htmlPipeline)
				.setDownloader(new QQPhantomJSDownloader())
				.thread(MAX_SIZE + CORE_SIZE).setExitWhenComplete(false);
		SpiderListener listener = new DownloaderSpiderListener(QQZONE_INDEX_LOG_NAME);
		spider.setSpiderListeners(Lists.newArrayList(listener, new StatusCodeListener(DomainConstant.DOMAIN_QQZONE_INDEX)));
		spider.setExecutorService(newThreadPool(CORE_SIZE, MAX_SIZE, RMQ_QQZONE_INDEX_QUEUE_ID));
		final AbstractScheduler scheduler = new QQZoneIndexScheduler(spider, RMQ_QQZONE_INDEX_QUEUE_ID);
		spider.setScheduler(scheduler);
		spider.runAsync();
		logger.info("Start startQQZoneIndex finished. " + spider.toString());
	}
}
