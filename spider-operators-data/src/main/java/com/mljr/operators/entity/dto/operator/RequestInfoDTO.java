package com.mljr.operators.entity.dto.operator;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @author gaoxi
 * @time 2017/2/28
 */
public class RequestInfoDTO {

  private String mobile;

  private String idcard;

  private String operatorsType;

  private Integer urlType;

  private String url;

  private String startDate;

  private String endDate;

  public static RequestInfoDTO me() {
    return new RequestInfoDTO();
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

  public String getOperatorsType() {
    return operatorsType;
  }

  public RequestInfoDTO setOperatorsType(String operatorsType) {
    this.operatorsType = operatorsType;
    return this;
  }

  public Integer getUrlType() {
    return urlType;
  }

  public RequestInfoDTO setUrlType(Integer urlType) {
    this.urlType = urlType;
    return this;
  }

  public String getUrl() {
    return url;
  }

  public RequestInfoDTO setUrl(String url) {
    this.url = url;
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

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this);
  }

  public String getSign() {
    StringBuilder builder = new StringBuilder();
    builder.append(mobile.trim()).append(idcard.trim()).append(operatorsType.trim()).append(urlType)
        .append(url.trim()).append(startDate.trim()).append(endDate.trim());
    return DigestUtils.md5Hex(builder.toString());
  }
}
