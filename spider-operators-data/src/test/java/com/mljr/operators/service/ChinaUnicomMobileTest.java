package com.mljr.operators.service;

import com.alibaba.fastjson.JSON;
import com.mljr.operators.SpringBootOperatorsMain;
import com.mljr.operators.entity.dto.chinaunicom.LoginDTO;
import com.mljr.operators.entity.vo.chinaunicom.BillVO;
import com.mljr.operators.entity.vo.chinaunicom.CallVO;
import com.mljr.operators.entity.vo.chinaunicom.SMSVO;
import com.mljr.operators.entity.vo.chinaunicom.UserInfoVO;
import com.mljr.operators.service.chinaunicom.IChinaUnicomService;
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
        UserInfoVO userInfoVO = chinaUnicomService.queryUserInfo(cookies);
        System.out.println(JSON.toJSON(userInfoVO));
    }

    @Test
    public void testQueryAcctBalance() throws Exception {
        String acctBalance = chinaUnicomService.queryAcctBalance(cookies);
        System.out.println(acctBalance);
    }

    @Test
    public void testQueryCallDetail() throws Exception {
        CallVO callVO = chinaUnicomService.queryCallDetail(cookies, 2017, 1, 1);
        System.out.println(JSON.toJSON(callVO));
    }


    @Test
    public void testQueryHistoryBill() throws Exception {
        BillVO billVO = chinaUnicomService.queryHistoryBill(cookies, 2017, 1);
        System.out.println(JSON.toJSON(billVO));
    }

    @Test
    public void testQuerySMS() throws Exception {
        SMSVO smsvo = chinaUnicomService.querySMS(cookies, 2017, 1, 1);
        System.out.println(JSON.toJSON(smsvo));
    }

}
