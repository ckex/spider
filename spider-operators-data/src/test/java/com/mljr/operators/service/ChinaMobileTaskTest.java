package com.mljr.operators.service;

import com.mljr.operators.dao.primary.operators.PackageInfoMapper;
import com.mljr.operators.task.chinamobile.CallInfoTask;
import com.mljr.operators.task.chinamobile.FlowInfoTask;
import com.mljr.operators.task.chinamobile.PackageInfoTask;
import com.mljr.operators.task.chinamobile.SMSInfoTask;
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
    PackageInfoMapper packageInfoMapper;

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
}
