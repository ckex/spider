package com.mljr.operators.service;

import com.mljr.operators.entity.model.operators.CallInfo;
import com.mljr.operators.service.primary.operators.ICallInfoService;
import com.mljr.operators.service.primary.operators.IPhoneInfoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class PhoneInfoServiceTest extends BaseTest {

    @Autowired
    IPhoneInfoService phoneInfoService;

    @Autowired
    ICallInfoService callInfoService;

    @Test
    public void testGetLocation() throws Exception {
        List<CallInfo> list = callInfoService.selectByUid(5L);
        for (CallInfo callInfo : list) {
            System.out.println(phoneInfoService.selectByPhone(callInfo.getCallNumber()));
        }


    }
}
