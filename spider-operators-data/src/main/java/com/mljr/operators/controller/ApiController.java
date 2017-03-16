package com.mljr.operators.controller;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.mljr.operators.common.constant.*;
import com.mljr.operators.common.utils.CookieUtils;
import com.mljr.operators.common.utils.IdcardValidator;
import com.mljr.operators.common.utils.RegexUtils;
import com.mljr.operators.entity.ApiResponse;
import com.mljr.operators.entity.BaseResponse;
import com.mljr.operators.entity.vo.PhoneInfoVO;
import com.mljr.operators.entity.dto.chinaunicom.LoginDTO;
import com.mljr.operators.entity.dto.operator.RequestUrlDTO;
import com.mljr.operators.entity.model.operators.OperatorFeatures;
import com.mljr.operators.entity.model.operators.UserInfo;
import com.mljr.operators.service.ApiService;
import com.mljr.operators.service.ChinaMobileService;
import com.mljr.operators.service.api.IOperatorAdminApiService;
import com.mljr.operators.service.chinaunicom.IChinaUnicomService;
import com.mljr.operators.service.primary.operators.IOperatorFeaturesService;
import com.mljr.operators.service.primary.operators.IUserInfoService;
import com.mljr.operators.service.statistics.ICallStatisticsService;
import com.mljr.operators.service.statistics.ISmsStatisticsService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by songchi on 16/12/23.
 */
@RestController
public class ApiController {

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

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

    @Autowired
    IOperatorFeaturesService operatorFeaturesService;

    @Autowired
    ICallStatisticsService callStatisticsService;

    @Autowired
    ISmsStatisticsService smsStatisticsService;

    Gson gson = new Gson();

    /**
     * token申请接口
     *
     * @param cellphone
     * @param idcard
     * @return
     */
    @RequestMapping(value = "/api/operators/tokenReq", method = RequestMethod.POST)
    public ApiResponse tokenReq(@RequestParam String cellphone, @RequestParam String idcard) {
        if (!RegexUtils.checkMobile(StringUtils.trim(cellphone))) {
            return new ApiResponse(ErrorCodeEnum.CELL_ERR, false);
        }

        if (!IdcardValidator.isValidatedAllIdcard(StringUtils.trim(idcard))) {
            return new ApiResponse(ErrorCodeEnum.IDCARD_ERR, false);
        }
        String token = DigestUtils.md5Hex(cellphone + idcard + "mljr");
        PhoneInfoVO phoneInfo = apiService.getPhoneInfo(cellphone);
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

        OperatorFeatures fe = operatorFeaturesService.selectUniqFeatures(ret.getProvinceCode(), ret.getType());
        apiService.sendSmsCodeIfNeeded(cellphone, fe);
        return new ApiResponse(ErrorCodeEnum.TOKEN_SUCC, true, token, fe);
    }

    /**
     * 采集任务申请接口
     *
     * @param token
     * @param password
     * @param smscode
     * @return
     */
    @RequestMapping(value = "/api/operators/collectReq", method = RequestMethod.POST)
    public BaseResponse collectReq(@RequestParam String token,
                                   @RequestParam String password,
                                   @RequestParam String smscode) {
        UserInfo u = apiService.findUserByToken(token);
        if (u == null) {
            return new BaseResponse(ErrorCodeEnum.USER_NOT_FOUND, false);
        }
        // 登录获取cookie
        try {
            String cookies = "";
            if (OperatorsEnum.CHINAMOBILE.getCode().equals(u.getType())) {
                Map<String, String> cMap = chinaMobileService.loginAndGetCookieMap(u.getMobile(), password, smscode);
                if (cMap == null || !"true".equals(cMap.get("is_login"))) {
                    return new BaseResponse(ErrorCodeEnum.LOGIN_FAIL, false);
                }
                cookies = CookieUtils.mapToString(cMap);
            } else if (OperatorsEnum.CHINAUNICOM.getCode().equals(u.getType())) {
                LoginDTO loginDTO = new LoginDTO();
                loginDTO.setMobile(u.getMobile());
                loginDTO.setPassword(password);
                cookies = chinaUnicomService.getCookies(loginDTO);
                if (cookies == null) {
                    return new BaseResponse(ErrorCodeEnum.LOGIN_FAIL, false);
                }
            }
            apiService.saveCookies(u.getMobile(), cookies);
        } catch (Exception e) {
            logger.error("获取cookie失败", e);
            return new BaseResponse(ErrorCodeEnum.LOGIN_FAIL, false);
        }

        RequestUrlDTO dto = new RequestUrlDTO(u.getMobile(), u.getIdcard(),
                OperatorsEnum.indexOf(u.getType()),
                ProvinceEnum.codeOf(u.getProvinceCode()));

        boolean ret = operatorAdminApiService.submitAcquisitionTasks(dto);
        if (ret) {
            return new BaseResponse(ErrorCodeEnum.COLLECT_REQ_SUCC, true);
        }

        return new BaseResponse(ErrorCodeEnum.COLLECT_REQ_FAIL, false);
    }

    /**
     * 数据获取接口
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/api/operators/accessData", method = RequestMethod.POST)
    public Object accessData(@RequestParam String token) {
        UserInfo u = apiService.findUserByToken(token);
        if (u == null) {
            return new BaseResponse(ErrorCodeEnum.USER_NOT_FOUND, false);
        }
        RequestInfoEnum state = apiService.checkState(u);
        switch (state) {
            case INIT:
                return new BaseResponse(ErrorCodeEnum.TASK_RUNNING, false);
            case RUNNING:
                return apiService.getData(token);
            case ERROR:
                return apiService.getData(token);
            case SUCCESS:
                return apiService.getData(token);
        }
        return null;
    }

    /**
     * 统计报告接口
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/api/operators/statistics", method = RequestMethod.POST)
    public Object statistics(@RequestParam String token) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        UserInfo u = apiService.findUserByToken(token);
        if (u == null) {
            return new BaseResponse(ErrorCodeEnum.USER_NOT_FOUND, false);
        }
        RequestInfoEnum state = apiService.checkState(u);
        if(RequestInfoEnum.INIT.equals(state)){
            return new BaseResponse(ErrorCodeEnum.TASK_RUNNING, false);
        }
        Long uid = u.getId();
        Map<String, Object> map = Maps.newHashMap();
        map.put("call_max_min_date", callStatisticsService.selectMaxMinDate(uid));

        map.put("call_by_month", callStatisticsService.selectByMonth(uid));

        map.put("call_by_address", callStatisticsService.selectByAddress(uid));

        map.put("call_by_number", callStatisticsService.getStatisticsByNumber(uid,OperatorsEnum.indexOf(u.getType())));

        map.put("sms_max_min_date", smsStatisticsService.getTimeBySmsType(uid, SmsTypeEnum.ALL));

        logger.debug("statistics api use time   " + stopwatch.elapsed(TimeUnit.MILLISECONDS));

        return map;
    }


}
