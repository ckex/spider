package com.mljr.operators.common.constant;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
public enum OperatorsEnum {

    CHINAUNICOM(1, "联通"),
    CHINAMOBILE(2, "移动"),
    CHINATELECOM(3, "电信"),;

    private int value;

    private String name;

    OperatorsEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static OperatorsEnum indexOf(int value) {
        for (OperatorsEnum enums : OperatorsEnum.values()) {
            if (value == enums.value) {
                return enums;
            }
        }
        return null;
    }
}
