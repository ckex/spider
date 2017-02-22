package com.mljr.operators.entity.model.operators;

import java.util.Date;

public class PackageInfoDetail {
    private Long id;

    private Long packageInfoId;

    private String discntName;

    private String discntFee;

    private String description;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPackageInfoId() {
        return packageInfoId;
    }

    public void setPackageInfoId(Long packageInfoId) {
        this.packageInfoId = packageInfoId;
    }

    public String getDiscntName() {
        return discntName;
    }

    public void setDiscntName(String discntName) {
        this.discntName = discntName == null ? null : discntName.trim();
    }

    public String getDiscntFee() {
        return discntFee;
    }

    public void setDiscntFee(String discntFee) {
        this.discntFee = discntFee == null ? null : discntFee.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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