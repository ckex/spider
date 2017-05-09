package com.mljr.monitor.task;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mljr.common.ServiceConfig;
import com.mljr.monitor.mail.DefaultMailSender;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by songchi on 17/2/6.
 */
@Component
public class ServersMonitorTask {
    public ZkClient zkClient = ServiceConfig.getZkClient();

    public final static String SERVERS_PATH = "/servers";

    public static List<String> srcChildren;

    @Autowired
    private DefaultMailSender mailSender;

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
                    mailSender.send(null, new String[]{"bee@mljr.com"}, "宕机服务器通知", content);
                }
                srcChildren = currentChilds;
            }
        });
    }
}
