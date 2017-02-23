package com.mljr.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gaoxi on 2017/1/6.
 */
public class CCookie implements Serializable {
  private static final long serialVersionUID = 8253483063460361644L;

  private String name;
  private String value;
  private String path;
  private String domain;
  private Date expiry;
  private boolean secure;
  private boolean httpOnly;

  public CCookie() {}

  public CCookie(String name, String value, String path, String domain, Date expiry, boolean secure, boolean httpOnly) {
    this.name = name;
    this.value = value;
    this.path = path;
    this.domain = domain;
    this.expiry = expiry;
    this.secure = secure;
    this.httpOnly = httpOnly;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public Date getExpiry() {
    return expiry;
  }

  public void setExpiry(Date expiry) {
    this.expiry = expiry;
  }

  public boolean isSecure() {
    return secure;
  }

  public void setSecure(boolean secure) {
    this.secure = secure;
  }

  public boolean isHttpOnly() {
    return httpOnly;
  }

  public void setHttpOnly(boolean httpOnly) {
    this.httpOnly = httpOnly;
  }
}
