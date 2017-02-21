package com.mljr.operators.entity.vo.chinaunicom;

import java.io.Serializable;
import java.util.List;

/**
 * @author gaoxi
 * @Time 2017/2/19
 */
public class ChinaUnicomVO implements Serializable {

    private UserInfoVO userInfo;

    private String acctBalance;

    private List<CallVO> callItems;

    private List<BillVO> billItems;

    private List<SMSVO> smsItems;

    public UserInfoVO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoVO userInfo) {
        this.userInfo = userInfo;
    }

    public String getAcctBalance() {
        return acctBalance;
    }

    public void setAcctBalance(String acctBalance) {
        this.acctBalance = acctBalance;
    }

    public List<CallVO> getCallItems() {
        return callItems;
    }

    public void setCallItems(List<CallVO> callItems) {
        this.callItems = callItems;
    }

    public List<BillVO> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<BillVO> billItems) {
        this.billItems = billItems;
    }

    public List<SMSVO> getSmsItems() {
        return smsItems;
    }

    public void setSmsItems(List<SMSVO> smsItems) {
        this.smsItems = smsItems;
    }
}
