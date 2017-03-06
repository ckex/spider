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
    CurrFlowInfoTask currFlowInfoTask;

    @Autowired
    CurrSMSInfoTask currSmsInfoTask;

    @Autowired
    CurrCallInfoTask currCallInfoTask;

    @Autowired
    PackageInfoTask packageInfoTask;

    @Autowired
    CurrBillInfoTask currBillInfoTask;

    @Test
    public void flowInfoTest() {

        Assert.notNull(currFlowInfoTask);

        currFlowInfoTask.run();
    }

    @Test
    public void smsInfoTest() throws Exception {
        Assert.notNull(currSmsInfoTask);

        currSmsInfoTask.run();

    }

    @Test
    public void callInfoTest() throws Exception {
        Assert.notNull(currCallInfoTask);

        currCallInfoTask.run();

    }

    @Test
    public void packageInfoTest() throws Exception {
        Assert.notNull(packageInfoTask);

        packageInfoTask.run();

    }

}
