package com.mljr.operators.entity.dto.operator;

import com.mljr.operators.common.constant.OperatorsEnum;
import com.mljr.operators.common.constant.ProvinceEnum;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author gaoxi
 * @time 2017/3/2
 */
public class RequestUrlDTO implements Serializable {

  private static final long serialVersionUID = -8122181270090911106L;

  private String mobile;

  private String idcard;

  private OperatorsEnum operators;

  private ProvinceEnum province;

  private LocalDate startDate = LocalDate.now();

  public RequestUrlDTO(String mobile, String idcard, OperatorsEnum operators,
      ProvinceEnum province) {
    this.mobile = mobile;
    this.idcard = idcard;
    this.operators = operators;
    this.province = province;
  }

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

  public ProvinceEnum getProvince() {
    return province;
  }

  public void setProvince(ProvinceEnum province) {
    this.province = province;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this);
  }
}
