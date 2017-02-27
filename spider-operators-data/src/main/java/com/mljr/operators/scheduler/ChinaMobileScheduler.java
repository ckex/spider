package com.mljr.operators.scheduler;

import com.mljr.operators.task.chinamobile.*;
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
    PackageInfoTask packageInfoTask;

    @Autowired
    BillInfoTask billInfoTask;

    @Autowired
    FlowInfoTask flowInfoTask;

    @Autowired
    SMSInfoTask smsInfoTask;

    @Autowired
    CallInfoTask callInfoTask;

    public final static ExecutorService threadPool = Executors.newCachedThreadPool();

    public void setParams(Long userInfoId, Map<String, String> cookies) {
        packageInfoTask.setParams(userInfoId, cookies);
        billInfoTask.setParams(userInfoId, cookies);
        flowInfoTask.setParams(userInfoId, cookies);
        smsInfoTask.setParams(userInfoId, cookies);
        callInfoTask.setParams(userInfoId, cookies);
    }

    public void start() {
        threadPool.execute(billInfoTask);

        threadPool.execute(packageInfoTask);

        threadPool.execute(flowInfoTask);

        threadPool.execute(smsInfoTask);

        threadPool.execute(callInfoTask);


    }
}
