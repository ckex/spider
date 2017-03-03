package com.mljr.operators.entity.dto.operator;

import com.mljr.operators.common.constant.OperatorsEnum;
import com.mljr.operators.common.constant.OperatorsUrlEnum;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @author gaoxi
 * @time 2017/2/28
 */
public class RequestInfoDTO {

  private String mobile;

  private String idcard;

  private String startDate;

  private String endDate;

  private OperatorsEnum operatorsEnum;

  private OperatorsUrlEnum operatorsUrl;

  private String url;

  private String pattern;

  public RequestInfoDTO(){}

  public RequestInfoDTO(String mobile, String idcard) {
    this.mobile = mobile;
    this.idcard = idcard;
  }

  public RequestInfoDTO me(String mobile, String idcard) {
    return new RequestInfoDTO(mobile, idcard);
  }

  public String getMobile() {
    return mobile;
  }

  public RequestInfoDTO setMobile(String mobile) {
    this.mobile = mobile;
    return this;
  }

  public String getIdcard() {
    return idcard;
  }

  public RequestInfoDTO setIdcard(String idcard) {
    this.idcard = idcard;
    return this;
  }

  public String getStartDate() {
    return startDate;
  }

  public RequestInfoDTO setStartDate(String startDate) {
    this.startDate = startDate;
    return this;
  }

  public String getEndDate() {
    return endDate;
  }

  public RequestInfoDTO setEndDate(String endDate) {
    this.endDate = endDate;
    return this;
  }

  public OperatorsEnum getOperatorsEnum() {
    return operatorsEnum;
  }

  public RequestInfoDTO setOperatorsEnum(OperatorsEnum operatorsEnum) {
    this.operatorsEnum = operatorsEnum;
    return this;
  }

  public OperatorsUrlEnum getOperatorsUrl() {
    return operatorsUrl;
  }

  public RequestInfoDTO setOperatorsUrl(OperatorsUrlEnum operatorsUrl) {
    this.operatorsUrl = operatorsUrl;
    return this;
  }

  public String getUrl() {
    return url;
  }

  public RequestInfoDTO setUrl(String url) {
    this.url = url;
    return this;
  }

  public String getPattern() {
    return pattern;
  }

  public RequestInfoDTO setPattern(String pattern) {
    this.pattern = pattern;
    return this;
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this);
  }

  public String getSign() {
    StringBuilder builder =
        new StringBuilder(mobile).append(idcard).append(operatorsEnum.name()).append(url);
    return DigestUtils.md5Hex(builder.toString());
  }
}
