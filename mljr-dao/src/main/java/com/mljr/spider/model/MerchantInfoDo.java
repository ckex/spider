package com.mljr.spider.model;

import java.io.Serializable;

public class MerchantInfoDo implements Serializable {
  private static final long serialVersionUID = 1481423827535L;

  private String mmId;
  private String merchantId;
  private String merchantName;
  private String mmBussinessLicense;
  private String mmPersonName;
  private String mmAccountName;
  private String provinceName;
  private String cityName;
  private String areaName;
  private String phyStreet;

  public String getMmId() {

    return mmId;
  }

  public void setMmId(String mmId) {
    this.mmId = mmId;
  }

  public String getMerchantId() {
    return merchantId;
  }

  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  public String getMerchantName() {
    return merchantName;
  }

  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  public String getMmBussinessLicense() {
    return mmBussinessLicense;
  }

  public void setMmBussinessLicense(String mmBussinessLicense) {
    this.mmBussinessLicense = mmBussinessLicense;
  }

  public String getMmPersonName() {
    return mmPersonName;
  }

  public void setMmPersonName(String mmPersonName) {
    this.mmPersonName = mmPersonName;
  }

  public String getMmAccountName() {
    return mmAccountName;
  }

  public void setMmAccountName(String mmAccountName) {
    this.mmAccountName = mmAccountName;
  }

  public String getProvinceName() {
    return provinceName;
  }

  public void setProvinceName(String provinceName) {
    this.provinceName = provinceName;
  }

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public String getAreaName() {
    return areaName;
  }

  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }

  public String getPhyStreet() {
    return phyStreet;
  }

  public void setPhyStreet(String phyStreet) {
    this.phyStreet = phyStreet;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    MerchantInfoDo that = (MerchantInfoDo) o;

    if (mmId != null ? !mmId.equals(that.mmId) : that.mmId != null)
      return false;
    if (merchantId != null ? !merchantId.equals(that.merchantId) : that.merchantId != null)
      return false;
    if (merchantName != null ? !merchantName.equals(that.merchantName) : that.merchantName != null)
      return false;
    if (mmBussinessLicense != null ? !mmBussinessLicense.equals(that.mmBussinessLicense) : that.mmBussinessLicense != null)
      return false;
    if (mmPersonName != null ? !mmPersonName.equals(that.mmPersonName) : that.mmPersonName != null)
      return false;
    if (mmAccountName != null ? !mmAccountName.equals(that.mmAccountName) : that.mmAccountName != null)
      return false;
    if (provinceName != null ? !provinceName.equals(that.provinceName) : that.provinceName != null)
      return false;
    if (cityName != null ? !cityName.equals(that.cityName) : that.cityName != null)
      return false;
    return areaName != null ? areaName.equals(that.areaName) : that.areaName == null;

  }

  @Override
  public int hashCode() {
    int result = mmId != null ? mmId.hashCode() : 0;
    result = 31 * result + (merchantId != null ? merchantId.hashCode() : 0);
    result = 31 * result + (merchantName != null ? merchantName.hashCode() : 0);
    result = 31 * result + (mmBussinessLicense != null ? mmBussinessLicense.hashCode() : 0);
    result = 31 * result + (mmPersonName != null ? mmPersonName.hashCode() : 0);
    result = 31 * result + (mmAccountName != null ? mmAccountName.hashCode() : 0);
    result = 31 * result + (provinceName != null ? provinceName.hashCode() : 0);
    result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
    result = 31 * result + (areaName != null ? areaName.hashCode() : 0);
    return result;
  }
}
