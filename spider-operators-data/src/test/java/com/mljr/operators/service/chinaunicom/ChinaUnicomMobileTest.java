package com.mljr.operators.service.chinaunicom;

import com.alibaba.fastjson.JSON;
import com.mljr.operators.entity.dto.chinaunicom.*;
import com.mljr.operators.service.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gaoxi
 * @Time 2017/2/19
 */

public class ChinaUnicomMobileTest extends BaseTest {

    @Autowired
    private IChinaUnicomService chinaUnicomService;

    private String cookies;

    @Override
    public void testInit() {
        super.testInit();
//        LoginDTO loginDTO = new LoginDTO();
//        loginDTO.setMobile("");
//        loginDTO.setPassword("");
//        cookies = chinaUnicomService.getCookies(loginDTO);
//        System.out.println(cookies);
        cookies = "mallcity=31|310; gipgeo=31|310; SHOP_PROV_CITY=; _n3fa_cid=6bcca70e3c8a430cb48c6bf475b7a65f; _n3fa_ext=ft=1488029441; _n3fa_lvt_a9e72dfe4a54a20c3d6e671b3bad01d9=1488029441; _n3fa_lpvt_a9e72dfe4a54a20c3d6e671b3bad01d9=1488029461; WT_FPC=id=23ec4136061c2db18411488029443595:lv=1488029490470:ss=1488029443595; Hm_lvt_9208c8c641bfb0560ce7884c36938d9d=1488029444; Hm_lpvt_9208c8c641bfb0560ce7884c36938d9d=1488029491; piw=%7B%22login_name%22%3A%22185****5531%22%2C%22nickName%22%3A%22%E9%AB%98%E9%94%A1%22%2C%22rme%22%3A%7B%22ac%22%3A%22%22%2C%22at%22%3A%22%22%2C%22pt%22%3A%2201%22%2C%22u%22%3A%2218521705531%22%7D%2C%22verifyState%22%3A%22%22%7D; _uop_id=a9e6789a0770d2b1e8427c80dd8a29cd; route=5eedb21b90a922c9b5a2ce53582667c0; e3=dWy9YxgVjZX2yVDkrTtVkyM2KypQ2y9WhsH8R2gY4gx3t4bfhZgR!-718672085; _ga=GA1.2.301877226.1488029465; ang_sessionid=020914990113588336; ang_seqid=4; ang_catchyou=1; _ga=GA1.3.301877226.1488029465; JUT=wboDJSyEi8XHxLysWPcuhFCGeqUM5VcNN5K+Fz0/4L2lpSbC4JNl9WIzNzKs/qdH/PmOGaQJi/PznCmSMwjphVKGIfVx6j4o/lCZUFNgOW7d2bF7qG+Z/V20nfhwrTkCK6cWny1lIUK9mZDZagVxt8lDbXuU6YhHSR8M3PZSCKjV8iou+GfzxyIkOSxWsCk87J126nsPHrbLDWsgb5Uamttf2iZF4hyyNbvwRxI5AUJoqwmLCbOStqpofHrNSYgZMQaq7gvOkBDegP1H/7kANBkIBHCdqbKrEs4QmTksaokrDC9vupsc86xgVPHLV/a9Xk7xZgRbkGe/zC1TaKPkbmhZXEnDMJM+zHnSDpAUc+YfLOgo8a9FSW3q0QchB7hp5YiN+OVyW/gIH+71/kbX0sB+a7RCjrhyHCnkYV6KcDzQVqL4xls+RS3mB6eBlerAZgeicIzdRl59OjJE2sOJPkN1o+0fTIkhv2d6ma02SnoVyO66AhhZBbiwBH4+pPmdLnemL0Y15ln/LLU45Jn+5qJSBu0j4AF7PiHMQ1I3AilafR4ejbJyhA==; MII=000100030004";
    }

    @Test
    public void testQueryUserInfo() {
        PersonInfoDTO personInfo = chinaUnicomService.queryUserInfo(cookies);
        System.out.println(JSON.toJSON(personInfo));
    }

    @Test
    public void testQueryAcctBalance() {
        RemainingDTO remaining = chinaUnicomService.queryAcctBalance(cookies);
        System.out.println(remaining);
    }

    @Test
    public void testQueryCallDetail() {
        CallDTO call = chinaUnicomService.queryCallDetail(cookies, 2017, 1, 1);
        System.out.println(JSON.toJSON(call));
    }


    @Test
    public void testQueryHistoryBill() {
        BillDTO bill = chinaUnicomService.queryHistoryBill(cookies, 2017, 1);
        System.out.println(JSON.toJSON(bill));
    }

    @Test
    public void testQuerySMS() {
        SMSDTO sms = chinaUnicomService.querySMS(cookies, 2017, 1, 1);
        System.out.println(JSON.toJSON(sms));
    }

    @Test
    public void testQueryCallFlow() {
        FlowDetailDTO flowDetailDTO = chinaUnicomService.queryCallFlow(cookies, "2017-02-25", 1);
        System.out.println(JSON.toJSON(flowDetailDTO));
    }

    @Test
    public void testQueryFlowRecord() {
        FlowRecordDTO flowRecordDTO = chinaUnicomService.queryFlowRecord(cookies, 2017, 1, 1);
        System.out.println(JSON.toJSON(flowRecordDTO));
    }

}
