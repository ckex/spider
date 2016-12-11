package com.mljr.spider.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * Created by xi.gao
 * Date:2016/12/6
 * 高德地图:根据地址信息得出经纬度
 */
public class LBSAMapGeoProcessor extends AbstractPageProcessor {

    private Site site = Site.me().setDomain("lbs.amap.com")
            .setSleepTime(1200).setRetrySleepTime(4500).setRetryTimes(3)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

    private static final String SUCCESS = "1";

    @Override
    public void process(Page page) {
        String json = page.getJson().get();
        JSONObject jsonObject = JSON.parseObject(json);
        String resultStatus = jsonObject.getString("status");
        if (StringUtils.isNotEmpty(resultStatus) && SUCCESS.equalsIgnoreCase(resultStatus)) {
            page.putField(page.getUrl().get(), json);
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("lbs amap geo request.page:{},json:{}", page.getRequest().toString(), json);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
