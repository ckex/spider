package com.mljr.operators.entity;

import com.mljr.operators.common.constant.ErrorCodeEnum;

/**
 * Created by songchi on 17/3/2.
 */
public class BaseResponse {
    private int code;
    private String message;
    private boolean success;

    public BaseResponse(ErrorCodeEnum errorCodeEnum, boolean success) {
        this.code = errorCodeEnum.getCode();
        this.message = errorCodeEnum.getDesc();
        this.success = success;
    }
}
