package com.mljr.operators.controller;

import com.mljr.operators.service.ChinaMobileService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by songchi on 16/12/23.
 */
@RestController
public class LoginController {

    @Autowired
    private ChinaMobileService chinaMobileService;

    @RequestMapping("/")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @RequestMapping("/getSmsCode/{telno}")
    public String getSmsCode(@PathVariable String telno) {
        if (StringUtils.isBlank(telno) || telno.length() != 11) {
            return "请输入正确的手机号";
        }
        chinaMobileService.getSmsCode(StringUtils.trim(telno));
        return chinaMobileService.getSmsCode(StringUtils.trim(telno));
    }

    @RequestMapping("/chinaMobile/getAllInfos")
    public ModelAndView getAllInfos(@RequestParam String telno,
                                    @RequestParam String password,
                                    @RequestParam String dtm) throws Exception {
        Map<String, String> cookies = chinaMobileService.loginAndGetCookies(telno, password, dtm);


        ModelAndView mav = new ModelAndView("info");
        mav.addObject("accountInfo", chinaMobileService.getAccountInfo(cookies));

        mav.addObject("planInfo", chinaMobileService.getPlanInfo(cookies));

        mav.addObject("costInfo", chinaMobileService.getCostInfo(cookies));

        mav.addObject("flowBill", chinaMobileService.getFlowBill(cookies));

        mav.addObject("smsBill", chinaMobileService.getSmsBill(cookies));

        mav.addObject("callBill", chinaMobileService.getCallBill(cookies));
        return mav;
    }


}
