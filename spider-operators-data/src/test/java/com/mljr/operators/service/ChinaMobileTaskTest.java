package com.mljr.operators.service;

import com.mljr.operators.task.chinamobile.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * @author gaoxi
 * @time 2017/2/22
 */
public class ChinaMobileTaskTest extends BaseTest {

    @Autowired
    FlowInfoTask flowInfoTask;

    @Autowired
    SMSInfoTask smsInfoTask;

    @Autowired
    CallInfoTask callInfoTask;

    @Autowired
    PackageInfoTask packageInfoTask;

    @Autowired
    BillInfoTask billInfoTask;

    @Test
    public void flowInfoTest() {

        Assert.notNull(flowInfoTask);

        flowInfoTask.run();
    }

    @Test
    public void smsInfoTest() throws Exception {
        Assert.notNull(smsInfoTask);

        smsInfoTask.run();

    }

    @Test
    public void callInfoTest() throws Exception {
        Assert.notNull(callInfoTask);

        callInfoTask.run();

    }

    @Test
    public void packageInfoTest() throws Exception {
        Assert.notNull(packageInfoTask);

        packageInfoTask.run();

    }

    @Test
    public void billInfoTest() throws Exception {
        billInfoTask.setParams(9900L, null);
        billInfoTask.writeToDb("", "2016年12月");

    }
}
