package com.mljr.operators.service;

import com.mljr.operators.service.ChinaMobileService;
import org.junit.Test;

import java.util.Map;

/**
 * Created by songchi on 17/2/17.
 */
public class ChinaMoblieTest {
    ChinaMobileService chinaMobileService = new ChinaMobileService();

    @Test
    public void getAllInfos() throws Exception {

        Map<String, String> cookies = chinaMobileService.loginAndGetCookies("13681668945", "672440","438237");


        System.out.println(chinaMobileService.getAccountInfo(cookies));

        System.out.println(chinaMobileService.getPlanInfo(cookies));

        System.out.println(chinaMobileService.getCostInfo(cookies));

        System.out.println(chinaMobileService.getFlowBill(cookies));

        System.out.println(chinaMobileService.getSmsBill(cookies));

        System.out.println(chinaMobileService.getCallBill(cookies));
    }
}
