package com.mljr.spider.processor;

import com.google.common.base.Joiner;
import com.mljr.spider.storage.LocalFilePipeline;
import org.apache.commons.collections.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class TianyanchaProcessor extends AbstractPageProcessor {
    public static final String JS_PATH = System.getProperty("user.home") + System.getProperty("file.separator")
            + "get_page.js";

    private Site site = Site.me().setDomain("tianyancha.com")
            .addHeader("loop", "null")
            .addHeader("Accept", "application/json, text/plain, */*")
            .setRetrySleepTime(1500).setRetryTimes(3).setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

    public TianyanchaProcessor() {
        super();
    }

    @Override
    public synchronized void process(Page page) {
        String url = page.getUrl().toString();
        if (url != null && url.contains("www.tianyancha.com/search")) {
            List<String> ids = new JsonPathSelector("$.data[*].id").selectList(page.getRawText());
            if (CollectionUtils.isNotEmpty(ids)) {
                for (String id : ids) {
                    try {
                        String html = getAjaxContent("http://www.tianyancha.com/company/" + id);
                        page.putField("", html);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    @Override
    public synchronized Site getSite() {
        return this.site;
    }

    public static void main(String[] args) {
        Spider.create(new TianyanchaProcessor())
                .addUrl("http://www.tianyancha.com/search/%E4%B8%8A%E6%B5%B7%E4%BA%91%E8%B4%9D%E7%BD%91%E7%BB%9C%E7%A7%91%E6%8A%80%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8.json")
                .addPipeline(new ConsolePipeline()).addPipeline(new LocalFilePipeline("/data/html")).run();

    }

    public synchronized String getAjaxContent(String url) throws IOException {
        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec(Joiner.on(" ").join("/usr/local/bin/casperjs", JS_PATH, url));
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
