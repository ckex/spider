package com.mljr.spider.processor;

import com.google.gson.internal.LinkedTreeMap;
import com.mljr.utils.QQUtils;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * Created by gaoxi on 2017/1/5.
 * QQ空间首页解析
 */
public class QQZoneIndexProcessor extends AbstractPageProcessor {

    private Site site = Site.me().setDomain("qqzone.index")
            .setSleepTime(3000).setRetrySleepTime(3000).setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

    @Override
    boolean onProcess(Page page) {
        try {
            String json = extract(page.getRawText());
            LinkedTreeMap treeMap = QQUtils.convert(json);
            if (null == treeMap) {
                logger.warn("qq shuoshuo jsonp data convert json failure.url:{}", page.getRequest().getUrl());
                return false;
            }
            double code = Math.abs(Double.parseDouble(treeMap.get("code").toString()));
            if (code == 0) { //成功
                LinkedTreeMap<String, Object> dataTreeMap = (LinkedTreeMap<String, Object>) treeMap.get("data");
                List<Object> friendDataList = (List<Object>) dataTreeMap.get("friend_data");
                StringBuilder builder = new StringBuilder();
                if (null != friendDataList && friendDataList.size() > 0) {
                    friendDataList.forEach(object -> {
                        if (object instanceof LinkedTreeMap) {
                            LinkedTreeMap<String, Object> friendDataTreeMap = (LinkedTreeMap<String, Object>) object;
                            String result = replace(friendDataTreeMap.get("html").toString());
                            builder.append(result);
                        }
                    });
                }
                if (StringUtils.isNotBlank(builder.toString())) {
                    Html html = new Html(builder.toString());
//                    List<Selectable> selectableList = html.xpath("//li[@class=\"f-single f-s-s\"]").nodes();
//                    selectableList.forEach(selectable -> {
//                        String shuoshuoTime = selectable.xpath("/li/div[1]/div[2]/div[2]/span[1]/text()").get();
//                        String content = selectable.xpath("/li/div[2]/div[1]/div[1]/text()").get();
//                        String ss = selectable.xpath("/li/div[2]/div[1]/div[2]").get();
//                    });
                    page.putField("", html);
                }
            } else if (code == 4001) {//未登陆
                page.addTargetRequest(page.getRequest());
            } else if (code == 5008) { //无权限
                logger.warn("qq shuoshuo no auth.qq_result_code:{},url:{}", code, page.getRequest().getUrl());
            } else {
                logger.warn("qq shuoshuo result error.qq_result_code:{},url:{}", code, page.getRequest().getUrl());
            }
        } catch (Exception e) {
            logger.error("qq shuoshuo json parse failure.url:{}", page.getRequest().getUrl(), e);
            return false;
        }
        return true;
    }

    @Override
    public Site getSite() {
        return site;
    }

    private String extract(String str) {
        String jsonp = str.replace("_Callback(", "");
        return jsonp.substring(0, jsonp.length() - 2);
    }

    private String replace(String str) {
        String html = StringUtils.replaceEach(str, new String[]{"x3C", "x22"}, new String[]{"<", "\""});
        return html;
    }
}
