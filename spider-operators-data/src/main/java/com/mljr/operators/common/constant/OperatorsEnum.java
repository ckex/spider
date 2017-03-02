package com.mljr.operators.common.constant;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
public enum OperatorsEnum {

  CHINAUNICOM("1", "联通"), CHINAMOBILE("2", "移动"), CHINATELECOM("3", "电信"),;

  private String value;

  private String name;

  OperatorsEnum(String value, String name) {
    this.value = value;
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public String getName() {
    return name;
  }

  public static OperatorsEnum indexOf(String value) {
    for (OperatorsEnum enums : OperatorsEnum.values()) {
      if (value.equals(enums.value)) {
        return enums;
      }
    }
    return null;
  }

}
