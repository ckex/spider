package com.mljr.spider.processor;

import com.mljr.spider.storage.CarHomeNetInfoPipeline;
import com.mljr.spider.storage.LocalFilePipeline;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;

/**
 * Created by fulin on 2017/3/3.
 */
public class TencentCarNetworkProcessorTest {

  public static void main(String[] args){
        //通过这个接口去获取所有的商标
        //http://js.data.auto.qq.com/car_public/1/manufacturer_list_json.js?_=1488525597592
        //通过下面的接口去获取每一个商标下面的分类
        //http://js.data.auto.qq.com/car_manufacturer/60/serial_list_json_baojia.js?_=1484291243255
   // MljrPhantomJSDownloader phantomDownloader = new MljrPhantomJSDownloader().setRetryNum(1);
    TencentCarNetworkProcessor tencentCarNetworkProcessor = new TencentCarNetworkProcessor();
    FilePipeline pipeline = new LocalFilePipeline("D:\\clawpage\\CarHome");
    Spider spider = Spider.create(tencentCarNetworkProcessor )
            .setDownloader(new HttpClientDownloader())
            .addPipeline(new ConsolePipeline()).addPipeline(new CarHomeNetInfoPipeline());
    //spider.addUrl("http://js.data.auto.qq.com/car_public/1/manufacturer_list_json.js?_=1488525597592");
    spider.addUrl("http://baojia.auto.qq.com/php/baojia_center.php?brandid=195");
    spider.thread(10);
    spider.run();
  }
}
