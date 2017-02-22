package com.mljr.operators.entity.model.operators;

import java.math.BigDecimal;
import java.util.Date;

public class FlowInfo {
    private Long id;

    private Long userInfoId;

    private Date startTime;

    private String homeArea;

    private String netType;

    private String svcName;

    private Boolean forwardType;

    private BigDecimal receiveBytes;

    private BigDecimal sendBytes;

    private BigDecimal totalBytes;

    private BigDecimal fee;

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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getHomeArea() {
        return homeArea;
    }

    public void setHomeArea(String homeArea) {
        this.homeArea = homeArea == null ? null : homeArea.trim();
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType == null ? null : netType.trim();
    }

    public String getSvcName() {
        return svcName;
    }

    public void setSvcName(String svcName) {
        this.svcName = svcName == null ? null : svcName.trim();
    }

    public Boolean getForwardType() {
        return forwardType;
    }

    public void setForwardType(Boolean forwardType) {
        this.forwardType = forwardType;
    }

    public BigDecimal getReceiveBytes() {
        return receiveBytes;
    }

    public void setReceiveBytes(BigDecimal receiveBytes) {
        this.receiveBytes = receiveBytes;
    }

    public BigDecimal getSendBytes() {
        return sendBytes;
    }

    public void setSendBytes(BigDecimal sendBytes) {
        this.sendBytes = sendBytes;
    }

    public BigDecimal getTotalBytes() {
        return totalBytes;
    }

    public void setTotalBytes(BigDecimal totalBytes) {
        this.totalBytes = totalBytes;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
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