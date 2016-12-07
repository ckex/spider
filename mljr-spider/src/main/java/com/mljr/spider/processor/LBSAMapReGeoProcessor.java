package com.mljr.spider.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * Created by xi.gao
 * Date:2016/12/6
 * 高德地图:根据经纬度得出地址信息
 */
public class LBSAMapReGeoProcessor extends AbstractPageProcessor {

    private Site site = Site.me().setDomain("lbs.amap.com")
            .setRetrySleepTime(1500).setRetryTimes(3)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

    private static final String SUCCESS = "1";

    @Override
    public void process(Page page) {
        String json = page.getJson().get();
        JSONObject jsonObject = JSON.parseObject(json);
        String status = jsonObject.getString("status");
        if (StringUtils.isNotEmpty(status) && SUCCESS.equalsIgnoreCase(status)) {
            page.putField(page.getUrl().get(), json);
            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("lbs amap regeo request.url:{},json:{}", page.getRequest().getUrl(), json);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
