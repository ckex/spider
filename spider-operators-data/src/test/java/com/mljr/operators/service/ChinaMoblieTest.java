package com.mljr.operators.service;

import com.google.common.collect.Lists;
import com.mljr.operators.dao.primary.operators.SMSInfoMapper;
import com.mljr.operators.entity.model.operators.SMSInfo;
import com.mljr.operators.service.ChinaMobileService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by songchi on 17/2/17.
 */
public class ChinaMoblieTest extends BaseTest{
    ChinaMobileService chinaMobileService = new ChinaMobileService();

    @Autowired
    SMSInfoMapper smsInfoMapper;

    @Test
    public void getAllInfos() throws Exception {

        Map<String, String> cookies = chinaMobileService.loginAndGetCookies("13681668945", "672440","438237");


//        System.out.println(chinaMobileService.getAccountInfo(cookies));
//
//        System.out.println(chinaMobileService.getPlanInfo(cookies));
//
//        System.out.println(chinaMobileService.getCostInfo(cookies));
//
//        System.out.println(chinaMobileService.getFlowBill(cookies));
//
//        System.out.println(chinaMobileService.getSmsBill(cookies));
//
//        System.out.println(chinaMobileService.getCallBill(cookies));
    }

    @Test
    public void testName() throws Exception {
//        System.out.println(chinaMobileService.getLatestMonths(12));

    }

    @Test
    public void test2() throws Exception {
        SMSInfo info = new SMSInfo();
        info.setUserInfoId(9991L);
        info.setSendNum("999");
        info.setSendTime(new Date());
        info.setSmsType("3");
        info.setBusinessType("5");
        List<SMSInfo> list = Lists.newArrayList(info,info,info,info);
        smsInfoMapper.insertByBatch(list);

    }
}
