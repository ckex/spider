package com.mljr.operators.common.constant;

import com.google.common.collect.ImmutableMap;
import com.mljr.operators.common.utils.IndexedEnumUtil;

/**
 * @author gaoxi
 * @Time: 2017/3/23 下午9:56
 */
public enum ApiCodeEnum {

    SUCC(200, "成功"),
    SYSTEM_EXCEPTIION(-1, "系统异常"),;

    private int index;

    private String desc;

    ApiCodeEnum(int index, String desc) {
        this.index = index;
        this.desc = desc;
    }

    public int getIndex() {
        return this.index;
    }

    public String getDesc() {
        return desc;
    }
}
