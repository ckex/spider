package com.mljr.operators.controller;

import com.google.gson.Gson;
import com.mljr.operators.common.constant.ErrorCodeEnum;
import com.mljr.operators.common.constant.OperatorsEnum;
import com.mljr.operators.common.constant.ProvinceEnum;
import com.mljr.operators.common.constant.RequestInfoEnum;
import com.mljr.operators.common.utils.IdcardValidator;
import com.mljr.operators.common.utils.RegexUtils;
import com.mljr.operators.entity.ApiResponse;
import com.mljr.operators.entity.PhoneInfo;
import com.mljr.operators.entity.model.operators.UserInfo;
import com.mljr.operators.scheduler.ChinaMobileScheduler;
import com.mljr.operators.service.ApiService;
import com.mljr.operators.service.ChinaMobileService;
import com.mljr.operators.service.primary.operators.IUserInfoService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
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

    Gson gson = new Gson();

    @RequestMapping(value = "/api/operators/tokenReq", method = RequestMethod.POST)
    public ApiResponse tokenReq(@RequestParam String cellphone, @RequestParam String idcard) {
        if (!RegexUtils.checkMobile(StringUtils.trim(cellphone))) {
            return new ApiResponse(ErrorCodeEnum.CELL_ERR, false);
        }

        if (!IdcardValidator.isValidatedAllIdcard(StringUtils.trim(idcard))) {
            return new ApiResponse(ErrorCodeEnum.IDCARD_ERR, false);
        }
        String token = DigestUtils.md5Hex(cellphone + idcard + "mljr");
        PhoneInfo phoneInfo = apiService.getPhoneInfo(cellphone);
        UserInfo info = new UserInfo();
        info.setMobile(cellphone);
        info.setIdcard(idcard);
        info.setProvinceCode(ProvinceEnum.getCode(phoneInfo.getResult().getProvince()));
        info.setType(OperatorsEnum.getCodeByDesc(phoneInfo.getResult().getCompany()));
        UserInfo ret = userInfoService.insertIgnore(info);
        if (ret == null) {
            ret = userInfoService.selectUniqUser(cellphone, idcard);
        }

        apiService.saveToken(token, ret.getId());

        return new ApiResponse(ErrorCodeEnum.TOKEN_SUCC, true, token, true, false);
    }

    @RequestMapping(value = "/api/operators/collectReq", method = RequestMethod.POST)
    public String collectReq() {
        return null;
    }

    @RequestMapping(value = "/api/operators/accessData", method = RequestMethod.POST)
    public Object accessData(@RequestParam String token) {
        RequestInfoEnum state = apiService.checkState(token);
        switch (state) {
            case RUNNING:
                return new ApiResponse(ErrorCodeEnum.TASK_RUNNING);
            case ERROR:
                return new ApiResponse(ErrorCodeEnum.TASK_RUNNING);
            case SUCCESS:
                return apiService.getData(token);
        }
        return null;
    }


}
