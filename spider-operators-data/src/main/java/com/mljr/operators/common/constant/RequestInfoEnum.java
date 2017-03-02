package com.mljr.operators.common.constant;

import com.google.common.collect.ImmutableMap;
import com.mljr.operators.common.utils.IndexedEnumUtil;

/**
 * @author gaoxi
 * @time 2017/2/27
 */
public enum RequestInfoEnum implements IndexedEnum<RequestInfoEnum> {

  INIT(1, "初始化"), RUNNING(2,"运行中"),SUCCESS(3, "成功"), ERROR(4, "失败"),;

  private int code;

  private String desc;

  RequestInfoEnum(int code, String name) {
    this.code = code;
    this.desc = name;
  }


  @Override
  public int getIndex() {
    return this.code;
  }

  private static final ImmutableMap<Integer, RequestInfoEnum> INDEXS =
      IndexedEnumUtil.toIndexes(values());

  public static RequestInfoEnum indexOf(int value) {
    return INDEXS.get(value);
  }

}
