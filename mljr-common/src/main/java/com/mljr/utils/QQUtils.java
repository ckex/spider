package com.mljr.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.mljr.common.ServiceConfig;
import com.mljr.constant.RedisConstant;
import com.mljr.entity.CCookie;
import com.mljr.entity.QQCookie;
import org.openqa.selenium.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gaoxi on 2017/1/8.
 */
public class QQUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(QQUtils.class);

  private QQUtils() {}

  public static final String QQ_P_SKY = "p_skey";

  public static final String QQ_COOKIE = "cookie";

  public static final String QQ_LOGIN = "login_qq";

  public static final int QQ_DEFAULT_COUNT = 20;

  /**
   * 默认分页
   */
  public static final int QQ_DEFAULT_PAGE = 10;

  /**
   * QQ首页动态
   */
  public static final String QQ_INDEX_URL =
      "https://h5.qzone.qq.com/proxy/domain/ic2.qzone.qq.com/cgi-bin/feeds/feeds_html_act_all?scope=0&filter=all&flag=1&refresh=0&firstGetGroup=0&mixnocache=0&scene=0&begintime=undefined&icServerTime=&sidomain=qzonestyle.gtimg.cn&useutf8=1&outputhtmlfeed=1&refer=2&r=0.8536552901318883&uin="
          + QQ_LOGIN + "&count=" + QQ_DEFAULT_COUNT + "&g_tk=" + QQ_P_SKY + "&hostuin=%s&start=%s";

  public static Set<String> getAllQQ() {
    return ServiceConfig.getSpiderRedisClient().use(new Function<Jedis, Set<String>>() {
      @Override
      public Set<String> apply(Jedis jedis) {
        return jedis.smembers(RedisConstant.QQ_GROUPS_KEY);
      }
    });
  }

  /**
   * 随机获取QQ号码
   *
   * @param count 一次获取多少个QQ
   * @return
   */
  public static List<String> getRandomKey(final int count) {
    return ServiceConfig.getSpiderRedisClient().use(new Function<Jedis, List<String>>() {
      @Override
      public List<String> apply(Jedis jedis) {
        return jedis.srandmember(RedisConstant.QQ_GROUPS_KEY, count);
      }
    });
  }

  /**
   * 获取redis cookie
   *
   * @param key QQ号码
   * @return
   */
  public static QQCookie getRedisCookie(final String key) {
    return ServiceConfig.getSpiderRedisClient().use(new Function<Jedis, QQCookie>() {
      @Override
      public QQCookie apply(Jedis jedis) {
        return JSON.parseObject(jedis.get(key), QQCookie.class);
      }
    });
  }

  public static void setRedisKey(QQCookie cookie) {
    ServiceConfig.getSpiderRedisClient().use(new Function<Jedis, Object>() {
      @Override
      public Object apply(Jedis jedis) {
        return jedis.set(cookie.getUser(), JSON.toJSONString(cookie));
      }
    });
  }

  /**
   * 分割QQ user:password
   *
   * @param key
   * @return
   */
  public static String[] spiltQQ(String key) {
    if (key.indexOf(":") < 0)
      throw new IllegalArgumentException("key is error.");
    return key.split(":");
  }

  public static CCookie convert(Cookie cookie) {
    CCookie entity = new CCookie();
    entity.setDomain(cookie.getDomain());
    entity.setExpiry(cookie.getExpiry());
    entity.setHttpOnly(cookie.isHttpOnly());
    entity.setName(cookie.getName());
    entity.setValue(cookie.getValue());
    entity.setPath(cookie.getPath());
    entity.setSecure(cookie.isSecure());
    return entity;
  }

  public static Cookie convert(CCookie cookie) {
    Cookie.Builder builder = new Cookie.Builder(cookie.getName(), cookie.getValue()).domain(cookie.getDomain()).expiresOn(cookie.getExpiry())
        .isHttpOnly(cookie.isHttpOnly()).isSecure(cookie.isSecure()).path(cookie.getPath());
    return builder.build();
  }

  /**
   * @param str cookie中skey值
   * @return 计算g_tk值
   */
  public static String getG_TK(String str) {
    int hash = 5381;
    for (int i = 0, len = str.length(); i < len; ++i) {
      hash += (hash << 5) + (int) (char) str.charAt(i);
    }
    return (hash & 0x7fffffff) + "";
  }

  public static LinkedTreeMap<String, Object> convert(String jsonp) {
    LinkedTreeMap<String, Object> treeMap = null;
    try {
      Gson gson = new GsonBuilder().create();
      Object object = gson.fromJson(jsonp, Object.class);
      if (object instanceof LinkedTreeMap) {
        treeMap = (LinkedTreeMap<String, Object>) object;
      }
    } catch (Exception e) {
      LOGGER.error("gson convert json failure.jsonp:{}", jsonp, e);
    }
    return treeMap;
  }

  /**
   * @param jsonp 服务端直接返回过来的数据
   * @return 抽取出json
   */
  public static String getJsonFromJsonp(String jsonp) {
    Pattern pattern = Pattern.compile("_Callback\\((.*)\\);");
    Matcher matcher = pattern.matcher(jsonp);
    if (matcher.find()) {
      return matcher.group(1);
    }
    return null;
  }
}
