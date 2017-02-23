package com.mljr.spider.util;

import com.google.common.collect.ImmutableMap;
import com.ucloud.umq.common.ServiceConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xi.gao Date:2016/12/16
 */
public class KeyCacheUtils {

  private static final String SEPARATOR = ";";

  private static ConcurrentHashMap<String, Boolean> BAIDU__KEY_CACHE = new ConcurrentHashMap<>();

  private static ConcurrentHashMap<String, Boolean> AMAP_KEY_CACHE = new ConcurrentHashMap<>();

  public static final String DEFAUT_LBS_BAIDU_URL = "http://api.map.baidu.com/geocoder/v2/?output=json&ak=%s&address=%s&city=%s";

  public static final String DEFAUT_LBS_AMAP_URL = "http://restapi.amap.com/v3/geocode/geo?key=%s&address=%s&city=%s&output=JSON";


  static {
    load();
  }

  private static void load() {
    String[] baidu_key = splitKey(ServiceConfig.getLBSBaiduKey());

    String[] amap_key = splitKey(ServiceConfig.getLBSAMapKey());

    for (String key : baidu_key) {
      BAIDU__KEY_CACHE.put(key, Boolean.FALSE);
    }

    for (String key : amap_key) {
      AMAP_KEY_CACHE.put(key, Boolean.FALSE);
    }

  }

  /**
   * 获取有效Key
   *
   * @param lbskey
   * @return
   */
  public static String getValidKey(LBSKEY lbskey) {
    ConcurrentHashMap<String, Boolean> map = null;
    switch (lbskey) {
      case BAIDU:
        map = BAIDU__KEY_CACHE;
        break;
      case AMAP:
        map = AMAP_KEY_CACHE;
        break;
      default:
        map = null;
        break;
    }
    if (null != map) {
      for (Map.Entry<String, Boolean> entry : map.entrySet()) {
        if (entry.getValue().booleanValue()) {
          return entry.getKey();
        }
      }
    }
    return null;
  }

  /**
   * 设置key 无效
   *
   * @param lbskey
   * @param key
   * @param flag
   */
  public static void setInValidKey(LBSKEY lbskey, String key, Boolean flag) {
    switch (lbskey) {
      case BAIDU:
        BAIDU__KEY_CACHE.put(key, flag);
        break;
      case AMAP:
        AMAP_KEY_CACHE.put(key, flag);
        break;
    }
  }

  /**
   * 获取所有Key
   *
   * @param lbskey
   * @return
   */
  public static ConcurrentHashMap<String, Boolean> getAllKey(LBSKEY lbskey) {
    switch (lbskey) {
      case AMAP:
        return AMAP_KEY_CACHE;
      case BAIDU:
        return BAIDU__KEY_CACHE;
      default:
        return null;
    }
  }

  private static String[] splitKey(String keyString) {
    return keyString.split(SEPARATOR);
  }


  public enum LBSKEY {
    BAIDU, AMAP
  }
}
