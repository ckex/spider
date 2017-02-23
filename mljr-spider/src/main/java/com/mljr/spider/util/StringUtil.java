package com.mljr.spider.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by xi.gao Date:2016/12/11
 */
public class StringUtil {

  private StringUtil() {}

  /**
   * 银行卡最小长度位
   */
  public static final int BANK_CARD__MIX_LENGTH = 16;

  /**
   * string字符右填充
   *
   * @param str str字符串
   * @param size 长度
   * @param fillStr 填充的字符
   * @return 返回填充后的字符串
   */
  public static String stringRightFill(String str, int size, String fillStr) {
    if (StringUtils.isBlank(str))
      return "";
    if (str.length() < size)
      str = StringUtils.rightPad(str, size, fillStr);
    return str;
  }

  /**
   * 银行卡不足16位时，用0补位
   *
   * @param str 字符串
   * @return 返回补位后的字符串
   */
  public static String bankCardDefaultFill(String str) {
    return stringRightFill(str, BANK_CARD__MIX_LENGTH, "0");
  }
}
