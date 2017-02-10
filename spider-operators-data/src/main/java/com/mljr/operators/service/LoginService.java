package com.mljr.operators.service;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.mljr.operators.entity.LoginResponse;
import com.mljr.operators.utils.JsUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by songchi on 16/12/23.
 */
@Service
public class LoginService {
    public static String getJumpUrl(String url) {
        try {
            Connection.Response response = Jsoup.connect(url).execute();
            LoginResponse loginResponse = new Gson().fromJson(response.body(), LoginResponse.class);
            return JsUtils.getJumpUrl(loginResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> getCookies(String url) {
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

    public static void main(String[] args) throws Exception {
        String telno = JsUtils.enString("13681668945");
        String password = JsUtils.enString("672440");
        String dtm = JsUtils.enString("653448");
        String loginUrl = "https://sh.ac.10086.cn/loginjt?act=2&telno=%s&password=%s&authLevel=5&dtm=%s&ctype=1&decode=1&source=wsyyt";
        String jumpUrl = getJumpUrl(String.format(loginUrl, telno, password, dtm));
        System.out.println(jumpUrl);
        Map<String, String> cookies = getCookies(jumpUrl);
        Connection.Response response1 = Jsoup.connect("http://www.sh.10086.cn/sh/wsyyt/action?act=my.getUserName").cookies(cookies).execute();
        Connection.Response response2 = Jsoup.connect("http://www.sh.10086.cn/sh/wsyyt/action?act=my.getMusType").cookies(cookies).execute();
        Connection.Response response3 = Jsoup.connect("http://www.sh.10086.cn/sh/wsyyt/action?act=my.getaccountinfo").cookies(cookies).execute();
        Connection.Response response4 = Jsoup.connect("http://www.sh.10086.cn/sh/wsyyt/action?act=my.getpoint").cookies(cookies).execute();
        Connection.Response response5 = Jsoup.connect("http://www.sh.10086.cn/sh/wsyyt/action?act=my.getbuyphone").cookies(cookies).execute();
        Connection.Response response6 = Jsoup.connect("http://www.sh.10086.cn/sh/wsyyt/action?act=my.getmycredit").cookies(cookies).execute();

        System.out.println(response1.body());
        System.out.println(response2.body());
        System.out.println(response3.body());
        System.out.println(response4.body());
        System.out.println(response5.body());
        System.out.println(response6.body());


    }
}
