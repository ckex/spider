package com.mljr.util;

import us.codecraft.webmagic.Site;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by songchi on 16/12/22.
 */
public class ConfigUtils {

    public final static String[] ips =
            {"192.168.1.103","127.0.0.1","10.9.86.137", "10.9.120.152", "10.9.144.100", "10.9.152.221",
                    "10.9.87.127", "10.9.186.101", "10.9.199.216", "10.9.108.39",
                    "10.9.88.4", "10.9.136.160", "10.9.145.53", "10.9.156.231",
                    "10.9.154.167", "10.9.169.120", "10.9.180.171"};
    public static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36";

    public static List<Site> getSiteList() {
        List<Site> siteList = new ArrayList<>();
        siteList.add(
                Site.me().setDomain("baidu-mobile").setSleepTime(5).setRetrySleepTime(1500).setRetryTimes(3)
                        .setUserAgent(DEFAULT_USER_AGENT)
        );

        siteList.add(
                Site.me().setDomain("67cha.com")
                        .setSleepTime(60000).setRetrySleepTime(30000).setRetryTimes(3)
                        .setUserAgent(DEFAULT_USER_AGENT)
        );

        siteList.add(
                Site.me().setDomain("cha.yinhangkadata.com")
                        .setSleepTime(10000).setRetrySleepTime(7500).setRetryTimes(3)
                        .addCookie("ASPSESSIONIDCAATTCQA", "LHEGLFIDENPGIJOLIFNHOIFB")//此字段必填
                        .setUserAgent(DEFAULT_USER_AGENT)

        );

        siteList.add(
                Site.me().setDomain("www.guabu.com")
                        .setSleepTime(15000).setRetrySleepTime(7500).setRetryTimes(3)
                        .setCharset("GB2312") //返回xml格式为gb2312
                        .setUserAgent(DEFAULT_USER_AGENT)
        );

        siteList.add(
                Site.me().setDomain("guishu.showji.com")
                        .setSleepTime(300).setRetrySleepTime(2000).setRetryTimes(3).setUserAgent(DEFAULT_USER_AGENT)
        );

        siteList.add(
                Site.me().setDomain("114huoche.com")
                        .setSleepTime(300).setRetrySleepTime(2000).setRetryTimes(3).setUserAgent(DEFAULT_USER_AGENT)
        );

        siteList.add(
                Site.me().setDomain("huochepiao.com")
                        .setSleepTime(1200).setRetrySleepTime(4500).setRetryTimes(3)
                        .setUserAgent(DEFAULT_USER_AGENT)
        );

        siteList.add(
                Site.me().setDomain("ip138.com")
                        .setSleepTime(300).setRetrySleepTime(2000).setRetryTimes(3).setUserAgent(DEFAULT_USER_AGENT)
        );

        siteList.add(
                Site.me().setDomain("juhe.cn")
                        .setSleepTime(10).setRetryTimes(2).setRetrySleepTime(500)
                        .setCharset("UTF-8")
        );

        siteList.add(
                Site.me().setDomain("lbs.amap.com")
                        .setSleepTime(1200).setRetrySleepTime(4500).setRetryTimes(3)
                        .setUserAgent(DEFAULT_USER_AGENT)

        );

        siteList.add(
                Site.me().setDomain("lbs.amap.com-re")
                        .setSleepTime(1200).setRetrySleepTime(4500).setRetryTimes(3)
                        .setUserAgent(
                                DEFAULT_USER_AGENT)

        );

        siteList.add(
                Site.me().setDomain("lbsyun.baidu.com")
                        .setSleepTime(1200).setRetrySleepTime(4500).setRetryTimes(3)
                        .setUserAgent(
                                DEFAULT_USER_AGENT)


        );

        siteList.add(
                Site.me().setDomain("lbsyun.baidu.com-re")
                        .setSleepTime(1200).setRetrySleepTime(4500).setRetryTimes(3)
                        .setUserAgent(
                                DEFAULT_USER_AGENT)

        );

        siteList.add(
                Site.me().setDomain("qichacha.com").setRetrySleepTime(1500).setRetryTimes(3).setUserAgent(
                        DEFAULT_USER_AGENT)

        );

        siteList.add(
                Site.me().setDomain("saige-gps").setCharset("UTF-8")
        );
        siteList.add(
                Site.me().setDomain("sgs.gov.cn").setRetrySleepTime(1500).setRetryTimes(3)
                        .setUserAgent(
                                DEFAULT_USER_AGENT)
        );
        siteList.add(
                Site.me().setDomain("sogou.com") //此字段在生成文件时用到
                        .setSleepTime(1000)
                        .setRetrySleepTime(4200)
                        .setRetryTimes(3)
                        .setUserAgent(
                                DEFAULT_USER_AGENT)

        );
        siteList.add(
                Site.me().setDomain("tianyancha.com").addHeader("loop", "null").setSleepTime(1000 * 20)
                        .addHeader("Accept", "application/json, text/plain, */*").setRetrySleepTime(1000 * 45).setRetryTimes(3)
                        .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:50.0) Gecko/20100101 Firefox/50.0")

        );
        siteList.add(
                Site.me().setDomain("yinhangka.388g.com")
                        .setSleepTime(10000).setRetrySleepTime(7500).setRetryTimes(3)
                        .setUserAgent(
                                DEFAULT_USER_AGENT)

        );

        return siteList;
    }

    public static Map<String,Site> getSiteMap(){
        Map<String,Site> siteMap = new HashMap<>();
        for (Site site : getSiteList()) {
            siteMap.put(site.getDomain(),site);
        }
        return siteMap;
    }

    public static List<String> getAllNodePath() {
        String path = "/config/%s/%s";
        List<String> nodePaths = new ArrayList<>();
        for (String ip : ips) {
            for (Site site : getSiteList()) {
                nodePaths.add(String.format(path, ip, site.getDomain()));
            }
        }
        return nodePaths;
    }

    public static void main(String[] args) {
//        System.out.println(getSiteList().size());
        System.out.println(getAllNodePath());
    }

}
