package com.mljr.operators.common.utils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import java.util.Map;

/**
 * Created by songchi on 17/3/6.
 */
public class CookieUtils {
    public static String mapToString(Map<String, String> map) {

        return Joiner.on(";").join(map.entrySet().iterator());
    }


    public static Map<String, String> stringToMap(String cookieStr) {

        return Splitter.on(";").omitEmptyStrings().withKeyValueSeparator("=").split(cookieStr);
    }

}
