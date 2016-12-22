package com.mljr.spider.zookeeper;

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

        String str = "{\"acceptStatCode\":[200],\"allCookies\":{},\"cookies\":{}," +
                "\"cycleRetryTimes\":0,\"domain\":\"ip138.com\",\"headers\":{}," +
                "\"retrySleepTime\":2000,\"retryTimes\":3,\"sleepTime\":300,\"startRequests\":[]," +
                "\"startUrls\":[],\"timeOut\":5000,\"useGzip\":true,\"userAgent\":\"dapao\"}";
        zkClient.writeData("/config/192.168.1.101/ip138.com",str);

    }

    @Test
    public void deleteRecursive() throws Exception {
        zkClient.deleteRecursive("/config");
    }
}
