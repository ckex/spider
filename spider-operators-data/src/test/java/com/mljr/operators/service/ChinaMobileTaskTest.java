package com.mljr.operators.service;

import com.mljr.operators.common.constant.ProvinceEnum;
import com.mljr.operators.dao.primary.operators.OperatorFeaturesMapper;
import com.mljr.operators.dao.primary.operators.RequestInfoMapper;
import com.mljr.operators.dao.primary.statistics.CallStatisticsMapper;
import com.mljr.operators.entity.model.operators.OperatorFeatures;
import com.mljr.operators.task.chinamobile.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Set;

/**
 * @author gaoxi
 * @time 2017/2/22
 */
public class ChinaMobileTaskTest extends BaseTest {

//    @Autowired
    CurrFlowInfoTask currFlowInfoTask;

//    @Autowired
    CurrSMSInfoTask currSmsInfoTask;

//    @Autowired
    CurrCallInfoTask currCallInfoTask;

//    @Autowired
    PackageInfoTask packageInfoTask;

//    @Autowired
    CurrBillInfoTask currBillInfoTask;

//    @Autowired
    RequestInfoMapper requestInfoMapper;

//    @Autowired
    ApiService apiService;

//    @Autowired
    OperatorFeaturesMapper operatorFeaturesMapper;

    @Autowired
    CallStatisticsMapper callStatisticsMapper;

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
        Set<Integer> set = requestInfoMapper.checkState("13681668945", "310112198212207354");

        System.out.println(set);

    }

    @Test
    public void testFe() throws Exception {
        OperatorFeatures fe = new OperatorFeatures();
        fe.setProvinceCode("sh");
        fe.setOperatorType("2");
        operatorFeaturesMapper.insertSelective(fe);

        operatorFeaturesMapper.selectUniqFeatures("sh", "2");
    }

    @Test
    public void testInsertData() throws Exception {
        ProvinceEnum[] arr = ProvinceEnum.values();
        for (int i = 0; i < arr.length; i++) {
            ProvinceEnum anEnum = arr[i];

            for (int j = 1; j <= 3; j++) {
                OperatorFeatures fe = new OperatorFeatures();
                fe.setProvinceCode(anEnum.name());
                fe.setOperatorType(j + "");
                operatorFeaturesMapper.insertSelective(fe);
            }

        }

    }

    @Test
    public void testCall1() throws Exception {
        callStatisticsMapper.selectMaxMinDate(2).forEach(System.out::println);
    }

    @Test
    public void testCall2() throws Exception {
        callStatisticsMapper.selectByMonth(2).forEach(System.out::println);

    }

    @Test
    public void testCall3() throws Exception {
      callStatisticsMapper.selectByAddress(2).forEach(System.out::println);

    }
}
