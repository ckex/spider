package com.mljr.operators.service;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.mljr.operators.common.utils.JsUtils;
import com.mljr.operators.entity.LoginResponse;
import com.mljr.operators.entity.chinamobile.DatePair;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.io.IOException;
import java.util.Map;

/**
 * Created by songchi on 16/12/23.
 */
@Service
public class ChinaMobileService {
    public String getSmsCode(String telno) {
        try {
            String url = "https://sh.ac.10086.cn/loginjt?act=1&telno=" + telno;
            String jsonStr = Jsoup.connect(url).execute().body();
            String result = new JsonPathSelector("result").select(jsonStr);
            if ("0".equals(result)) {
                return new JsonPathSelector("message").select(jsonStr);
            }
            return "请求验证码失败";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    public String getJumpUrl(String url) {
        try {
            Connection.Response response = Jsoup.connect(url).execute();
            LoginResponse loginResponse = new Gson().fromJson(response.body(), LoginResponse.class);
            return JsUtils.getJumpUrl(loginResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> getCookies(String url) {
        Map<String, String> cookies = Maps.newHashMap();
        try {
            Connection.Response response = Jsoup.connect(url).execute();
            cookies = response.cookies();
            for (Map.Entry<String, String> entry : cookies.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cookies;
    }

    public Map<String, String> loginAndGetCookies(String telno, String password, String dtm) {
        String _telno = JsUtils.enString(telno);
        String _password = JsUtils.enString(password);
        String _dtm = JsUtils.enString(dtm);
        String loginUrl = "https://sh.ac.10086.cn/loginjt?act=2&telno=%s&password=%s&authLevel=5&dtm=%s&ctype=1&decode=1&source=wsyyt";
        String jumpUrl = getJumpUrl(String.format(loginUrl, _telno, _password, _dtm));
        System.out.println(jumpUrl);
        return getCookies(jumpUrl);
    }

    // 01 用户在运营商信息
    public String getUserInfo(Map<String, String> cookies) throws Exception {
        String url = "http://www.sh.10086.cn/sh/wsyyt/action?act=my.getUserName";
        return Jsoup.connect(url).cookies(cookies).execute().body();
    }

    // 02 套餐信息
    public String getPackageInfo(Map<String, String> cookies) throws Exception {
        String url = "http://www.sh.10086.cn/sh/wsyyt/action?act=my.getMusType";
        return Jsoup.connect(url).cookies(cookies).execute().body();
    }

    // 03 用户话费信息
    public String getCostInfo(Map<String, String> cookies) throws Exception {
        String url = "http://www.sh.10086.cn/sh/wsyyt/busi/historySearch.do?method=FiveBillAllNewAjax&dateTime=2016年12月&tab=tab2_15&isPriceTaxSeparate=null&showType=0&r=1487313589994";
        return Jsoup.connect(url).cookies(cookies).execute().body();
    }

    // 04 网络流量信息
    public String getFlowInfo(Map<String, String> cookies, DatePair pair) throws Exception {
        String url = "http://www.sh.10086.cn/sh/wsyyt/busi/historySearch.do?method=getFiveBillDetailAjax&billType=NEW_GPRS_NEW&startDate=%s&endDate=%s&filterfield=输入对方号码：&filterValue=&searchStr=-1&index=0&r=1487232359541&isCardNo=0&gprsType=";
        return Jsoup.connect(String.format(url, pair.getStartDate(), pair.getEndDate())).cookies(cookies).execute().body();
    }

    // 05 短信使用信息
    public String getSmsInfo(Map<String, String> cookies, DatePair pair) throws Exception {
        String url = "http://www.sh.10086.cn/sh/wsyyt/busi/historySearch.do?method=getFiveBillDetailAjax&billType=NEW_SMS&startDate=2016-12-01&endDate=2016-12-31&filterfield=输入对方号码：&filterValue=&searchStr=-1&index=0&r=1487232359541&isCardNo=0&gprsType=";
        return Jsoup.connect(url).cookies(cookies).execute().body();
    }

    // 06 通话详单
    public String getCallInfo(Map<String, String> cookies, DatePair pair) throws Exception {
        String url = "http://www.sh.10086.cn/sh/wsyyt/busi/historySearch.do?method=getFiveBillDetailAjax&billType=NEW_GSM&startDate=2016-12-01&endDate=2016-12-31&filterfield=输入对方号码：&filterValue=&searchStr=-1&index=0&r=1487232359541&isCardNo=0&gprsType=";
        return Jsoup.connect(url).cookies(cookies).execute().body();
    }

}