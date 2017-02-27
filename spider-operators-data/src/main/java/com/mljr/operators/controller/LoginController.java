package com.mljr.operators.controller;

import com.alibaba.fastjson.JSON;
import com.mljr.operators.common.constant.OperatorsEnum;
import com.mljr.operators.entity.model.operators.UserInfo;
import com.mljr.operators.scheduler.ChinaMobileScheduler;
import com.mljr.operators.service.ChinaMobileService;
import com.mljr.operators.service.primary.operators.IUserInfoService;
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
    private IUserInfoService userInfoService;

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
                              @RequestParam String userName,
                              @RequestParam String idcard,
                              @RequestParam String dtm
    ) throws Exception {
        UserInfo retInfo = userInfoService.selectUniqUser(telno, idcard);
        if (retInfo != null) {
            return "运营商数据已存在";
        }
        Map<String, String> cookies = chinaMobileService.loginAndGetCookies(telno, password, dtm);
        if (cookies != null && "true".equals(cookies.get("is_login"))) {
            UserInfo userInfo = new UserInfo();
            userInfo.setMobile(telno);
            userInfo.setPwd(password);
            userInfo.setIdcard(idcard);
            userInfo.setUserName(userName);
            userInfo.setCityCode("上海");
            userInfo.setType(OperatorsEnum.CHINAMOBILE.getValue());
            UserInfo ret = userInfoService.insertIgnore(userInfo);
            if (ret != null) {
                chinaMobileScheduler.setParams(ret.getId(), cookies);
                chinaMobileScheduler.start();
                return "success";
            }
        }
        return "登录失败 " + JSON.toJSONString(cookies);
    }


}
