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

        String str = "{\"acceptStatCode\":[200],\"allCookies\":{},\"cookies\":{\"myCookie\":\"lisi778899\"},\"cycleRetryTimes\":0,\"domain\":\"sogou.com\",\"headers\":{\"myHeader\":\"zhangsan66\"},\"retrySleepTime\":2000,\"retryTimes\":3,\"sleepTime\":300,\"startRequests\":[],\"startUrls\":[]," +
                "\"timeOut\":5000,\"useGzip\":true,\"userAgent\":\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36\"}";
        zkClient.writeData("/config/192.168.1.101/sogou.com",str);

    }

    @Test
    public void deleteRecursive() throws Exception {
        zkClient.deleteRecursive("/config");
    }
}
