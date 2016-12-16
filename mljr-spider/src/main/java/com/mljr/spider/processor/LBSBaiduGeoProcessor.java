package com.mljr.spider.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mljr.spider.util.KeyCacheUtils;
import com.mljr.spider.vo.JSONTransferVO;
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

    private Site site = Site.me().setDomain("lbsyun.baidu.com")
            .setSleepTime(1200).setRetrySleepTime(4500).setRetryTimes(3)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");


    @Override
    public void process(Page page) {

        String json = page.getJson().get();

        JSONObject jsonObject = JSON.parseObject(json);

        Integer status = jsonObject.getInteger("status");

        if (null != status && 0 == status.intValue()) {
            JSONTransferVO transferVO = new JSONTransferVO();
            transferVO.setUrl(page.getUrl().get());
            transferVO.setContext(jsonObject);
            page.putField("", JSON.toJSON(transferVO));
            return;
        }

        if (4 == status) { //表示key用完

            String baidu_key = "";

            List<NameValuePair> params = URLEncodedUtils.parse(page.getUrl().get(), Charset.forName(UTF_8));
            for (NameValuePair nameValuePair : params) {
                if (StringUtils.equalsIgnoreCase(nameValuePair.getName(), "ak")) {
                    baidu_key = nameValuePair.getValue().trim();
                    return;
                }
            }
            KeyCacheUtils.setInValidKey(KeyCacheUtils.LBSKEY.BAIDU, baidu_key, false);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("lbs baidu geo request.url:{},json:{}", page.getRequest().toString(), json);
        }

    }

    @Override
    public Site getSite() {
        return site;
    }
}
