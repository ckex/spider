package com.mljr.operators.common.constant;

/**
 * Created by gaoxi
 *
 * @Time: 2017/3/13 下午12:54
 */
public enum SmsTypeEnum {

  ALL("所有"), RECEIVE("接收"), SEND("发送"),;

  private String desc;

  SmsTypeEnum(String desc) {
    this.desc = desc;
  }

  public String getDesc() {
    return desc;
  }
}
