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
        private Date minCallOutDate;

        private Date maxCallOutDate;

        private Date minCallInDate;

        private Date maxCallInDate;

        public Date getMinCallOutDate() {
            return minCallOutDate;
        }

        public void setMinCallOutDate(Date minCallOutDate) {
            this.minCallOutDate = minCallOutDate;
        }

        public Date getMaxCallOutDate() {
            return maxCallOutDate;
        }

        public void setMaxCallOutDate(Date maxCallOutDate) {
            this.maxCallOutDate = maxCallOutDate;
        }

        public Date getMinCallInDate() {
            return minCallInDate;
        }

        public void setMinCallInDate(Date minCallInDate) {
            this.minCallInDate = minCallInDate;
        }

        public Date getMaxCallInDate() {
            return maxCallInDate;
        }

        public void setMaxCallInDate(Date maxCallInDate) {
            this.maxCallInDate = maxCallInDate;
        }
    }

    public static class ByMonth {
        private int uniqNum;
        private int uniqCity;

        private int totalDuraton;
        private int totalCount;

        private int totalCallOutDuraton;
        private int totalCallInDuraton;

        private int totalCallOutCount;
        private int totalCallInCount;

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
            return this.getTotalCallInDuraton() + this.getTotalCallOutCount();
        }

        public void setTotalDuraton(int totalDuraton) {
            this.totalDuraton = totalDuraton;
        }

        public int getTotalCount() {
            return this.getTotalCallInCount() + this.getTotalCallOutCount();
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getTotalCallOutDuraton() {
            return totalCallOutDuraton;
        }

        public void setTotalCallOutDuraton(int totalCallOutDuraton) {
            this.totalCallOutDuraton = totalCallOutDuraton;
        }

        public int getTotalCallInDuraton() {
            return totalCallInDuraton;
        }

        public void setTotalCallInDuraton(int totalCallInDuraton) {
            this.totalCallInDuraton = totalCallInDuraton;
        }

        public int getTotalCallOutCount() {
            return totalCallOutCount;
        }

        public void setTotalCallOutCount(int totalCallOutCount) {
            this.totalCallOutCount = totalCallOutCount;
        }

        public int getTotalCallInCount() {
            return totalCallInCount;
        }

        public void setTotalCallInCount(int totalCallInCount) {
            this.totalCallInCount = totalCallInCount;
        }
    }

    public static class ByAddress {
        private int uniqNum;
        private int uniqCity;

        private int totalDuraton;
        private int totalCount;

        private int totalCallOutDuraton;
        private int totalCallInDuraton;

        private int totalCallOutCount;
        private int totalCallInCount;

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
            return this.getTotalCallInDuraton() + this.getTotalCallOutCount();
        }

        public void setTotalDuraton(int totalDuraton) {
            this.totalDuraton = totalDuraton;
        }

        public int getTotalCount() {
            return this.getTotalCallInCount() + this.getTotalCallOutCount();
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getTotalCallOutDuraton() {
            return totalCallOutDuraton;
        }

        public void setTotalCallOutDuraton(int totalCallOutDuraton) {
            this.totalCallOutDuraton = totalCallOutDuraton;
        }

        public int getTotalCallInDuraton() {
            return totalCallInDuraton;
        }

        public void setTotalCallInDuraton(int totalCallInDuraton) {
            this.totalCallInDuraton = totalCallInDuraton;
        }

        public int getTotalCallOutCount() {
            return totalCallOutCount;
        }

        public void setTotalCallOutCount(int totalCallOutCount) {
            this.totalCallOutCount = totalCallOutCount;
        }

        public int getTotalCallInCount() {
            return totalCallInCount;
        }

        public void setTotalCallInCount(int totalCallInCount) {
            this.totalCallInCount = totalCallInCount;
        }
    }

}
