package com.mljr.entity;

import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

public class MonitorData implements Serializable {
    private String time;
    private String domain;
    private String serverIp;
    private int totalRequests;
    private String statusCodes;
    private int freq200;
    private int freq301;
    private int freq302;
    private int freq304;
    private int freq307;
    private int freq401;
    private int freq403;
    private int freq404;
    private int freq500;
    private int freq501;
    private int freq504;

    public String getStatusCodes() {
        return statusCodes;
    }

    public void setStatusCodes(String statusCodes) {
        this.statusCodes = statusCodes;
    }

    // 成功率
    private double successRate;

    private int diffTime;

    public int getFreq301() {
        return freq301;
    }

    public void setFreq301(int freq301) {
        this.freq301 = freq301;
    }

    public int getFreq302() {
        return freq302;
    }

    public void setFreq302(int freq302) {
        this.freq302 = freq302;
    }

    public int getFreq304() {
        return freq304;
    }

    public void setFreq304(int freq304) {
        this.freq304 = freq304;
    }

    public int getFreq307() {
        return freq307;
    }

    public void setFreq307(int freq307) {
        this.freq307 = freq307;
    }

    public int getDiffTime() {
        try {
            Date dbDate = DateUtils.parseDate(time, "yy-MM-dd-HH-mm");
            long diff = System.currentTimeMillis() - dbDate.getTime();
            // 时间单位:分钟
            return (int) (diff / 1000 / 60);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void setDiffTime(int diffTime) {
        this.diffTime = diffTime;
    }




    public MonitorData() {
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public double getSuccessRate() {
        if (totalRequests == 0) {
            return 0;
        }
        BigDecimal result = new BigDecimal(freq200 * 100).divide(new BigDecimal(totalRequests), 2, BigDecimal.ROUND_HALF_EVEN);

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
                ", serverIp='" + serverIp + '\'' +
                ", totalRequests=" + totalRequests +
                ", freq200=" + freq200 +
                ", freq301=" + freq301 +
                ", freq302=" + freq302 +
                ", freq304=" + freq304 +
                ", freq307=" + freq307 +
                ", freq401=" + freq401 +
                ", freq403=" + freq403 +
                ", freq404=" + freq404 +
                ", freq500=" + freq500 +
                ", freq501=" + freq501 +
                ", freq504=" + freq504 +
                ", successRate=" + successRate +
                ", diffTime=" + diffTime +
                '}';
    }
}