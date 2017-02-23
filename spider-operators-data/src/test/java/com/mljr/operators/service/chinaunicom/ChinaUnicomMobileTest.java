package com.mljr.operators.service.chinaunicom;

import com.alibaba.fastjson.JSON;
import com.mljr.operators.SpringBootOperatorsMain;
import com.mljr.operators.entity.dto.chinaunicom.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author gaoxi
 * @Time 2017/2/19
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootOperatorsMain.class)
public class ChinaUnicomMobileTest {

    @Autowired
    private IChinaUnicomService chinaUnicomService;

    private String cookies;

    @Before
    public void testCookies() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setMobile("");
        loginDTO.setPassword("");
        cookies = chinaUnicomService.getCookies(loginDTO);
        System.out.println(cookies);

    }

    @Test
    public void testQueryUserInfo() throws Exception {
        PersonInfoDTO personInfo = chinaUnicomService.queryUserInfo(cookies);
        System.out.println(JSON.toJSON(personInfo));
    }

    @Test
    public void testQueryAcctBalance() throws Exception {
        RemainingDTO remaining = chinaUnicomService.queryAcctBalance(cookies);
        System.out.println(remaining);
    }

    @Test
    public void testQueryCallDetail() throws Exception {
        CallDTO call = chinaUnicomService.queryCallDetail(cookies, 2017, 1, 1);
        System.out.println(JSON.toJSON(call));
    }


    @Test
    public void testQueryHistoryBill() throws Exception {
        BillDTO bill = chinaUnicomService.queryHistoryBill(cookies, 2017, 1);
        System.out.println(JSON.toJSON(bill));
    }

    @Test
    public void testQuerySMS() throws Exception {
        SMSDTO sms = chinaUnicomService.querySMS(cookies, 2017, 1, 1);
        System.out.println(JSON.toJSON(sms));
    }

}
