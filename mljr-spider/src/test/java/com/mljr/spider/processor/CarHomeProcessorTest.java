package com.mljr.spider.processor;

import com.google.common.base.Joiner;
import com.mljr.spider.downloader.MljrPhantomJSDownloader;
import com.mljr.spider.storage.CarHomeNetInfoPipeline;
import org.apache.commons.io.FileUtils;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by fulin on 2017/1/9.
 */
public class CarHomeProcessorTest {

    private static final String name = "spider-dw";
    private static final AtomicInteger count = new AtomicInteger();

    protected static final ThreadPoolExecutor DEFAULT_THREAD_POOL = new ThreadPoolExecutor(5, 5, 100,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10), new ThreadFactory() {

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, Joiner.on("-").join(name, count.incrementAndGet()));
        }
    }, new ThreadPoolExecutor.CallerRunsPolicy());
     private static final String filePath = "D:\\clawpage\\car.txt";
    public static void main(String[] args){
        //处理js渲染问题
        MljrPhantomJSDownloader phantomDownloader = new MljrPhantomJSDownloader().setRetryNum(1);
        //读取本地的商标文件
       CarHomeProcessor carHomeProcessor= new CarHomeProcessor();
        try {
            List<String> urls = FileUtils.readLines(new File(filePath), "utf-8");
            Spider spider = Spider.create(carHomeProcessor)
                    .setDownloader(phantomDownloader)
                  .addPipeline(new CarHomeNetInfoPipeline()).addPipeline(new ConsolePipeline());
       /*    for (String url : urls) {
                spider.addUrl(url);
            }*/
          spider.addUrl("http://car.autohome.com.cn/config/series/692.html");
           // spider.addUrl("http://car.autohome.com.cn/config/series/692.html");
            spider.thread(1);

            spider.setExecutorService(DEFAULT_THREAD_POOL);
            spider.run();
          //  FileUtils.writeLines(new File("D:\\clawpage\\auto_home_target_urls2.txt"),carHomeProcessor.urlSet);
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
