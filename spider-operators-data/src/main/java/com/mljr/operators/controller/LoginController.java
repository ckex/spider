package com.mljr.operators.controller;

import com.mljr.operators.scheduler.ChinaMobileScheduler;
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

    @Autowired
    ChinaMobileScheduler chinaMobileScheduler;

    @RequestMapping("/")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @RequestMapping("/getSmsCode/{telno}")
    public String getSmsCode(@PathVariable String telno) {
        if (StringUtils.isBlank(telno) || telno.length() != 11) {
            return "请输入正确的手机号";
        }
        return chinaMobileService.getSmsCode(StringUtils.trim(telno));
    }

    @RequestMapping("/chinaMobile/getAllInfos")
    public String getAllInfos(@RequestParam String telno,
                                    @RequestParam String password,
                                    @RequestParam String dtm) throws Exception {
        Map<String, String> cookies = chinaMobileService.loginAndGetCookies(telno, password, dtm);
        chinaMobileScheduler.setParams(777L,cookies);
        chinaMobileScheduler.start();
        return "success";
    }


}
