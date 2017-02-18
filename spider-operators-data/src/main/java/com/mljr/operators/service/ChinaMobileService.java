package com.mljr.operators.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.mljr.operators.entity.LoginResponse;
import com.mljr.operators.utils.JsUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

    public Map<String, String> loginAndGetCookies(String telno, String password) {
        String _telno = JsUtils.enString(telno);
        String _dtm = JsUtils.enString(password);
        String loginUrl = "https://sh.ac.10086.cn/loginjt?act=2&telno=%s&password=%s&authLevel=1&dtm&ctype=1&decode=1&source=wsyyt";
        //                 https://sh.ac.10086.cn/loginjt?act=2&telno=%s&password=39EC2E305EE6744CF3308D16947A9CAF&authLevel=1&dtm&ctype=1&decode=1&source=wsyyt
        String jumpUrl = getJumpUrl(String.format(loginUrl, _telno, _dtm));
        System.out.println(jumpUrl);
        return getCookies(jumpUrl);
    }

    public String getAllInfos(Map<String, String> cookies) throws Exception {
        StringBuilder accum = new StringBuilder();
        accum.append(this.getAccountInfo(cookies))
                .append(this.getPlanInfo(cookies))
                .append(this.getCostInfo(cookies))
                .append(this.getFlowBill(cookies))
                .append(this.getSmsBill(cookies))
                .append(this.getCallBill(cookies));
        return accum.toString();
    }

    // 01 用户在运营商信息
    public String getAccountInfo(Map<String, String> cookies) throws Exception {
        System.out.println("==============  01 用户在运营商信息 start ===============");
        String url = "http://www.sh.10086.cn/sh/wsyyt/action?act=my.getUserName";
        return Jsoup.connect(url).cookies(cookies).execute().body();
    }

    // 02 套餐信息
    public String getPlanInfo(Map<String, String> cookies) throws Exception {
        System.out.println("==============  02 套餐信息 start ===============");
        String url = "http://www.sh.10086.cn/sh/wsyyt/action?act=my.getMusType";
        return Jsoup.connect(url).cookies(cookies).execute().body();
    }

    // 03 用户话费信息
    public String getCostInfo(Map<String, String> cookies) throws Exception {
        System.out.println("==============  03 用户话费信息 start ===============");
        String url = "http://www.sh.10086.cn/sh/wsyyt/busi/historySearch.do?method=FiveBillAllNewAjax&dateTime=2016年12月&tab=tab2_15&isPriceTaxSeparate=null&showType=0&r=1487313589994";
        return Jsoup.connect(url).cookies(cookies).execute().body();
    }

    // 04 网络流量信息
    public String getFlowBill(Map<String, String> cookies) throws Exception {
        System.out.println("==============  04 网络流量信息 start ===============");
        String url = "http://www.sh.10086.cn/sh/wsyyt/busi/historySearch.do?method=getFiveBillDetailAjax&billType=NEW_GPRS_NEW&startDate=2016-12-01&endDate=2016-12-31&filterfield=输入对方号码：&filterValue=&searchStr=-1&index=0&r=1487232359541&isCardNo=0&gprsType=";
        return Jsoup.connect(url).cookies(cookies).execute().body();
    }

    // 05 短信使用信息
    public String getSmsBill(Map<String, String> cookies) throws Exception {
        System.out.println("==============  05 短信使用信息 start ===============");
        String url = "http://www.sh.10086.cn/sh/wsyyt/busi/historySearch.do?method=getFiveBillDetailAjax&billType=NEW_SMS&startDate=2016-12-01&endDate=2016-12-31&filterfield=输入对方号码：&filterValue=&searchStr=-1&index=0&r=1487232359541&isCardNo=0&gprsType=";
        return Jsoup.connect(url).cookies(cookies).execute().body();
    }

    // 06 通话详单
    public String getCallBill(Map<String, String> cookies) throws Exception {
        System.out.println("==============  06 通话详单 start ===============");
        String url = "http://www.sh.10086.cn/sh/wsyyt/busi/historySearch.do?method=getFiveBillDetailAjax&billType=NEW_GSM&startDate=2016-12-01&endDate=2016-12-31&filterfield=输入对方号码：&filterValue=&searchStr=-1&index=0&r=1487232359541&isCardNo=0&gprsType=";
        return Jsoup.connect(url).cookies(cookies).execute().body();
    }

    public List<String> getLatest12Months() {
        List<String> months = Lists.newArrayList();
        for (int i = 0; i <= 11; i++) {
            String date = LocalDate.now().minusMonths(i).format(DateTimeFormatter.ofPattern("yyyyMM"));
            months.add(date);
        }
        return months;
    }

//    public String getBodyByJsoup(){
//        Jsoup.connect("http://www.sh.10086.cn/sh/wsyyt/action?act=my.getmycredit").cookies(cookies).execute();
//    }
}
