/**
 * 
 */
package com.mljr.spider.processor;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

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

  protected transient static Logger logger = LoggerFactory.getLogger("warn-email");
  protected transient static Logger LOG = LoggerFactory.getLogger(SaiGeGPSProcessorTests.class);

  public static final String URL = "http://218.17.3.228:8008/mljrserver/vehicle/queryGpsInfo";

  private static final String params = "{\"callLetter\":\"\",\"flag\":true,\"sign\":\"335BB919C5476417E424FF6F0BC5AD6F\"}";

  public SaiGeGPSProcessorTests() {
    super();
  }

  public static void main(String[] args) throws InterruptedException {
    logger.error("spider test {} ", new Date());
    logger.error(MarkerFactory.getMarker("warn-email"), "预警发送：xxx");
    LOG.info("Start ... ");
    TimeUnit.MINUTES.sleep(2);
    String url = URL;
    Spider spider = Spider.create(new SaiGeGPSProcessor()).setDownloader(new RestfulDownloader()).thread(1).addPipeline(new ConsolePipeline());
    Scheduler scheduler = spider.getScheduler();
    RestfulReqeust request = new RestfulReqeust(url, params);
    request.setMethod(AbstractRequest.POST);
    scheduler.push(request, spider);
    spider.run();
  }

}
