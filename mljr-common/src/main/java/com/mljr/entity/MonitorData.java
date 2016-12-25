package com.mljr.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MonitorData implements Serializable {
    private String time;
    private String domain;
    private int totalRequests;
    private int freq200;
    private int freq401;
    private int freq403;
    private int freq404;
    private int freq500;
    private int freq501;
    private int freq504;
    // 成功率
    private double successRate;

    public MonitorData() {
    }

    public double getSuccessRate() {
        BigDecimal result =new BigDecimal(freq200*100).divide(new BigDecimal(totalRequests),2,BigDecimal.ROUND_HALF_EVEN);

        return result.doubleValue();
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getFreq200() {
        return freq200;
    }

    public void setFreq200(int freq200) {
        this.freq200 = freq200;
    }

    public int getFreq401() {
        return freq401;
    }

    public void setFreq401(int freq401) {
        this.freq401 = freq401;
    }

    public int getFreq403() {
        return freq403;
    }

    public void setFreq403(int freq403) {
        this.freq403 = freq403;
    }

    public int getFreq404() {
        return freq404;
    }

    public void setFreq404(int freq404) {
        this.freq404 = freq404;
    }

    public int getFreq500() {
        return freq500;
    }

    public void setFreq500(int freq500) {
        this.freq500 = freq500;
    }

    public int getFreq501() {
        return freq501;
    }

    public void setFreq501(int freq501) {
        this.freq501 = freq501;
    }

    public int getFreq504() {
        return freq504;
    }

    public void setFreq504(int freq504) {
        this.freq504 = freq504;
    }

    public int getTotalRequests() {
        return totalRequests;
    }

    public void setTotalRequests(int totalRequests) {
        this.totalRequests = totalRequests;
    }


    @Override
    public String toString() {
        return "MonitorData{" +
                "time='" + time + '\'' +
                ", domain='" + domain + '\'' +
                ", freq200=" + freq200 +
                ", freq401=" + freq401 +
                ", freq403=" + freq403 +
                ", freq404=" + freq404 +
                ", freq500=" + freq500 +
                ", freq501=" + freq501 +
                ", freq504=" + freq504 +
                '}';
    }
}