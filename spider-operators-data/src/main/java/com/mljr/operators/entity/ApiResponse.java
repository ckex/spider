package com.mljr.operators.entity;

import com.mljr.operators.common.constant.ErrorCodeEnum;
import com.mljr.operators.entity.model.operators.OperatorFeatures;

/**
 * Created by songchi on 17/3/1.
 */
public class ApiResponse {
    public Integer code;
    public String message;
    public Boolean success;
    public String token;
    public Boolean need_pwd;
    public Boolean need_sms_code;
    public Boolean need_captcha;
    public Boolean need_second_query_pwd;

    public ApiResponse() {
    }

    public ApiResponse(ErrorCodeEnum errorCodeEnum) {
        this.code = errorCodeEnum.getCode();
        this.message = errorCodeEnum.getDesc();
    }

    public ApiResponse(ErrorCodeEnum errorCodeEnum, boolean success) {
        this.code = errorCodeEnum.getCode();
        this.message = errorCodeEnum.getDesc();
        this.success = success;
    }

    public ApiResponse(ErrorCodeEnum errorCodeEnum, Boolean success, String token, OperatorFeatures fe) {
        this.code = errorCodeEnum.getCode();
        this.message = errorCodeEnum.getDesc();
        this.success = success;
        this.token = token;
        this.need_pwd = fe.getNeedPwd();
        this.need_sms_code = fe.getNeedSmsCode();
        this.need_captcha = fe.getNeedCaptcha();
        this.need_second_query_pwd = fe.getNeedSecondQueryPwd();
    }

}
