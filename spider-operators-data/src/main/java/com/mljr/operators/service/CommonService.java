package com.mljr.operators.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by songchi on 17/3/9.
 */
public class CommonService {

    private static final Logger logger = LoggerFactory.getLogger(CommonService.class);

    public static int toSecond(String duration) {
        try {
            LocalTime time = LocalTime.parse(duration, DateTimeFormatter.ofPattern("HH:mm:ss"));

            return time.getSecond() + time.getMinute() * 60 + time.getHour() * 60 * 60;
        } catch (Exception e) {
            logger.error("解析时间错误", e);
        }
        return 0;
    }
}
