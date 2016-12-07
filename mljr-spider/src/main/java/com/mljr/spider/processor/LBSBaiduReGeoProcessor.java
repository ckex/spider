package com.mljr.spider.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * Created by xi.gao
 * Date:2016/12/7
 */
public class LBSBaiduReGeoProcessor extends AbstractPageProcessor{

    private Site site=Site.me().setDomain("lbsyun.baidu.com")
            .setSleepTime(1200).setRetrySleepTime(4500).setRetryTimes(3)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");


    @Override
    public void process(Page page) {

        String json=page.getJson().get();

        JSONObject jsonObject=JSON.parseObject(json);

        Integer status=jsonObject.getInteger("status");

        if(null!=status && 0==status.intValue()){
            page.putField(page.getUrl().get(),json);
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("lbs baidu regeo request.url:{},json:{}", page.getRequest().getUrl(), json);
        }

    }

    @Override
    public Site getSite() {
        return site;
    }
}
