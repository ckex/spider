package com.mljr.operators.common.utils;

import com.google.common.collect.Lists;
import com.mljr.operators.entity.chinamobile.DatePair;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 * Created by gaoxi on 2017/1/16.
 */
public class DateUtil {

  private DateUtil() {}

  public static final String PATTERN_M = "M";
  public static final String PATTERN_yyyy = "yyyy";
  public static final String PATTERN_yyyy_MM = "yyyy-MM";
  public static final String PATTERN_yyyy_MM_dd = "yyyy-MM-dd";
  public static final String PATTERN_yyyyMMdd = "yyyyMMdd";
  public static final String PATTERN_yyyyMM="yyyyMM";
  public static final String PATTERN_MMdd = "MM-dd";
  public static final String PATTERN_yyMMdd = "yy/MM/dd";
  public static final String PATTERN_yyyyMMddHHmmss = "yyyyMMddHHmmss";
  public static final String PATTERN_yyyyMMddHHmmssS = "yyyyMMddHHmmssS";
  public static final String PATTERN_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
  public static final String PATTERN_yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
  public static final String PATTERN_HH_mm_ss = "HH:mm:ss";

  public static Date defaultStringToDate(String sdate) {
    String[] patterns = new String[] {PATTERN_yyyy_MM_dd, PATTERN_yyyyMMdd};
    return stringToDate(sdate, patterns);
  }

  public static Date stringToDate(String sdate, String... patterns) {
    Date date = null;
    try {
      date = DateUtils.parseDate(sdate, patterns);
    } catch (ParseException e) {
      throw new RuntimeException("日期解析失败", e);
    }
    return date;
  }

  public static LocalDate stringToLocalDate(String date, String pattern) {
    return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
  }

  public static String dateToString(LocalDate localDate, String pattern) {
    return localDate.format(DateTimeFormatter.ofPattern(pattern));
  }

  public static String dateToString(Date date, String format) {
    return DateFormatUtils.format(date, format);
  }

  /**
   * 往前推多少个月 不包括当月
   * 
   * @param monthCount 多少月
   * @param pattern 格式化
   * @return
   */
  public static List<DatePair> getEachMonth(int monthCount, String pattern) {
    List<DatePair> list = Lists.newArrayList();
    LocalDate today = LocalDate.now();
    for (int i = 1; i <= monthCount; i++) {
      LocalDate first = LocalDate.of(today.getYear(), today.getMonth(), 1).minusMonths(i);
      LocalDate last = first.plusMonths(1).minusDays(1);
      list.add(new DatePair(dateToString(first, pattern), dateToString(last, pattern)));
    }
    return list;
  }

  /**
   * 根据日期往前推多少个月
   * 
   * @param date 日期
   * @param monthCount 获取多个月
   * @param pattern 格式
   * @return
   */
  public static List<DatePair> getPreEachOfMonth(LocalDate date, int monthCount, String pattern) {
    List<DatePair> list = Lists.newArrayList();
    for (int i = 1; i <= monthCount; i++) {
      LocalDate first = LocalDate.of(date.getYear(), date.getMonth(), 1).minusMonths(i);
      LocalDate last = first.plusMonths(1).minusDays(1);
      list.add(new DatePair(dateToString(first, pattern), dateToString(last, pattern)));
    }
    return list;
  }

  /**
   * 获取startDate to endDate 每一天(包含开始日期、结束日期)
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param pattern 格式化
   * @return
   */
  public static List<DatePair> getEachDayOfBetween(LocalDate startDate, LocalDate endDate,
      String pattern) {
    List<DatePair> list = Lists.newArrayList();
    if (startDate.isAfter(endDate)) {
      startDate = endDate;
      endDate = startDate;
    }
    long days = startDate.until(endDate, ChronoUnit.DAYS);
    for (int i = 0; i <= days; i++) {
      String sDate = dateToString(startDate.plusDays(i), pattern);
      list.add(new DatePair(sDate, sDate));
    }
    return list;
  }
  
  public static LocalDate dateToLocalDate(Date date) {
    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
  }

  public static Date localDateToDate(LocalDate localDate) {
    return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
  }
}
