package com.mljr.operators.entity;

/**
 * Created by songchi on 17/3/1.
 */
public class TokenReqResponse {
    private int code;
    private String message;
    private boolean success;
    private String token;
    private boolean sms_required;
    private boolean required_captcha_user_identified;

    public TokenReqResponse(int code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public TokenReqResponse(int code, String message, boolean success, String token, boolean sms_required,
                            boolean required_captcha_user_identified) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.token = token;
        this.sms_required = sms_required;
        this.required_captcha_user_identified = required_captcha_user_identified;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isSms_required() {
        return sms_required;
    }

    public void setSms_required(boolean sms_required) {
        this.sms_required = sms_required;
    }

    public boolean isRequired_captcha_user_identified() {
        return required_captcha_user_identified;
    }

    public void setRequired_captcha_user_identified(boolean required_captcha_user_identified) {
        this.required_captcha_user_identified = required_captcha_user_identified;
    }
}
