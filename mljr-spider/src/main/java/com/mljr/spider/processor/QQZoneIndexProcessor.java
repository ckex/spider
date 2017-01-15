package com.mljr.spider.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mljr.utils.QQUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by gaoxi on 2017/1/5.
 * QQ空间首页解析
 */
public class QQZoneIndexProcessor extends AbstractPageProcessor {

    private static   Site site = Site.me().setDomain("qqzone.index")
            .setSleepTime(3000).setRetrySleepTime(3000).setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36")
            .addHeader("Cookie","pgv_pvi=7619716096; pgv_si=s3455289344; pgv_pvid=3348108335; pgv_info=ssid=s1200344877; ptisp=ctc; pt2gguin=o0543109152; uin=o0543109152; skey=@4nqjh9lVE; RK=kdcS5dqbep; ptcz=7570452e9ddf6fe2ba8cd570e121ba660a84c6d2c95a726ba5bfb5a816db28d1; p_uin=o0543109152; p_skey=m5*Ob4yHYwzskabhTtQrNE3WETta6W-UY*442PG5sjM_; pt4_token=fYMSHk05LD5TgrEsJyV8gkPL-RbWu3UBALvTHDv0HMc_; Loading=Yes; QZ_FE_WEBP_SUPPORT=0; cpu_performance_v8=17; __Q_w_s__QZN_TodoMsgCnt=1");

//    public QQZoneIndexProcessor() {
//        super(site);
//    }

    @Override
    boolean onProcess(Page page) {
        System.out.println(page.getHtml().toString());
        return false;
    }

    public QQZoneIndexProcessor() {
        super(site);
    }


//    @Override
//    public void process(Page page) {
////        String json= QQUtils.getJsonFromJsonp(page.getRawText());
////
////        JSONObject object=JSON.parseObject(json);
//
//        System.out.println(page.getHtml().toString());
//    }
//
//    @Override
//    public Site getSite() {
//        return site;
//    }

//    @Override
//    boolean onProcess(Page page) {
//        String json= QQUtils.getJsonFromJsonp(page.getRawText());
//
//        JSONObject object=JSON.parseObject(json);
//
//        System.out.println();
//
//        r
//
// eturn true;
//    }
}
