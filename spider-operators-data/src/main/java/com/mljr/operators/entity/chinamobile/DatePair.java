package com.mljr.operators.entity.chinamobile;

import com.google.common.collect.Lists;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

/**
 * Created by songchi on 17/2/24.
 */
public class DatePair {
    private String startDate;
    private String endDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "DatePair{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    public static List<DatePair> getLatestDatePair(int months) {
        List<DatePair> ret = Lists.newArrayList();
        for (int i = 0; i < months; i++) {

            LocalDate today = LocalDate.now().minusMonths(i);
            //本月的第一天
            LocalDate firstday = LocalDate.of(today.getYear(), today.getMonth(), 1);
            //本月的最后一天
            LocalDate lastDay = today.with(TemporalAdjusters.lastDayOfMonth());
            DatePair pair = new DatePair();
            pair.setStartDate(firstday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            pair.setEndDate(lastDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            ret.add(pair);
        }

        return ret;
    }

}
