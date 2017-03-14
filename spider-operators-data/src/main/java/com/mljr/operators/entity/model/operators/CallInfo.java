package com.mljr.operators.entity.model.operators;

import java.math.BigDecimal;
import java.util.Date;

public class CallInfo {
    private Long id;

    private Long userInfoId;

    private Date callDate;

    private String callNumber;

    private String callType;

    private String landType;

    private String callLongHour;

    private Integer callDuration;

    private BigDecimal callFee;

    private String callLocalAddress;

    private String callRemoteAddress;

    private String discountPackage;

    private String firstCall;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber == null ? null : callNumber.trim();
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType == null ? null : callType.trim();
    }

    public String getLandType() {
        return landType;
    }

    public void setLandType(String landType) {
        this.landType = landType == null ? null : landType.trim();
    }

    public String getCallLongHour() {
        return callLongHour;
    }

    public void setCallLongHour(String callLongHour) {
        this.callLongHour = callLongHour == null ? null : callLongHour.trim();
    }

    public Integer getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(Integer callDuration) {
        this.callDuration = callDuration;
    }

    public BigDecimal getCallFee() {
        return callFee;
    }

    public void setCallFee(BigDecimal callFee) {
        this.callFee = callFee;
    }

    public String getCallLocalAddress() {
        return callLocalAddress;
    }

    public void setCallLocalAddress(String callLocalAddress) {
        this.callLocalAddress = callLocalAddress == null ? null : callLocalAddress.trim();
    }

    public String getCallRemoteAddress() {
        return callRemoteAddress;
    }

    public void setCallRemoteAddress(String callRemoteAddress) {
        this.callRemoteAddress = callRemoteAddress == null ? null : callRemoteAddress.trim();
    }

    public String getDiscountPackage() {
        return discountPackage;
    }

    public void setDiscountPackage(String discountPackage) {
        this.discountPackage = discountPackage == null ? null : discountPackage.trim();
    }

    public String getFirstCall() {
        return firstCall;
    }

    public void setFirstCall(String firstCall) {
        this.firstCall = firstCall == null ? null : firstCall.trim();
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