package com.mljr.spider.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SpiderBankCardLocationDo implements Serializable {
    private static final long serialVersionUID = 1481348112773L;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpiderBankCardLocationDo that = (SpiderBankCardLocationDo) o;

        if (bankCard11Digits != null ? !bankCard11Digits.equals(that.bankCard11Digits) : that.bankCard11Digits != null)
            return false;
        if (bankName != null ? !bankName.equals(that.bankName) : that.bankName != null) return false;
        if (bankCardType != null ? !bankCardType.equals(that.bankCardType) : that.bankCardType != null) return false;
        if (bankCardName != null ? !bankCardName.equals(that.bankCardName) : that.bankCardName != null) return false;
        if (bankCardProvince != null ? !bankCardProvince.equals(that.bankCardProvince) : that.bankCardProvince != null)
            return false;
        return bankCardCity != null ? bankCardCity.equals(that.bankCardCity) : that.bankCardCity == null;

    }

    @Override
    public int hashCode() {
        int result = bankCard11Digits != null ? bankCard11Digits.hashCode() : 0;
        result = 31 * result + (bankName != null ? bankName.hashCode() : 0);
        result = 31 * result + (bankCardType != null ? bankCardType.hashCode() : 0);
        result = 31 * result + (bankCardName != null ? bankCardName.hashCode() : 0);
        result = 31 * result + (bankCardProvince != null ? bankCardProvince.hashCode() : 0);
        result = 31 * result + (bankCardCity != null ? bankCardCity.hashCode() : 0);
        return result;
    }


}
