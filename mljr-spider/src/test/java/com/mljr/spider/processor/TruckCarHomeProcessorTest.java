package com.mljr.spider.processor;

import com.google.common.base.Joiner;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by fulin on 2017/2/23.
 */
public class TruckCarHomeProcessorTest {
    private static final String name = "spider-dw";
    private static final AtomicInteger count = new AtomicInteger();

    protected static final ThreadPoolExecutor DEFAULT_THREAD_POOL = new ThreadPoolExecutor(1, 1, 100,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10), new ThreadFactory() {

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, Joiner.on("-").join(name, count.incrementAndGet()));
        }
    }, new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args){
        //处理js渲染问题
      //  MljrPhantomJSDownloader phantomDownloader = new MljrPhantomJSDownloader().setRetryNum(1);
        TruckCarHomeProcessor  truckCarHomeProcessor = new TruckCarHomeProcessor();
        Spider spider = Spider.create(truckCarHomeProcessor)
                .setDownloader(new HttpClientDownloader())
               .addPipeline(new ConsolePipeline());
        spider.addUrl("http://product.360che.com/");
        spider.thread(1);
        spider.setExecutorService(DEFAULT_THREAD_POOL);
        spider.run();
    }

}
