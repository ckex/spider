package com.mljr.operators.entity.dto.chinaunicom;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * @author gaoxi
 * @Time 2017/2/13
 * @Description 联通登陆数据传输层
 */
public class LoginDTO implements Serializable {

    private static final long serialVersionUID = -4056581740112809809L;

    private String mobile;//手机号码

    private String password;//服务密码

    private String verifyCode;//验证码

    private String provinceCode;//省份

    private String idcard;//身份证

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
