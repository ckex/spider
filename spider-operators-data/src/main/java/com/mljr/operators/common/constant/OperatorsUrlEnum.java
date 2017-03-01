package com.mljr.operators.common.constant;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mljr.operators.common.utils.IndexedEnumUtil;

import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/28
 */
public enum OperatorsUrlEnum implements IndexedEnum<OperatorsUrlEnum> {

  CHINA_UNICOM_LOGIN(10001,OperatorsEnum.CHINAUNICOM,ProvinceEnum.SH,"http://iservice.10010.com/e4/index.html"),
  CHINA_UNICOM_USER_INFO(10002,OperatorsEnum.CHINAUNICOM,ProvinceEnum.SH,"http://iservice.10010.com/e3/static/query/searchPerInfo/?_=1488265304303"),
  CHINA_UNICOM_REMAINING(10003,OperatorsEnum.CHINAUNICOM,ProvinceEnum.SH,"http://iservice.10010.com/e3/static/query/userinfoquery?_=1488265304303"),
  CHINA_UNICOM_CALL(10004,OperatorsEnum.CHINAUNICOM,ProvinceEnum.SH,"http://iservice.10010.com/e3/static/query/callDetail?_=1488265304303&pageNo=%s&pageSize=100&beginDate=%s&endDate=%s"),
  CHINA_UNICOM_SMS(10005,OperatorsEnum.CHINAUNICOM,ProvinceEnum.SH,"http://iservice.10010.com/e3/static/query/sms?_=1488265304303&pageSize=100&pageNo=%s&begindate=%s&enddate=%s"),
  CHINA_UNICOM_BILL(10006,OperatorsEnum.CHINAUNICOM,ProvinceEnum.SH,"http://iservice.10010.com/e3/static/query/queryHistoryBill?_=1488265304303&querytype=0001&querycode=0001&billdate=%s&flag=2"),
  CHINA_UNICOM_FLOW(10007,OperatorsEnum.CHINAUNICOM,ProvinceEnum.SH,"http://iservice.10010.com/e3/static/query/callFlow?_=1488265304303&pageSize=100&pageNo=%s&beginDate=%s&endDate=%s"),
  CHINA_UNICOM_FLOW_RECORD(10008,OperatorsEnum.CHINAUNICOM,ProvinceEnum.SH,"http://iservice.10010.com/e3/static/query/callNetPlayRecord?_=1488265304303&pageSize=100&pageNo=%s&beginDate=%s&endDate=%s"),


  CHINA_MOBILE_CALL(20001,OperatorsEnum.CHINAUNICOM,ProvinceEnum.SH,""),
  CHINA_MOBILE_SMS(20002,OperatorsEnum.CHINAUNICOM,ProvinceEnum.SH,""),

  CHINA_TELECOM_CALL(30001,OperatorsEnum.CHINAUNICOM,ProvinceEnum.SH,""),

  ;

  private int index;//eg：10001 1:自增 00:省份 01:类型

  private String url;

  private OperatorsEnum operators;

  private ProvinceEnum province;

  OperatorsUrlEnum(int index,OperatorsEnum operators,ProvinceEnum province,String url){
    this.index=index;
    this.url=url;
    this.operators=operators;
    this.province=province;
  }

  @Override
  public int getIndex() {
    return this.index;
  }

  public String getUrl() {
    return this.url;
  }

  public OperatorsEnum getOperators() {
    return this.operators;
  }

  public ProvinceEnum getProvince() {
    return province;
  }

  private static final ImmutableMap<Integer, OperatorsUrlEnum> INDEXS =
          IndexedEnumUtil.toIndexes(values());

  public static OperatorsUrlEnum indexOf(int value) {
    return INDEXS.get(value);
  }

  public static List<OperatorsUrlEnum> indexOf(OperatorsEnum value) {
    List<OperatorsUrlEnum> list = Lists.newArrayList();
    for (OperatorsUrlEnum enums : OperatorsUrlEnum.values()) {
      if (enums.getOperators() == value) {
        list.add(enums);
      }
    }
    return list;
  }
}
