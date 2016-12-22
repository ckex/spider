package com.mljr.monitor;

import com.mljr.util.ConfigUtils;
import com.mljr.zk.ZkUtils;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * 配置监控
 */
public class ConfigWatcher {
    private static ZkClient zkClient = ZkUtils.getZkClient();
    List<String> children;

    /**
     * 初始化zookeeper
     */
    public void initialize(String path) {

        new Thread(new Runnable() {

            public void run() {

                zkClient.subscribeDataChanges(path, new IZkDataListener() {

                    public void handleDataDeleted(String dataPath) throws Exception {
                        System.out.println("the node 'dataPath'===>");
                    }

                    public void handleDataChange(String dataPath, Object data) throws Exception {
                        System.out.println("the node 'dataPath'===>"+dataPath+", data has changed.it's data is "+String.valueOf(data));

                    }
                });

            }

        }).start();
    }

    public static void main(String[] args) {
        ConfigWatcher spiderWatcher = new ConfigWatcher();
        for (String path : ConfigUtils.getAllNodePath()) {
            spiderWatcher.initialize(path);
        }

        spiderWatcher.start();
    }

    /**
     * 让当前进程一直运行
     */
    private void start() {
        while (true) {
            ;
        }
    }

}
