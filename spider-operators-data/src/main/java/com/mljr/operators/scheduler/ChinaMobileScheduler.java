package com.mljr.operators.scheduler;

import com.mljr.operators.task.chinamobile.CallInfoTask;
import com.mljr.operators.task.chinamobile.FlowInfoTask;
import com.mljr.operators.task.chinamobile.PackageInfoTask;
import com.mljr.operators.task.chinamobile.SMSInfoTask;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by songchi on 17/2/23.
 */
@Component
public class ChinaMobileScheduler {

    @Autowired
    FlowInfoTask flowInfoTask;

    @Autowired
    SMSInfoTask smsInfoTask;

    @Autowired
    CallInfoTask callInfoTask;

    @Autowired
    PackageInfoTask packageInfoTask;

    public final static ExecutorService threadPool = Executors.newCachedThreadPool();

    public void setParams(Long userInfoId, Map<String, String> cookies) {
        flowInfoTask.setParams(userInfoId, cookies);
        smsInfoTask.setParams(userInfoId, cookies);
        callInfoTask.setParams(userInfoId, cookies);
        packageInfoTask.setParams(userInfoId, cookies);
    }

    public void start() {
        threadPool.execute(flowInfoTask);

        threadPool.execute(smsInfoTask);

        threadPool.execute(callInfoTask);

        threadPool.execute(packageInfoTask);

    }
}
