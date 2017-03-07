package com.mljr.operators.entity.model.operators;

import java.util.Date;

public class OperatorFeatures {
    private Integer id;

    private String provinceCode;

    private String operatorType;

    private Boolean needSmsCode;

    private Boolean needPwd;

    private Boolean needCaptcha;

    private Boolean needSecondQueryPwd;

    private String smsCodeUrl;

    private String captchaUrl;

    private String loginUrl;

    private String userInfoUrl;

    private String packageInfoUrl;

    private String packageInfoDetailUrl;

    private String currBillInfoUrl;

    private String currFlowInfoUrl;

    private String currSmsInfoUrl;

    private String currCallInfoUrl;

    private String hisBillInfoUrl;

    private String hisFlowInfoUrl;

    private String hisSmsInfoUrl;

    private String hisCallInfoUrl;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType == null ? null : operatorType.trim();
    }

    public Boolean getNeedSmsCode() {
        return needSmsCode;
    }

    public void setNeedSmsCode(Boolean needSmsCode) {
        this.needSmsCode = needSmsCode;
    }

    public Boolean getNeedPwd() {
        return needPwd;
    }

    public void setNeedPwd(Boolean needPwd) {
        this.needPwd = needPwd;
    }

    public Boolean getNeedCaptcha() {
        return needCaptcha;
    }

    public void setNeedCaptcha(Boolean needCaptcha) {
        this.needCaptcha = needCaptcha;
    }

    public Boolean getNeedSecondQueryPwd() {
        return needSecondQueryPwd;
    }

    public void setNeedSecondQueryPwd(Boolean needSecondQueryPwd) {
        this.needSecondQueryPwd = needSecondQueryPwd;
    }

    public String getSmsCodeUrl() {
        return smsCodeUrl;
    }

    public void setSmsCodeUrl(String smsCodeUrl) {
        this.smsCodeUrl = smsCodeUrl == null ? null : smsCodeUrl.trim();
    }

    public String getCaptchaUrl() {
        return captchaUrl;
    }

    public void setCaptchaUrl(String captchaUrl) {
        this.captchaUrl = captchaUrl == null ? null : captchaUrl.trim();
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl == null ? null : loginUrl.trim();
    }

    public String getUserInfoUrl() {
        return userInfoUrl;
    }

    public void setUserInfoUrl(String userInfoUrl) {
        this.userInfoUrl = userInfoUrl == null ? null : userInfoUrl.trim();
    }

    public String getPackageInfoUrl() {
        return packageInfoUrl;
    }

    public void setPackageInfoUrl(String packageInfoUrl) {
        this.packageInfoUrl = packageInfoUrl == null ? null : packageInfoUrl.trim();
    }

    public String getPackageInfoDetailUrl() {
        return packageInfoDetailUrl;
    }

    public void setPackageInfoDetailUrl(String packageInfoDetailUrl) {
        this.packageInfoDetailUrl = packageInfoDetailUrl == null ? null : packageInfoDetailUrl.trim();
    }

    public String getCurrBillInfoUrl() {
        return currBillInfoUrl;
    }

    public void setCurrBillInfoUrl(String currBillInfoUrl) {
        this.currBillInfoUrl = currBillInfoUrl == null ? null : currBillInfoUrl.trim();
    }

    public String getCurrFlowInfoUrl() {
        return currFlowInfoUrl;
    }

    public void setCurrFlowInfoUrl(String currFlowInfoUrl) {
        this.currFlowInfoUrl = currFlowInfoUrl == null ? null : currFlowInfoUrl.trim();
    }

    public String getCurrSmsInfoUrl() {
        return currSmsInfoUrl;
    }

    public void setCurrSmsInfoUrl(String currSmsInfoUrl) {
        this.currSmsInfoUrl = currSmsInfoUrl == null ? null : currSmsInfoUrl.trim();
    }

    public String getCurrCallInfoUrl() {
        return currCallInfoUrl;
    }

    public void setCurrCallInfoUrl(String currCallInfoUrl) {
        this.currCallInfoUrl = currCallInfoUrl == null ? null : currCallInfoUrl.trim();
    }

    public String getHisBillInfoUrl() {
        return hisBillInfoUrl;
    }

    public void setHisBillInfoUrl(String hisBillInfoUrl) {
        this.hisBillInfoUrl = hisBillInfoUrl == null ? null : hisBillInfoUrl.trim();
    }

    public String getHisFlowInfoUrl() {
        return hisFlowInfoUrl;
    }

    public void setHisFlowInfoUrl(String hisFlowInfoUrl) {
        this.hisFlowInfoUrl = hisFlowInfoUrl == null ? null : hisFlowInfoUrl.trim();
    }

    public String getHisSmsInfoUrl() {
        return hisSmsInfoUrl;
    }

    public void setHisSmsInfoUrl(String hisSmsInfoUrl) {
        this.hisSmsInfoUrl = hisSmsInfoUrl == null ? null : hisSmsInfoUrl.trim();
    }

    public String getHisCallInfoUrl() {
        return hisCallInfoUrl;
    }

    public void setHisCallInfoUrl(String hisCallInfoUrl) {
        this.hisCallInfoUrl = hisCallInfoUrl == null ? null : hisCallInfoUrl.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}