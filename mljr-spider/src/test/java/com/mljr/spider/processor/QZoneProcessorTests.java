package com.mljr.spider.processor;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.io.IOException;
import java.util.Set;

/**
 * Created by gaoxi on 2016/12/30.
 */
public class QZoneProcessorTests {

    private static final String QQ_ZONE_LOGIN_URL = "http://qzone.qq.com";

    private static final String QQ_ZONE_URL="http://user.qzone.qq.com/121713331";

    public static void main(String[] args) throws IOException {

        WebDriver webDriver = new PhantomJSDriver();

        webDriver.get(QQ_ZONE_LOGIN_URL);

        webDriver.switchTo().frame("login_frame");

        webDriver.findElement(By.id("switcher_plogin")).click();

        WebElement account_input=webDriver.findElement(By.id("u"));//账号输入狂

        WebElement password_input=webDriver.findElement(By.id("p"));//密码输入框

        WebElement login_button=webDriver.findElement(By.id("login_button"));//登陆按钮

        account_input.clear();

        password_input.clear();

        account_input.sendKeys("543109152");//QQ账号

        password_input.sendKeys("sg*1090?CJ");//QQ密码

        login_button.click();

        Set<Cookie> cookieSet=webDriver.manage().getCookies();//获取cookies信息

        StringBuilder sb=new StringBuilder();

        WebDriver webDriver1=new PhantomJSDriver();

        for(Cookie cookie:cookieSet){
            webDriver1.manage().addCookie(cookie);
        }

        System.out.println(webDriver.getTitle());

        System.out.println(webDriver.getCurrentUrl());

        webDriver1.get(QQ_ZONE_URL);

        String pageSource=webDriver1.getPageSource();

        WebElement moreQuery=webDriver1.findElement(By.id("search_smart_search_more"));

        moreQuery.click();

        pageSource=webDriver1.getPageSource();


        System.out.println();





    }
}
