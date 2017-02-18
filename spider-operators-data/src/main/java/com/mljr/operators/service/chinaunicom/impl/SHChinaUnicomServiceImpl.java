package com.mljr.operators.service.chinaunicom.impl;

import com.mljr.operators.entity.vo.chinaunicom.BillVO;
import com.mljr.operators.entity.vo.chinaunicom.CallVO;
import com.mljr.operators.entity.vo.chinaunicom.SMSVO;
import com.mljr.operators.entity.vo.chinaunicom.UserInfoVO;

import java.util.List;

/**
 * @author gaoxi
 * @Time 2017/2/17
 */
public class SHChinaUnicomServiceImpl extends AbstractChinaUnicomServiceImpl {

    public static void main(String[] args) throws Exception {

        String cookies = "mallcity=11|110; piw=%7B%22login_name%22%3A%22185****5531%22%2C%22nickName%22%3A%22%E9%AB%98%E9%94%A1%22%2C%22rme%22%3A%7B%22ac%22%3A%22%22%2C%22at%22%3A%22%22%2C%22pt%22%3A%2201%22%2C%22u%22%3A%2218521705531%22%7D%2C%22verifyState%22%3A%22%22%7D; _uop_id=ba3314943dd32cfbd56f7ba2bb3c04b5; route=ba702beb58c51e2a16d5a28d4fdb15f3; e3=qFsQYnCGjvxBdSBhvR8Rjr5mH2vp45kq23Wt2n0xGqDZySGYHGTt!276053916; WT_FPC=id=2a3aee3636b4c2fd91f1487351943784:lv=1487351943788:ss=1487351943784; Hm_lvt_9208c8c641bfb0560ce7884c36938d9d=1487351944; Hm_lpvt_9208c8c641bfb0560ce7884c36938d9d=1487351944; _ga=GA1.3.903320661.1487351945; JUT=xu5OQU/vFuVTbLBYtSGP41KbhHB0DAG6zXqZLn9+XfxXZoAWTr7b6wwNs1AZpwl8rH/WxhU7ueF6wDvPZXlWHCldMryzW+7iabFmRB9oVDmqvqXUIZjAUKPSElWLSlLWKt1a8a2ZY6pBcmz8mdWNL9RHv/wFBhS4FbmRLw6s8zhXCY3+p1UUmKn/oBBl6WX7zOeg0/2ftZ+uIMyeSJxR05mprY+9nUrxI0r1pobbhIQw+vF2RdSwfcBv7JUN9UqCmQe6LtL63ttQY7y8Wq/mnCpn8ItVxe4GCEInhu/2/WTHeyW64iH7TOXRts+3YN8jMsLUHfSlbKDBsOpamMK+xbEtyEvo8QHNUZY+EUlw7u78hCcOihnHhJmH3ziemblHYRAUbMUKpZCj6Z5FrI4wDmNSpoX9MJj1fx3pC+WfX0sUSJkEsEgyfZ0M1LJCTvAIQkU1eyQdee9pp7DrIlrQS7K/48zl/M1K3cea5LKM0ISL7kePCA+sD8Cd5sOtFOUs0IK7fvZfPM9LsQp2s6kTXYdu24upWlpHYyujFhgHsJWE8/HfGnVWIw==";

        SHChinaUnicomServiceImpl service = new SHChinaUnicomServiceImpl();

//        UserInfoVO userInfoVO = service.queryUserInfo(cookies);

//        String acctBalance = service.queryAcctBalance(cookies);
//
//        CallVO callVO = service.queryCallDetail(cookies, 2017, 1, 1);
//
//        List<BillVO> billVOList = service.queryHistoryBill(cookies, 2017, 1);
//
        SMSVO smsvo = service.querySMS(cookies, 2016, 11, 1);

        System.out.println();
    }
}