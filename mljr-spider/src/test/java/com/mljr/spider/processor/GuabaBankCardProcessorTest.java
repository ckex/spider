package com.mljr.spider.processor;

import com.mljr.spider.storage.LocalFilePipeline;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by xi.gao Date:2016/12/5
 */
public class GuabaBankCardProcessorTest {

  private static final String URL = "http://www.guabu.com/api/bank/?cardid=%s";

  private static final String PATH = "/data/test_html";

  public static void main(String[] args) {

    String url = String.format(URL, "62284819061");

    Pipeline pipeline = new LocalFilePipeline(PATH);

    Spider spider = Spider.create(new GuabuBankCardProcessor()).addPipeline(pipeline).addPipeline(new ConsolePipeline()).addUrl(url);
    spider.runAsync();

  }

}
