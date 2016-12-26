package com.mljr.spider.scheduler.zk;

import com.mljr.zk.ZkUtils;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.Random;

/**
 * Created by songchi on 16/12/26.
 */
@Service
public class ZkScheduler {
    protected transient org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ZkScheduler.class);

    private ZkClient zkClient = ZkUtils.getZkClient();


    @Scheduled(cron = "0/10 * * * * ?")
    public void startZkMonitorTask() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String ip = InetAddress.getLocalHost().getHostAddress();
                        String path = String.format("/servers/%s", ip);
                        String data = new Random().nextInt(100000) + "";
                        System.out.println("服务器上传负载：" + data);
                        if (!zkClient.exists("/servers")) {
                            zkClient.createPersistent("/servers");
                        }
                        if (!zkClient.exists(path)) {
                            zkClient.createEphemeral(path, data.getBytes("utf-8"));
                        }
                        Thread.sleep(1000 * 10);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("服务器上传负载出错", e);
                }

            }
        }).start();
    }
}


