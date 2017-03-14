package com.mljr.operators.entity.vo.statistics.call;

public class ByMonthVO {
    private int uniqNum;
    private int uniqCity;

    private int totalDuration;
    private int totalCount;

    private int outDuration;
    private int inDuration;

    private int outCount;
    private int inCount;

    private String month;

    public int getUniqNum() {
        return uniqNum;
    }

    public void setUniqNum(int uniqNum) {
        this.uniqNum = uniqNum;
    }

    public int getUniqCity() {
        return uniqCity;
    }

    public void setUniqCity(int uniqCity) {
        this.uniqCity = uniqCity;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getOutDuration() {
        return outDuration;
    }

    public void setOutDuration(int outDuration) {
        this.outDuration = outDuration;
    }

    public int getInDuration() {
        return inDuration;
    }

    public void setInDuration(int inDuration) {
        this.inDuration = inDuration;
    }

    public int getOutCount() {
        return outCount;
    }

    public void setOutCount(int outCount) {
        this.outCount = outCount;
    }

    public int getInCount() {
        return inCount;
    }

    public void setInCount(int inCount) {
        this.inCount = inCount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "ByMonthVO{" +
                "uniqNum=" + uniqNum +
                ", uniqCity=" + uniqCity +
                ", totalDuration=" + totalDuration +
                ", totalCount=" + totalCount +
                ", outDuration=" + outDuration +
                ", inDuration=" + inDuration +
                ", outCount=" + outCount +
                ", inCount=" + inCount +
                ", month='" + month + '\'' +
                '}';
    }
}