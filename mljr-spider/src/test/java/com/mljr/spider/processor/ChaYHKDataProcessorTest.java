package com.mljr.spider.processor;

import com.google.common.collect.Maps;
import com.mljr.spider.storage.LocalFilePipeline;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.Map;

/**
 * Created by xi.gao Date:2016/12/5
 */
public class ChaYHKDataProcessorTest {

  private static final String URL = "http://cha.yinhangkadata.com/";

  private static final String PATH = "/data/test_html";

  public static void main(String[] args) {

    Request request = new Request(URL);

    request.setMethod(HttpConstant.Method.POST);

    NameValuePair[] values = new NameValuePair[1];

    values[0] = new BasicNameValuePair("card", "6222021001085329875");

    Map<String, Object> nameValuePair = Maps.newHashMap();

    nameValuePair.put("nameValuePair", values);

    request.setExtras(nameValuePair);

    Spider spider = Spider.create(new ChaYHKDataProcessor()).setDownloader(new HttpClientDownloader()).addPipeline(new LocalFilePipeline(PATH))
        .addPipeline(new ConsolePipeline()).addRequest(request);
    spider.runAsync();

  }
}
