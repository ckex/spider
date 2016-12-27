package com.mljr.spider.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mljr.spider.util.KeyCacheUtils;
import com.mljr.spider.vo.JSONTransferVO;
import con.mljr.spider.config.SiteManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by xi.gao
 * Date:2016/12/6
 * 高德地图:根据地址信息得出经纬度
 */
public class LBSAMapGeoProcessor extends AbstractPageProcessor {

    private static Site site = Site.me().setDomain("lbs.amap.com")
            .setSleepTime(1200).setRetrySleepTime(4500).setRetryTimes(3)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

    private static final String SUCCESS = "1";

    private static final String DAILY_QUERY_OVER_LIMIT = "10003";//当日限制标记
    public LBSAMapGeoProcessor() {
        super(site);
    }

    @Override
    public void process(Page page) {

        String json = page.getJson().get();

        JSONObject jsonObject = JSON.parseObject(json);

        String resultStatus = jsonObject.getString("status");

        String infocode = jsonObject.getString("infocode");

        if (logger.isDebugEnabled()) {
            logger.debug("lbs amap geo request.page:{},json:{}", page.getRequest().toString(), json);
        }

        //判断密钥是否超出请求限制
        if (StringUtils.isNotEmpty(infocode) && DAILY_QUERY_OVER_LIMIT.equalsIgnoreCase(infocode)) {

            String amap_key = getKeyByRequestUrl(page.getUrl().get());

            KeyCacheUtils.setInValidKey(KeyCacheUtils.LBSKEY.AMAP, amap_key, Boolean.FALSE);

            return;
        }

        if (StringUtils.isNotEmpty(resultStatus) && SUCCESS.equalsIgnoreCase(resultStatus)) {

            JSONTransferVO transferVO = new JSONTransferVO();

            transferVO.setUrl(page.getUrl().get());

            transferVO.setContext(jsonObject);

            page.putField("", JSON.toJSON(transferVO));
        }
    }

    @Override
    public Site getSite() {
        return SiteManager.getSiteByDomain("lbs.amap.com");
    }

    private String getKeyByRequestUrl(String url) {
        List<NameValuePair> params = URLEncodedUtils.parse(url, Charset.forName(UTF_8));
        for (NameValuePair nameValuePair : params) {
            if (StringUtils.equalsIgnoreCase(nameValuePair.getName(), "key"))
                return nameValuePair.getValue().trim();
        }
        return null;
    }
}
