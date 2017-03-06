//package com.mljr.operators.scheduler;
//
//import com.mljr.operators.task.chinamobile.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
///**
// * Created by songchi on 17/2/23.
// */
//@Component
//public class ChinaMobileScheduler {
//    @Autowired
//    PackageInfoTask packageInfoTask;
//
//    @Autowired
//    CurrBillInfoTask currBillInfoTask;
//
//    @Autowired
//    CurrFlowInfoTask currFlowInfoTask;
//
//    @Autowired
//    CurrSMSInfoTask currSmsInfoTask;
//
//    @Autowired
//    CurrCallInfoTask currCallInfoTask;
//
//    public final static ExecutorService threadPool = Executors.newCachedThreadPool();
//
//    public void setParams(Long userInfoId, Map<String, String> cookies) {
//        packageInfoTask.setParams(userInfoId, cookies);
//        currBillInfoTask.setParams(userInfoId, cookies);
//        currFlowInfoTask.setParams(userInfoId, cookies);
//        currSmsInfoTask.setParams(userInfoId, cookies);
//        currCallInfoTask.setParams(userInfoId, cookies);
//    }
//
//    public void start() {
//        threadPool.execute(currBillInfoTask);
//
//        threadPool.execute(packageInfoTask);
//
//        threadPool.execute(currFlowInfoTask);
//
//        threadPool.execute(currSmsInfoTask);
//
//        threadPool.execute(currCallInfoTask);
//
//
//    }
//}
