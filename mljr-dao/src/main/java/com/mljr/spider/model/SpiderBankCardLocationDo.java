package com.mljr.spider.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ckex created 2016-12-11 10:37:07:007
 * @explain -
 */
public class SpiderBankCardLocationDo implements Serializable {
  private static final long serialVersionUID = 1481423827589L;

  private String bankCard11Digits;
  private String bankName;
  private String bankCardType;
  private String bankCardName;
  private String bankCardProvince;
  private String bankCardCity;

  public void setBankCard11Digits(String bankCard11Digits) {
    this.bankCard11Digits = bankCard11Digits;
  }

  public String getBankCard11Digits() {
    return bankCard11Digits;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankCardType(String bankCardType) {
    this.bankCardType = bankCardType;
  }

  public String getBankCardType() {
    return bankCardType;
  }

  public void setBankCardName(String bankCardName) {
    this.bankCardName = bankCardName;
  }

  public String getBankCardName() {
    return bankCardName;
  }

  public void setBankCardProvince(String bankCardProvince) {
    this.bankCardProvince = bankCardProvince;
  }

  public String getBankCardProvince() {
    return bankCardProvince;
  }

  public void setBankCardCity(String bankCardCity) {
    this.bankCardCity = bankCardCity;
  }

  public String getBankCardCity() {
    return bankCardCity;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((bankCard11Digits == null) ? 0 : bankCard11Digits.hashCode());
    result = prime * result + ((bankName == null) ? 0 : bankName.hashCode());
    result = prime * result + ((bankCardType == null) ? 0 : bankCardType.hashCode());
    result = prime * result + ((bankCardName == null) ? 0 : bankCardName.hashCode());
    result = prime * result + ((bankCardProvince == null) ? 0 : bankCardProvince.hashCode());
    result = prime * result + ((bankCardCity == null) ? 0 : bankCardCity.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    SpiderBankCardLocationDo other = (SpiderBankCardLocationDo) obj;
    if (bankCard11Digits == null) {
      if (other.bankCard11Digits != null)
        return false;
    } else if (!bankCard11Digits.equals(other.bankCard11Digits))
      return false;
    if (bankName == null) {
      if (other.bankName != null)
        return false;
    } else if (!bankName.equals(other.bankName))
      return false;
    if (bankCardType == null) {
      if (other.bankCardType != null)
        return false;
    } else if (!bankCardType.equals(other.bankCardType))
      return false;
    if (bankCardName == null) {
      if (other.bankCardName != null)
        return false;
    } else if (!bankCardName.equals(other.bankCardName))
      return false;
    if (bankCardProvince == null) {
      if (other.bankCardProvince != null)
        return false;
    } else if (!bankCardProvince.equals(other.bankCardProvince))
      return false;
    if (bankCardCity == null) {
      if (other.bankCardCity != null)
        return false;
    } else if (!bankCardCity.equals(other.bankCardCity))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "SpiderBankCardLocationDo[bankCard11Digits=" + bankCard11Digits + ", bankName=" + bankName + ", bankCardType=" + bankCardType
        + ", bankCardName=" + bankCardName + ", bankCardProvince=" + bankCardProvince + ", bankCardCity=" + bankCardCity + "]";
  }
}
