package com.mljr.spider.zookeeper;

import com.alibaba.fastjson.JSON;
import com.mljr.entity.SiteConfig;
import com.mljr.zk.ZkUtils;
import org.I0Itec.zkclient.ZkClient;
import org.junit.Test;
import us.codecraft.webmagic.utils.IPUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by songchi on 16/12/22.
 */
public class ZkTest {
    ZkClient zkClient = ZkUtils.getZkClient();

    @Test
    public void updateData() throws Exception {
        String str = zkClient.readData("/config/127.0.0.1/ip138.com");
        SiteConfig SiteConfig =JSON.parseObject(str,SiteConfig.class);
        System.out.println(SiteConfig.getSleepTime());
        SiteConfig.setRetrySleepTime(78978993);
        SiteConfig.setSleepTime(99999999);
        zkClient.writeData("/config/127.0.0.1/ip138.com",JSON.toJSONString(SiteConfig));

    }

    public static String getHostNameForLiunx() {
        try {
            return (InetAddress.getLocalHost()).getHostName();
        } catch (UnknownHostException uhe) {
            String host = uhe.getMessage(); // host = "hostname: hostname"
            if (host != null) {
                int colon = host.indexOf(':');
                if (colon > 0) {
                    return host.substring(0, colon);
                }
            }
            return "UnknownHost";
        }
    }

    public static String getHostName() {
        if (System.getenv("COMPUTERNAME") != null) {
            return System.getenv("COMPUTERNAME");
        } else {
            return getHostNameForLiunx();
        }
    }

    @Test
    public void testip() throws Exception {
//        getHostName();
        getHostNameForLiunx();

    }

    public static void main(String[] args) throws Exception{
//        System.out.println(getHostName());
        System.out.println(IPUtils.getFirstNoLoopbackIPAddresses());
        ;
    }
}
