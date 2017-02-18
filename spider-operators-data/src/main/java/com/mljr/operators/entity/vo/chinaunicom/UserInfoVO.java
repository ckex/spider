package com.mljr.operators.entity.vo.chinaunicom;

import java.io.Serializable;
import java.util.List;

/**
 * @author gaoxi
 * @Time 2017/2/17
 */
public class UserInfoVO implements Serializable {

    private static final long serialVersionUID = 2747499786550977753L;

    private String userName;

    private String idcard;

    private String cityCode;

    private String level;

    private String productName;

    private List<PackageInfoVO> packageInfos;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<PackageInfoVO> getPackageInfos() {
        return packageInfos;
    }

    public void setPackageInfos(List<PackageInfoVO> packageInfos) {
        this.packageInfos = packageInfos;
    }
}
