/**
 *
 */
package com.mljr.spider.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mljr.spider.vo.JSONTransferVO;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * @author Ckex zha </br> 2016年11月7日,下午5:20:39
 */
public class SaiGeGPSProcessor extends AbstractPageProcessor {

    private static final Site site = Site.me().setDomain("saige-gps").setCharset(UTF_8);

    @Override
    boolean onProcess(Page page) {
        String json = page.getJson().get();
        if (StringUtils.isBlank(json)) {
            logger.warn("saige-gps response no data.url:" + page.getUrl().get());
            return true;
        }
        JSONObject jsonObject = JSON.parseObject(json);
        boolean success = jsonObject.getBooleanValue("success");
        if (!success) {
            logger.warn("saige-gps request data failure.url:" + page.getUrl().get());
            return true;
        }

        JSONTransferVO jsonTransferVO = new JSONTransferVO();
        jsonTransferVO.setUrl(page.getUrl().get());
        jsonTransferVO.setContext(json);
        page.putField("", JSON.toJSON(jsonTransferVO));
        return true;
    }

    public SaiGeGPSProcessor() {
        super(site);
    }

}
