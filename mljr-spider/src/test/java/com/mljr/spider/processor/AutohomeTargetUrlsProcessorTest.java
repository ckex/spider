package com.mljr.spider.processor;

import com.mljr.spider.downloader.MljrPhantomJSDownloader;
import com.mljr.spider.storage.LocalFilePipeline;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;

/**
 * Created by fulin on 2017/3/5.
 */
public class AutohomeTargetUrlsProcessorTest {
  public static void main(String[] args){
    MljrPhantomJSDownloader phantomDownloader = new MljrPhantomJSDownloader().setRetryNum(1);
    AutohomeTargetUrlsProcessor  tencentCarNetworkProcessor = new AutohomeTargetUrlsProcessor();
    FilePipeline pipeline = new LocalFilePipeline("D:\\clawpage\\Test");
    Spider spider = Spider.create(tencentCarNetworkProcessor )
            .setDownloader(phantomDownloader)
            .addPipeline(new ConsolePipeline()).addPipeline(pipeline);
    spider.addUrl("http://car.autohome.com.cn/price/brand-33.html");
    spider.thread(1);
    spider.run();
  }
}
