package com.mljr.operators.common.constant;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
public enum OperatorsEnum {

  CHINAUNICOM("1", "联通"), CHINAMOBILE("2", "移动"), CHINATELECOM("3", "电信"),;

  private String code;

  private String desc;

  OperatorsEnum(String code, String name) {
    this.code = code;
    this.desc = name;
  }

  public String getCode() {
    return code;
  }

  public String getDesc() {
    return desc;
  }

  public static OperatorsEnum indexOf(String value) {
    for (OperatorsEnum enums : OperatorsEnum.values()) {
      if (value.equals(enums.getCode())) {
        return enums;
      }
    }
    return null;
  }

  public static String getCodeByDesc(String desc){
    for (OperatorsEnum e : OperatorsEnum.values()) {
      if(desc.contains(e.getDesc())){
        return e.getCode();
      }
    }
    return null;
  }


}
