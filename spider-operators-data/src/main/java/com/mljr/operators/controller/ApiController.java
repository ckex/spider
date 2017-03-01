package com.mljr.operators.controller;

import com.mljr.operators.common.constant.ErrorCodeEnum;
import com.mljr.operators.common.constant.RequestInfoEnum;
import com.mljr.operators.common.utils.RegexUtils;
import com.mljr.operators.entity.TokenReqResponse;
import com.mljr.operators.scheduler.ChinaMobileScheduler;
import com.mljr.operators.service.ApiService;
import com.mljr.operators.service.ChinaMobileService;
import com.mljr.operators.service.primary.operators.IUserInfoService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by songchi on 16/12/23.
 */
@RestController
public class ApiController {

    @Autowired
    private ChinaMobileService chinaMobileService;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    ChinaMobileScheduler chinaMobileScheduler;

    @Autowired
    ApiService apiService;

    @RequestMapping(value = "/api/operators/tokenReq", method = RequestMethod.POST)
    public TokenReqResponse tokenReq(@RequestParam String cellphone, @RequestParam String idcard) {
        if (!RegexUtils.checkMobile(cellphone)) {
            return new TokenReqResponse(ErrorCodeEnum.CELL_ERR.getValue(), ErrorCodeEnum.CELL_ERR.getName(), false);
        }

        if (!RegexUtils.checkIdCard(idcard)) {
            return new TokenReqResponse(ErrorCodeEnum.IDCARD_ERR.getValue(), ErrorCodeEnum.IDCARD_ERR.getName(), false);
        }
        String token = DigestUtils.md5Hex(cellphone + idcard + "mljr");
        return null;
    }

    @RequestMapping(value = "/api/operators/collectReq", method = RequestMethod.POST)
    public String collectReq() {
        return null;
    }

    @RequestMapping(value = "/api/operators/accessData", method = RequestMethod.POST)
    public String accessData(@RequestParam String token) {
        RequestInfoEnum state = apiService.checkState(token);
        switch (state) {
            case RUNNING:
                System.out.println("running");
                break;
            case SUCCESS:
                System.out.println("error");
                break;
            case ERROR:
                System.out.println("error");
                break;
        }
        return null;
    }


}
