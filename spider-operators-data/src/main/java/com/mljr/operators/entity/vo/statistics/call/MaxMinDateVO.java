package com.mljr.operators.entity.vo.statistics.call;

import java.util.Date;

public class MaxMinDateVO {
    private Date minCallDate;

    private Date maxCallDate;

    private String callType;

    public Date getMinCallDate() {
        return minCallDate;
    }

    public void setMinCallDate(Date minCallDate) {
        this.minCallDate = minCallDate;
    }

    public Date getMaxCallDate() {
        return maxCallDate;
    }

    public void setMaxCallDate(Date maxCallDate) {
        this.maxCallDate = maxCallDate;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    @Override
    public String toString() {
        return "MaxMinDateVO{" +
                "minCallDate=" + minCallDate +
                ", maxCallDate=" + maxCallDate +
                ", callType='" + callType + '\'' +
                '}';
    }
}