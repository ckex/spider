package com.mljr.operators.entity.dto.operator;

import java.io.Serializable;

/**
 * @author gaoxi
 * @time 2017/2/27
 */
public class RequestInfoDTO implements Serializable {

  private static final long serialVersionUID = 7315260678662042609L;

  private String mobile;

  private String idcard;

  private String provinceCode;

  private String dataDate;

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getIdcard() {
    return idcard;
  }

  public void setIdcard(String idcard) {
    this.idcard = idcard;
  }

  public String getProvinceCode() {
    return provinceCode;
  }

  public void setProvinceCode(String provinceCode) {
    this.provinceCode = provinceCode;
  }

  public String getDataDate() {
    return dataDate;
  }

  public void setDataDate(String dataDate) {
    this.dataDate = dataDate;
  }
}
