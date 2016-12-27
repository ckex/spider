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
 * Date:2016/12/7
 */
public class LBSBaiduGeoProcessor extends AbstractPageProcessor {

    private static Site site = Site.me().setDomain("lbsyun.baidu.com")
            .setSleepTime(1200).setRetrySleepTime(4500).setRetryTimes(3)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

    public LBSBaiduGeoProcessor() {
        super(site);
    }

    @Override
    public void process(Page page) {

        String json = page.getJson().get();

        JSONObject jsonObject = JSON.parseObject(json);

        Integer status = jsonObject.getInteger("status");

        if (logger.isDebugEnabled()) {
            logger.debug("lbs baidu geo request.url:{},json:{}", page.getRequest().toString(), json);
        }

        //判断密钥是否超出每天的限制
        if (null!=status && 4 == status) {

            String baidu_key =getKeyByRequestUrl(page.getUrl().get());

            KeyCacheUtils.setInValidKey(KeyCacheUtils.LBSKEY.BAIDU, baidu_key, false);

            return;
        }

        if (null != status && 0 == status.intValue()) {

            JSONTransferVO transferVO = new JSONTransferVO();

            transferVO.setUrl(page.getUrl().get());

            transferVO.setContext(jsonObject);

            page.putField("", JSON.toJSON(transferVO));
        }

    }

    @Override
    public Site getSite() {
        return SiteManager.getSiteByDomain("lbsyun.baidu.com");
    }

    private String getKeyByRequestUrl(String url) {
        List<NameValuePair> params = URLEncodedUtils.parse(url, Charset.forName(UTF_8));
        for (NameValuePair nameValuePair : params) {
            if (StringUtils.equalsIgnoreCase(nameValuePair.getName(), "ak"))
                return nameValuePair.getValue().trim();
        }
        return null;
    }
}
