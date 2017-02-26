package com.mljr.operators.common.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gaoxi on 2017/1/16.
 */
public class DateUtil {

    private DateUtil() {
    }

    public static final String PATTERN_M = "M";
    public static final String PATTERN_yyyy = "yyyy";
    public static final String PATTERN_yyyy_MM = "yyyy-MM";
    public static final String PATTERN_yyyy_MM_dd = "yyyy-MM-dd";
    public static final String PATTERN_yyyyMMdd = "yyyyMMdd";
    public static final String PATTERN_MMdd = "MM-dd";
    public static final String PATTERN_yyMMdd = "yy/MM/dd";
    public static final String PATTERN_yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String PATTERN_yyyyMMddHHmmssS = "yyyyMMddHHmmssS";
    public static final String PATTERN_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_HH_mm_ss = "HH:mm:ss";

    public static Date defaultStringToDate(String sdate) {
        String[] patterns = new String[]{PATTERN_yyyy_MM_dd, PATTERN_yyyyMMdd};
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

    public static String dateToString(Date date,String format){
        if (date == null)
            return "";
        if (null == format || "".equals(format))
            format = PATTERN_yyyy_MM_dd_HH_mm_ss;
        SimpleDateFormat form = new SimpleDateFormat(format);
        return form.format(date);
    }

}
