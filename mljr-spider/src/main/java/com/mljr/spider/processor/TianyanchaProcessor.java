package com.mljr.spider.processor;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.util.List;

public class TianyanchaProcessor extends AbstractPageProcessor {

    private Site site = Site.me().setDomain("www.tianyancha.com")
            .addHeader("loop", "null")
            .addHeader("Accept","application/json, text/plain, */*")
            .setRetrySleepTime(1500).setRetryTimes(3).setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

    public TianyanchaProcessor() {
        super();
    }

    @Override
    public void process(Page page) {
        String url = page.getUrl().toString();
        if(StringUtils.isBlank(url)){
            return;
        }
        String companyName= url.substring(url.lastIndexOf("/")+1,url.lastIndexOf(".json"));
        String copyright_url = String.format("http://www.tianyancha.com/extend/getCopyrightList.json?companyName=%s&pn=1&ps=100",companyName);
        String employment_url = String.format("http://www.tianyancha.com/extend/getEmploymentList.json?companyName=%s&pn=1&ps=100",companyName);
        String news_url = String.format("http://www.tianyancha.com/getnews/%s.json",companyName);

        page.addTargetRequest(copyright_url);
        page.addTargetRequest(employment_url);
        page.addTargetRequest(news_url);
        if (url.contains("www.tianyancha.com/search")) {
            List<String> ids = new JsonPathSelector("$.data[*].id").selectList(page.getRawText());
            if (CollectionUtils.isNotEmpty(ids)) {
                for (String id : ids) {
                    String tm_url = String.format("http://www.tianyancha.com/tm/getTmList.json?id=%s&pageNum=1&ps=100",id);
                    page.addTargetRequest(tm_url);
                }
            }
        } else {
            // handle target page
            if(url.contains("getCopyrightList")){
                page.putField("著作权",page.getJson());
            }else if(url.contains("getEmploymentList.json")){
                page.putField("招聘信息",page.getJson());
            }else if(url.contains("getnews")){
                page.putField("新闻",page.getJson());
            }else if(url.contains("getTmList")){
                page.putField("商标",page.getJson());
            }
        }
    }

    @Override
    public Site getSite() {
        return this.site;
    }

    public static void main(String[] args) {
        Spider.create(new TianyanchaProcessor())
                .addUrl("http://www.tianyancha.com/search/%E4%B8%8A%E6%B5%B7%E4%BA%91%E8%B4%9D%E7%BD%91%E7%BB%9C%E7%A7%91%E6%8A%80%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8.json")
                .addPipeline(new ConsolePipeline()).addPipeline(new JsonFilePipeline("/data/html")).run();

    }

}
