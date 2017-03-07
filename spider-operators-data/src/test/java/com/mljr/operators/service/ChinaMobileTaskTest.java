package com.mljr.operators.service;

import com.google.common.collect.Maps;
import com.mljr.operators.dao.primary.operators.OperatorFeaturesMapper;
import com.mljr.operators.dao.primary.operators.RequestInfoMapper;
import com.mljr.operators.entity.model.operators.OperatorFeatures;
import com.mljr.operators.entity.model.operators.RequestInfo;
import com.mljr.operators.task.chinamobile.*;
import com.mljr.redis.RedisClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @Autowired
    RequestInfoMapper requestInfoMapper;

    @Autowired
    ApiService apiService;

    @Autowired
    OperatorFeaturesMapper operatorFeaturesMapper;

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

    @Test
    public void checkState() throws Exception {
        Set<Integer> set = requestInfoMapper.checkState("13681668945","310112198212207354");

        System.out.println(set);

    }

    @Test
    public void testFe() throws Exception {
        OperatorFeatures fe = new OperatorFeatures();
        fe.setProvinceCode("sh");
        fe.setOperatorType("2");
        operatorFeaturesMapper.insertSelective(fe);

        operatorFeaturesMapper.selectUniqFeatures("sh","2");
    }
}
