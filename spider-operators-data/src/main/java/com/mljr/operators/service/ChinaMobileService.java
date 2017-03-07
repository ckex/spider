package com.mljr.operators.service;

import com.google.gson.Gson;
import com.mljr.operators.common.constant.ChinaMobileConstant;
import com.mljr.operators.common.utils.CookieUtils;
import com.mljr.operators.common.utils.JsUtils;
import com.mljr.operators.entity.LoginResponse;
import com.mljr.operators.entity.chinamobile.DatePair;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.util.Map;

/**
 * Created by songchi on 16/12/23.
 */
@Service
public class ChinaMobileService {
    public String sendSmsCode(String telno) {
        try {
            String url = String.format(ChinaMobileConstant.Shanghai.SMS_CODE_PATTERN, telno);
            String jsonStr = fetchData(url);
            String result = new JsonPathSelector("result").select(jsonStr);
            if ("0".equals(result)) {
                return new JsonPathSelector("message").select(jsonStr);
            }
            return "请求验证码失败";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    public String getJumpUrl(String url) {
        try {
            String data = fetchData(url);
            LoginResponse loginResponse = new Gson().fromJson(data, LoginResponse.class);
            return JsUtils.getJumpUrl(loginResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> loginAndGetCookieMap(String telno, String password, String dtm) throws Exception {
        String _telno = JsUtils.enString(telno);
        String _password = JsUtils.enString(password);
        String _dtm = JsUtils.enString(dtm);
        String jumpUrl = getJumpUrl(String.format(ChinaMobileConstant.Shanghai.LOGIN_PATTERN, _telno, _password, _dtm));
        System.out.println(jumpUrl);
        return fetchCookies(jumpUrl);
    }

    public String loginAndGetCookieStr(String telno, String password, String dtm) throws Exception {
        return CookieUtils.mapToString(loginAndGetCookieMap(telno, password, dtm));
    }

    // 01 用户在运营商信息
    public String getUserInfo(Map<String, String> cookies) throws Exception {
        return fetchData(ChinaMobileConstant.Shanghai.USER_INFO_PATTERN, cookies);
    }

    // 02 套餐信息
    public String getPackageInfo(Map<String, String> cookies) throws Exception {
        return fetchData(ChinaMobileConstant.Shanghai.PACKAGE_INFO_PATTERN, cookies);
    }

    // 03-1 当月话费信息
    public String getCurrentBillInfo(Map<String, String> cookies) throws Exception {
        return fetchData(ChinaMobileConstant.Shanghai.CURRENT_BILL_INFO_PATTERN, cookies);
    }

    // 03-2 历史话费信息
    public String getHistoryBillInfo(Map<String, String> cookies, String queryTime) throws Exception {
        String url = String.format(ChinaMobileConstant.Shanghai.HISTORY_BILL_INFO_PATTERN, queryTime);
        return fetchData(url, cookies);
    }

    // 04-1 当月网络流量信息
    public String getCurrentFlowInfo(Map<String, String> cookies, DatePair pair) throws Exception {
        String url = String.format(ChinaMobileConstant.Shanghai.CURRENT_FLOW_INFO_PATTERN,
                pair.getStartDate(), pair.getEndDate());
        return fetchData(url, cookies);
    }

    // 04-2 历史网络流量信息
    public String getHistoryFlowInfo(Map<String, String> cookies, DatePair pair) throws Exception {
        String url = String.format(ChinaMobileConstant.Shanghai.HISTORY_FLOW_INFO_PATTERN,
                pair.getStartDate(), pair.getEndDate());
        return fetchData(url, cookies);
    }

    // 05-1 当月短信使用信息
    public String getCurrentSmsInfo(Map<String, String> cookies, DatePair pair) throws Exception {
        String url = String.format(ChinaMobileConstant.Shanghai.CURRENT_SMS_INFO_PATTERN,
                pair.getStartDate(), pair.getEndDate());
        return fetchData(url, cookies);
    }

    // 05-2 历史短信使用信息
    public String getHistorySmsInfo(Map<String, String> cookies, DatePair pair) throws Exception {
        String url = String.format(ChinaMobileConstant.Shanghai.HISTORY_SMS_INFO_PATTERN,
                pair.getStartDate(), pair.getEndDate());
        return fetchData(url, cookies);
    }

    // 06-1 当月通话详单
    public String getCurrentCallInfo(Map<String, String> cookies, DatePair pair) throws Exception {
        String url = String.format(ChinaMobileConstant.Shanghai.CURRENT_CALL_INFO_PATTERN,
                pair.getStartDate(), pair.getEndDate());
        return fetchData(url, cookies);
    }

    // 06-2 历史通话详单
    public String getHistoryCallInfo(Map<String, String> cookies, DatePair pair) throws Exception {
        String url = String.format(ChinaMobileConstant.Shanghai.HISTORY_CALL_INFO_PATTERN,
                pair.getStartDate(), pair.getEndDate());
        return fetchData(url, cookies);
    }

    public String fetchData(String url) throws Exception {
        return Jsoup.connect(url).timeout(1000 * 60).execute().body();
    }

    public String fetchData(String url, Map<String, String> cookies) throws Exception {
        return Jsoup.connect(url).timeout(1000 * 60).cookies(cookies).execute().body();
    }

    public Map<String, String> fetchCookies(String url) throws Exception {
        return Jsoup.connect(url).timeout(1000 * 60).execute().cookies();
    }

}