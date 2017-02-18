package com.mljr.operators.entity.vo.chinaunicom;

import java.io.Serializable;

/**
 * @author gaoxi
 * @Time 2017/2/17
 */
public class BillVO implements Serializable {

    private static final long serialVersionUID = 6004148136392327211L;

    private String feeName;//费用名称

    private String fee;//费用

    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
