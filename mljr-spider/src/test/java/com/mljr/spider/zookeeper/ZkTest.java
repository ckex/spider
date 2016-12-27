package com.mljr.spider.zookeeper;

import com.alibaba.fastjson.JSON;
import com.mljr.entity.SiteConfig;
import com.mljr.zk.ZkUtils;
import org.I0Itec.zkclient.ZkClient;
import org.junit.Test;

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

}
