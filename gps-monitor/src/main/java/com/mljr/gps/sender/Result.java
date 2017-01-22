package com.mljr.gps.sender;

import com.alibaba.fastjson.JSON;

/**
 * Created by xi.gao
 * Date:2016/12/7
 */
public class Result {

    private static final String SUCCESS = "0";

    private String returnCode;//编码

    private String message;//信息

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static boolean isSucc(String json) {
        Result result = JSON.parseObject(json, Result.class);
        return null != result ? SUCCESS.equalsIgnoreCase(result.getReturnCode()) : false;
    }
}
