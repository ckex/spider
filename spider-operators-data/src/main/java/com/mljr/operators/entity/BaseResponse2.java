package com.mljr.operators.entity;

import com.mljr.operators.common.constant.ApiCodeEnum;

/**
 * @author gaoxi
 * @Time: 2017/3/23 下午9:30
 */
public class BaseResponse2<T> {

    private int code;

    private String message;

    private T context;

    public BaseResponse2() {
    }

    public BaseResponse2(ApiCodeEnum apiCodeEnum,T context) {
        this.code = apiCodeEnum.getIndex();
        this.message = apiCodeEnum.getDesc();
        this.context=context;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContext() {
        return context;
    }

    public void setContext(T context) {
        this.context = context;
    }
}
