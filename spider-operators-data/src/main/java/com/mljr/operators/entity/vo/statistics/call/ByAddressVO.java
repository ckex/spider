package com.mljr.operators.entity.vo.statistics.call;

public class ByAddressVO {
    private int uniqNum;

    private int totalDuraton;
    private int totalCount;

    private int outDuration;
    private int inDuration;

    private int outCount;
    private int inCount;

    private String city;

    public int getUniqNum() {
        return uniqNum;
    }

    public void setUniqNum(int uniqNum) {
        this.uniqNum = uniqNum;
    }

    public int getTotalDuraton() {
        return totalDuraton;
    }

    public void setTotalDuraton(int totalDuraton) {
        this.totalDuraton = totalDuraton;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "ByAddressVO{" +
                "uniqNum=" + uniqNum +
                ", totalDuraton=" + totalDuraton +
                ", totalCount=" + totalCount +
                ", outDuration=" + outDuration +
                ", inDuration=" + inDuration +
                ", outCount=" + outCount +
                ", inCount=" + inCount +
                ", city='" + city + '\'' +
                '}';
    }
}