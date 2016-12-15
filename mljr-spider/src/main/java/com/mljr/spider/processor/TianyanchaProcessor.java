package com.mljr.spider.processor;

import com.google.common.base.Joiner;
import com.mljr.spider.storage.LocalFilePipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.selector.Html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public  class TianyanchaProcessor extends AbstractPageProcessor {
    public static final String JS_PATH = System.getProperty("user.home") + System.getProperty("file.separator")
            + "get_page.js";

    private Site site = Site.me().setDomain("tianyancha.com")
            .addHeader("loop", "null")
            .setSleepTime(1000*20)
            .addHeader("Accept", "application/json, text/plain, */*")
            .setRetrySleepTime(1500).setRetryTimes(3).setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:50.0) Gecko/20100101 Firefox/50.0");

    public TianyanchaProcessor() {
        super();
    }

    @Override
    public  void process(Page page) {
        try {
            String htmlStr  = getAjaxContent(page.getUrl().toString());
            Html html = new Html(htmlStr);
            List<String> requests = html.links().all();
            int i=0;
            if(requests!=null&&requests.size()>0){
                for (String request : requests) {
                    if(request.contains("http://www.tianyancha.com/company")){
                        String targetHtml  = getAjaxContent(request);
                        page.putField("",targetHtml);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("tianyancha error",e);

        }

    }


    @Override
    public Site getSite() {
        return this.site;
    }

    public static void main(String[] args) {
        Spider.create(new TianyanchaProcessor())
                .addUrl("http://www.tianyancha.com/search?key=%E5%AE%9C%E9%83%BD%E5%B8%82%E6%9E%9D%E5%9F%8E%E9%95%87%E4%B8%AD%E4%B8%BA%E8%81%94%E9%80%9A%E5%90%88%E4%BD%9C%E8%90%A5%E4%B8%9A%E5%8E%85&checkFrom=searchBox")
                .addPipeline(new ConsolePipeline()).addPipeline(new LocalFilePipeline("/data/html")).run();

    }

    public synchronized String getAjaxContent(String url) throws IOException {
        Runtime rt = Runtime.getRuntime();
        String command = Joiner.on(" ").join("phantomjs", JS_PATH, url);
        logger.info("command ================="  +  command);
        Process p = rt.exec(command);
        InputStream is = p.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuffer sbf = new StringBuffer();
        String tmp = "";
        while ((tmp = br.readLine()) != null) {
            sbf.append(tmp);
        }
//        System.out.println(sbf.toString());
        return sbf.toString();
    }

}
