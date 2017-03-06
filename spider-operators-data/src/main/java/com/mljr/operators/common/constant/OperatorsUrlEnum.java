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

  //中国联通
  CHINA_UNICOM_LOGIN(10001,OperatorsEnum.CHINAUNICOM,ProvinceEnum.SH,"http://iservice.10010.com/e4/index.html"),
  CHINA_UNICOM_USER_INFO(10002,OperatorsEnum.CHINAUNICOM,ProvinceEnum.SH,"http://iservice.10010.com/e3/static/query/searchPerInfo/?_=1488265304303"),
  CHINA_UNICOM_REMAINING(10003,OperatorsEnum.CHINAUNICOM,ProvinceEnum.SH,"http://iservice.10010.com/e3/static/query/userinfoquery?_=1488265304303"),
  CHINA_UNICOM_CALL(10004,OperatorsEnum.CHINAUNICOM,ProvinceEnum.SH,"http://iservice.10010.com/e3/static/query/callDetail?_=1488265304303&pageNo=%s&pageSize=100&beginDate=%s&endDate=%s"),
  CHINA_UNICOM_SMS(10005,OperatorsEnum.CHINAUNICOM,ProvinceEnum.SH,"http://iservice.10010.com/e3/static/query/sms?_=1488265304303&pageSize=100&pageNo=%s&begindate=%s&enddate=%s"),
  CHINA_UNICOM_BILL(10006,OperatorsEnum.CHINAUNICOM,ProvinceEnum.SH,"http://iservice.10010.com/e3/static/query/queryHistoryBill?_=1488265304303&querytype=0001&querycode=0001&billdate=%s&flag=2"),
  CHINA_UNICOM_FLOW(10007,OperatorsEnum.CHINAUNICOM,ProvinceEnum.SH,"http://iservice.10010.com/e3/static/query/callFlow?_=1488265304303&pageSize=100&pageNo=%s&beginDate=%s&endDate=%s"),
  CHINA_UNICOM_FLOW_RECORD(10008,OperatorsEnum.CHINAUNICOM,ProvinceEnum.SH,"http://iservice.10010.com/e3/static/query/callNetPlayRecord?_=1488265304303&pageSize=100&pageNo=%s&beginDate=%s&endDate=%s"),

  //中国移动
  CHINA_MOBILE_SMS_CODE(20001,OperatorsEnum.CHINAUNICOM,ProvinceEnum.SH,ChinaMobileConstant.Shanghai.SMS_CODE_PATTERN),
  CHINA_MOBILE_LOGIN(20002,OperatorsEnum.CHINAUNICOM,ProvinceEnum.SH,ChinaMobileConstant.Shanghai.LOGIN_PATTERN),

  CHINA_MOBILE_USER_INFO(2003,OperatorsEnum.CHINAMOBILE,ProvinceEnum.SH,ChinaMobileConstant.Shanghai.USER_INFO_PATTERN),
  CHINA_MOBILE_PACKAGE_INFO(2004,OperatorsEnum.CHINAMOBILE,ProvinceEnum.SH,ChinaMobileConstant.Shanghai.PACKAGE_INFO_PATTERN),
  CHINA_MOBILE_CURRENT_CALL(2005,OperatorsEnum.CHINAMOBILE,ProvinceEnum.SH,ChinaMobileConstant.Shanghai.CURRENT_CALL_INFO_PATTERN),
  CHINA_MOBILE_CURRENT_SMS(2006,OperatorsEnum.CHINAMOBILE,ProvinceEnum.SH,ChinaMobileConstant.Shanghai.CURRENT_SMS_INFO_PATTERN),
  CHINA_MOBILE_CURRENT_BILL(2007,OperatorsEnum.CHINAMOBILE,ProvinceEnum.SH,ChinaMobileConstant.Shanghai.CURRENT_BILL_INFO_PATTERN),
  CHINA_MOBILE_CURRENT_FLOW(2008,OperatorsEnum.CHINAMOBILE,ProvinceEnum.SH,ChinaMobileConstant.Shanghai.CURRENT_FLOW_INFO_PATTERN),

  CHINA_MOBILE_HISTORY_CALL(2009,OperatorsEnum.CHINAMOBILE,ProvinceEnum.SH,ChinaMobileConstant.Shanghai.HISTORY_CALL_INFO_PATTERN),
  CHINA_MOBILE_HISTORY_SMS(2010,OperatorsEnum.CHINAMOBILE,ProvinceEnum.SH,ChinaMobileConstant.Shanghai.HISTORY_SMS_INFO_PATTERN),
  CHINA_MOBILE_HISTORY_BILL(2011,OperatorsEnum.CHINAMOBILE,ProvinceEnum.SH,ChinaMobileConstant.Shanghai.HISTORY_BILL_INFO_PATTERN),
  CHINA_MOBILE_HISTORY_FLOW(2012,OperatorsEnum.CHINAMOBILE,ProvinceEnum.SH,ChinaMobileConstant.Shanghai.HISTORY_FLOW_INFO_PATTERN),

  //中国电信
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
