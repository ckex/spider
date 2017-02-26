package com.mljr.utils;

/**
 * Created by gaoxi on 2017/1/4.
 */
public class RandomUtils {

  public static boolean randomPrint(int random) {
    return Math.random() * random < 1;
  }
}
