package com.mljr.operators.entity.vo.statistics;

import java.io.Serializable;

/**
 * Created by gaoxi
 *
 * @Time: 2017/3/13 下午3:17
 */
public class CallNumberStatisticsVO implements Serializable {

  private static final long serialVersionUID = 3267887209724193285L;

  private String callNumber;// 号码

  private String callNumberAddress;// 号码归属地

  private int totalDuration;// 总共呼叫时长

  private int totalCount;// 总呼叫次数

  private int callerDuration;// 呼出时长

  private int callerCount;// 呼出次数

  private int calledDuration;// 呼入时长

  private int calledCount;// 呼入次数

  private int lastWeekCount;// 最近一周联系次数

  private int lastOneMonthCount;// 最近一个月联系次数(不包含前一周)

  private int lastThreeMonthCount;// 最近三个月联系次数(不包含前一月)

  private int threeMonthAgoCount;// 三个月前联系次数

  private int zeroCount; // 0-6点联系次数[0,6)

  private int sixCount;// 6-9点联系次数

  private int nineCount;// 9-18点联系次数

  private int eighteenCount;// 18-23点联系次数

  private int weekendCount;// 周末联系次数

  private int workingDayCount;// 工作日联系次数

  private int holidayCount;// 法定节假日联系次数

  public String getCallNumber() {
    return callNumber;
  }

  public void setCallNumber(String callNumber) {
    this.callNumber = callNumber;
  }

  public String getCallNumberAddress() {
    return callNumberAddress;
  }

  public void setCallNumberAddress(String callNumberAddress) {
    this.callNumberAddress = callNumberAddress;
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

  public int getCallerDuration() {
    return callerDuration;
  }

  public void setCallerDuration(int callerDuration) {
    this.callerDuration = callerDuration;
  }

  public int getCallerCount() {
    return callerCount;
  }

  public void setCallerCount(int callerCount) {
    this.callerCount = callerCount;
  }

  public int getCalledDuration() {
    return calledDuration;
  }

  public void setCalledDuration(int calledDuration) {
    this.calledDuration = calledDuration;
  }

  public int getCalledCount() {
    return calledCount;
  }

  public void setCalledCount(int calledCount) {
    this.calledCount = calledCount;
  }

  public int getLastWeekCount() {
    return lastWeekCount;
  }

  public void setLastWeekCount(int lastWeekCount) {
    this.lastWeekCount = lastWeekCount;
  }

  public int getLastOneMonthCount() {
    return lastOneMonthCount;
  }

  public void setLastOneMonthCount(int lastOneMonthCount) {
    this.lastOneMonthCount = lastOneMonthCount;
  }

  public int getLastThreeMonthCount() {
    return lastThreeMonthCount;
  }

  public void setLastThreeMonthCount(int lastThreeMonthCount) {
    this.lastThreeMonthCount = lastThreeMonthCount;
  }

  public int getThreeMonthAgoCount() {
    return threeMonthAgoCount;
  }

  public void setThreeMonthAgoCount(int threeMonthAgoCount) {
    this.threeMonthAgoCount = threeMonthAgoCount;
  }

  public int getZeroCount() {
    return zeroCount;
  }

  public void setZeroCount(int zeroCount) {
    this.zeroCount = zeroCount;
  }

  public int getSixCount() {
    return sixCount;
  }

  public void setSixCount(int sixCount) {
    this.sixCount = sixCount;
  }

  public int getNineCount() {
    return nineCount;
  }

  public void setNineCount(int nineCount) {
    this.nineCount = nineCount;
  }

  public int getEighteenCount() {
    return eighteenCount;
  }

  public void setEighteenCount(int eighteenCount) {
    this.eighteenCount = eighteenCount;
  }

  public int getWeekendCount() {
    return weekendCount;
  }

  public void setWeekendCount(int weekendCount) {
    this.weekendCount = weekendCount;
  }

  public int getWorkingDayCount() {
    return workingDayCount;
  }

  public void setWorkingDayCount(int workingDayCount) {
    this.workingDayCount = workingDayCount;
  }

  public int getHolidayCount() {
    return holidayCount;
  }

  public void setHolidayCount(int holidayCount) {
    this.holidayCount = holidayCount;
  }
}
