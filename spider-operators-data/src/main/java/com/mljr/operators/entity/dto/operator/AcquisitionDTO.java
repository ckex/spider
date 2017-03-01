package com.mljr.operators.entity.dto.operator;

import com.mljr.operators.common.constant.OperatorsEnum;
import com.mljr.operators.common.constant.ProvinceEnum;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author gaoxi
 * @time 2017/2/27
 */
public class AcquisitionDTO implements Serializable {

  private static final long serialVersionUID = 7315260678662042609L;

  private String mobile;

  private String idcard;

  private OperatorsEnum operators;

  private ProvinceEnum province;

  private LocalDate startDate =LocalDate.now();

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

  public OperatorsEnum getOperators() {
    return operators;
  }

  public void setOperators(OperatorsEnum operators) {
    this.operators = operators;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public ProvinceEnum getProvince() {
    return province;
  }

  public void setProvince(ProvinceEnum province) {
    this.province = province;
  }
}
