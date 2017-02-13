package com.mljr.spider.processor;

import com.google.gson.internal.LinkedTreeMap;
import com.mljr.utils.QQUtils;
import com.mljr.utils.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by gaoxi on 2017/1/5.
 * QQ空间首页解析
 */
public class QQZoneIndexProcessor extends AbstractPageProcessor {

    private Site site = Site.me().setDomain("qqzone.index")
            .setSleepTime(2000).setRetrySleepTime(3000).setRetryTimes(3);

    @Override
    boolean onProcess(Page page) {
        try {
            if (StringUtils.isBlank(page.getRawText())) {
                logger.info("qq shuoshuo download page content is empty.url:{}", page.getRequest().getUrl());
                return true;
            }
            if (RandomUtils.randomPrint(10)) {
                logger.info("qq shuoshuo download page content:{}", page.getRawText());
            }
            Integer start = Integer.parseInt(getKeyByRequestUrl(page.getRequest().getUrl(), "start"));
            String hostuin = getKeyByRequestUrl(page.getRequest().getUrl(), "hostuin");
            String json = extract(page.getRawText());
            LinkedTreeMap treeMap = QQUtils.convert(json);
            if (null == treeMap) {
                logger.warn("qq shuoshuo jsonp data convert json failure.url:{}", page.getRequest().getUrl());
                return false;
            }
            double code = Math.abs(Double.parseDouble(treeMap.get("code").toString()));
            if (code == 0) { //成功
                logger.info("qq shuoshuo success.url{}", page.getRequest().getUrl());
                LinkedTreeMap<String, Object> dataTreeMap = (LinkedTreeMap<String, Object>) treeMap.get("data");
                List<Object> friendDataList = (List<Object>) dataTreeMap.get("friend_data");
                StringBuilder builder = new StringBuilder("<html><head></head><title></title><body>");
                if (null != friendDataList && friendDataList.size() > 0) {
                    friendDataList.forEach(object -> {
                        if (object instanceof LinkedTreeMap) {
                            LinkedTreeMap<String, Object> friendDataTreeMap = (LinkedTreeMap<String, Object>) object;
                            String result = replace(friendDataTreeMap.get("html").toString());
                            result = StringUtils.isNoneBlank(result) ? StringUtils.replaceEach(result.trim(), new String[]{"&gt;"}, new String[]{">"}) : "";
                            builder.append(result);
                        }
                    });
                }
                builder.append("</body></html>");
                page.putField("", builder.toString());
                //分页
                if (start <= QQUtils.QQ_DEFAULT_PAGE && (null != friendDataList && friendDataList.size() > 0)) {
                    logger.warn("qq shuoshuo page url:{}", page.getRequest().getUrl());
                    String page_url = String.format(QQUtils.QQ_INDEX_URL, hostuin, start + 1);
                    page.addTargetRequest(new Request(page_url));
                }

            } else if (code == 4001) {//未登陆
                logger.warn("qq shuoshuo no login.qq_result_code:{},url:{}", code, page.getRequest().getUrl());
                String url = String.format(QQUtils.QQ_INDEX_URL, hostuin, start);
                page.addTargetRequest(new Request(url));
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
        String jsonp = str.replace("<html><head></head><body><pre style=\"word-wrap: break-word; white-space: pre-wrap;\">", "").replace("</pre></body></html>", "");
        jsonp = jsonp.replace("_Callback(", "");
        return jsonp.substring(0, jsonp.length() - 2);
    }

    private String replace(String str) {
        String html = StringUtils.replaceEach(str, new String[]{"x3C", "x22"}, new String[]{"<", "\""});
        return html;
    }

    private String getKeyByRequestUrl(String url, String key) {
        List<NameValuePair> params = URLEncodedUtils.parse(url, Charset.forName(UTF_8));
        for (NameValuePair nameValuePair : params) {
            if (StringUtils.equalsIgnoreCase(nameValuePair.getName(), key))
                return nameValuePair.getValue().trim();
        }
        return null;
    }
}
