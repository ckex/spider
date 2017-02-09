package com.mljr.monitor.task;

import com.alibaba.fastjson.JSON;
import com.commons.email.SpringEmailServer;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mljr.common.ServiceConfig;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by songchi on 17/2/6.
 */
public class ServersMonitorTask {
    public ZkClient zkClient = ServiceConfig.getZkClient();

    public final static String SERVERS_PATH = "/servers";

    public static List<String> srcChildren;

    String[] emails = {"chi.song@mljr.com", "xi.gao01@mljr.com", "jinmin.zha@mljr.com", "lin.fu01@mljr.com"};

    public ServersMonitorTask() {
        srcChildren = zkClient.getChildren(SERVERS_PATH);
        if (srcChildren == null) {
            srcChildren = Lists.newArrayList();
        }
    }

    public void start() {
        zkClient.subscribeChildChanges(SERVERS_PATH, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                // 某台服务器宕机
                if (currentChilds.size() < srcChildren.size()) {
                    Sets.SetView<String> diffs = Sets.difference(new HashSet<>(srcChildren), new HashSet<>(currentChilds));
                    Map<String, Object> map = Maps.newHashMap();
                    map.put("宕机服务器", diffs.toString());
                    map.put("宕机时间", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    String content = JSON.toJSONString(map, true);
                    SpringEmailServer.sendEmail("alert@i-click.com", emails, "服务器宕机了", content, false);
                }
                srcChildren = currentChilds;
            }
        });
    }
}
