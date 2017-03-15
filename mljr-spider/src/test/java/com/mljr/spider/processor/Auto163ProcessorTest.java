package com.mljr.spider.processor;

import com.mljr.spider.storage.CarHomeNetInfoPipeline;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by fulin on 2017/3/15.
 */
public class Auto163ProcessorTest {

    public static final ThreadPoolExecutor DEFAULT_THREAD_POOL =
            new ThreadPoolExecutor(25, 25, 100,
                    TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10), new ThreadFactory() {
              @Override
              public Thread newThread(Runnable r) {
                return new Thread(r);
              }}, new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args){
      String ss ="http://product.auto.163.com/brand/";
      Spider spider = Spider.create(new Auto163Processor())
              .addPipeline(new CarHomeNetInfoPipeline()).addPipeline(new ConsolePipeline());
              //.setPipelines(Lists.newArrayList(new FilePipeline("D:\\clawpage\\Test")));
      spider.addUrl(ss);
      spider.thread(DEFAULT_THREAD_POOL, 20);
      spider.run();

    }
}
