package com.mljr.operators.controller;

import com.google.gson.Gson;
import com.mljr.operators.common.constant.ErrorCodeEnum;
import com.mljr.operators.common.constant.OperatorsEnum;
import com.mljr.operators.common.constant.ProvinceEnum;
import com.mljr.operators.common.constant.RequestInfoEnum;
import com.mljr.operators.common.utils.IdcardValidator;
import com.mljr.operators.common.utils.RegexUtils;
import com.mljr.operators.entity.ApiResponse;
import com.mljr.operators.entity.BaseResponse;
import com.mljr.operators.entity.PhoneInfo;
import com.mljr.operators.entity.dto.chinaunicom.LoginDTO;
import com.mljr.operators.entity.dto.operator.RequestUrlDTO;
import com.mljr.operators.entity.model.operators.UserInfo;
import com.mljr.operators.service.ApiService;
import com.mljr.operators.service.ChinaMobileService;
import com.mljr.operators.service.api.IOperatorAdminApiService;
import com.mljr.operators.service.chinaunicom.IChinaUnicomService;
import com.mljr.operators.service.primary.operators.IUserInfoService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ChinaUnicomController.class);

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    ApiService apiService;

    @Autowired
    IOperatorAdminApiService operatorAdminApiService;

    @Autowired
    ChinaMobileService chinaMobileService;

    @Autowired
    IChinaUnicomService chinaUnicomService;

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
    public BaseResponse collectReq(@RequestParam String token,
                                   @RequestParam String cellphone,
                                   @RequestParam String password,
                                   @RequestParam String smscode) {
        UserInfo u = apiService.findUserByToken(token);
        if (u == null) {
            return new BaseResponse(ErrorCodeEnum.USER_NOT_FOUND, false);
        }
        // 登录获取cookie
        if(OperatorsEnum.CHINAMOBILE.getCode().equals(u.getType())){
            try {
                chinaMobileService.loginAndGetCookies(cellphone,password,smscode);
            } catch (Exception e) {
                logger.error("中国移动登录失败",e);
            }
        }else if(OperatorsEnum.CHINAUNICOM.getCode().equals(u.getType())){
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setMobile(cellphone);
            loginDTO.setPassword(password);
            chinaUnicomService.getCookies(loginDTO);
        }


        RequestUrlDTO dto = new RequestUrlDTO(u.getMobile(), u.getIdcard(),
                OperatorsEnum.indexOf(u.getType()),
                ProvinceEnum.indexOf(u.getProvinceCode()));

        boolean ret = operatorAdminApiService.submitAcquisitionTasks(dto);
        if (ret) {
            return new BaseResponse(ErrorCodeEnum.COLLECT_REQ_SUCC, true);
        }

        return new BaseResponse(ErrorCodeEnum.COLLECT_REQ_FAIL, false);
    }

    @RequestMapping(value = "/api/operators/accessData", method = RequestMethod.POST)
    public Object accessData(@RequestParam String token) {
        UserInfo u = apiService.findUserByToken(token);
        if (u == null) {
            return new BaseResponse(ErrorCodeEnum.USER_NOT_FOUND, false);
        }
        RequestInfoEnum state = apiService.checkState(token);
        switch (state) {
            case RUNNING:
                return new ApiResponse(ErrorCodeEnum.TASK_RUNNING);
            case ERROR:
                return new ApiResponse(ErrorCodeEnum.TASK_ERROR);
            case SUCCESS:
                return apiService.getData(token);
        }
        return null;
    }


}
