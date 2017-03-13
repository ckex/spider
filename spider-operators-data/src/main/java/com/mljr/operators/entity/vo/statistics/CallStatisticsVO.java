package com.mljr.operators.entity.vo.statistics;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gaoxi
 *
 * @Time: 2017/3/13 上午11:36
 */
public class CallStatisticsVO implements Serializable {
    public static class MaxMinDate {
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
    }

    public static class ByMonth {
        private int uniqNum;
        private int uniqCity;

        private int totalDuraton;
        private int totalCount;

        private int outDuration;
        private int inDuration;

        private int outCount;
        private int inCount;

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
    }

    public static class ByAddress {
        private int uniqNum;

        private int totalDuraton;
        private int totalCount;

        private int outDuration;
        private int inDuration;

        private int outCount;
        private int inCount;

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
    }

}
