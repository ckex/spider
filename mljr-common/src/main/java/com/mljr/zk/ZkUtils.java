package com.mljr.zk;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by songchi on 16/12/21.
 */
public class ZkUtils {


    public transient Logger logger = LoggerFactory.getLogger(getClass());

    public static final String SEPARATOR = "/";

    public static final String ZK_PARENT = SEPARATOR + "config";

    private static final int TIME_OUT = 1000 * 60 * 1; // Minutes

    private static final String ZK_URL = "106.75.80.159:2181";

    private static ZkClient zkClient;

    public static synchronized ZkClient getZkClient() {
        if (zkClient == null) {
            zkClient = createZkClint(ZK_URL);
        }
        return zkClient;
    }

    public static void changeZkServer(String hosts) {
        zkClient = createZkClint(hosts);
    }

    public static List<String> listServerNode(String path) {
        return getZkClient().getChildren(path);
    }

    public static String readData(String path) {
        createIgnore(path);
        return getZkClient().readData(path);
    }

    public static void updateData(String path, Object data) {
        createIgnore(path);
        getZkClient().writeData(path, data);
    }

    public static void addChildListener(String path, IZkChildListener listener) {
        getZkClient().subscribeChildChanges(path, listener);
    }

    public static void addDataListener(String path, IZkDataListener listener) {
        getZkClient().subscribeDataChanges(path, listener);
    }

//    public static void registerApiNode(String path, DynamicConfig config) {
//        createIgnore(path);
//        getZkClient().writeData(path, config);
//    }
//
//    public static boolean updateConfig(String path, DynamicConfig config) {
//        getZkClient().writeData(path, config);
//        return true;
//    }

    private static ZkClient createZkClint(String zkUrl) {
        return new ZkClient(zkUrl, TIME_OUT);
    }

    public static void createPath(String path) {
        if (StringUtils.isBlank(path)) {
            throw new IllegalArgumentException("path can't be null.");
        }
        getZkClient().createPersistent(path, true);
    }

    private static void createIgnore(String path) {
        if (!path.startsWith(ZK_PARENT)) {
            throw new IllegalArgumentException("path must be start with " + ZK_PARENT);
        }
        if (!getZkClient().exists(ZK_PARENT)) {
            getZkClient().createPersistent(ZK_PARENT);
//                logger.info("create parent path finished.");
        }
        List<String> nodes = Splitter.on(SEPARATOR).omitEmptyStrings().trimResults().splitToList(path);
        String nodePath = ZK_PARENT;
        int count = nodes.size();
        for (int i = 1; i < count; i++) {
            nodePath = Joiner.on(SEPARATOR).join(nodePath, nodes.get(i));
            if (!getZkClient().exists(nodePath)) {
                if (i == (count - 1)) {
                    getZkClient().createEphemeral(nodePath);
                } else {
                    getZkClient().createPersistent(nodePath);
                }
//                    logger.info("create " + nodePath + " finished.");
            }
        }

    }



}

