package com.mljr.operators.entity.vo.statistics;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gaoxi
 *
 * @Time: 2017/3/13 上午11:36
 */
public class SmsStatisticsVO implements Serializable{

    private static final long serialVersionUID = 2899860894918601173L;

    private String smsType;

    private Date minDateTime;

    private Date maxDateTime;

    private String minDateTimeStr;

    private String maxDateTimeStr;

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public Date getMinDateTime() {
        return minDateTime;
    }

    public void setMinDateTime(Date minDateTime) {
        this.minDateTime = minDateTime;
    }

    public Date getMaxDateTime() {
        return maxDateTime;
    }

    public void setMaxDateTime(Date maxDateTime) {
        this.maxDateTime = maxDateTime;
    }

    public String getMinDateTimeStr() {
        return minDateTimeStr;
    }

    public void setMinDateTimeStr(String minDateTimeStr) {
        this.minDateTimeStr = minDateTimeStr;
    }

    public String getMaxDateTimeStr() {
        return maxDateTimeStr;
    }

    public void setMaxDateTimeStr(String maxDateTimeStr) {
        this.maxDateTimeStr = maxDateTimeStr;
    }
}
