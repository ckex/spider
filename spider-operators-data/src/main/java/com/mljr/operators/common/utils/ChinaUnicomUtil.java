package com.mljr.operators.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author gaoxi
 * @time 2017/3/2
 */
public class ChinaUnicomUtil {

  private static final Logger logger = LoggerFactory.getLogger(ChinaUnicomUtil.class);

  private ChinaUnicomUtil() {}

  public static <T> T request(String cookies, String url, Class<T> c) {
    String respStr = request(url, cookies);
    if (null != respStr) {
      return JSON.parseObject(respStr, c);
    }
    return null;
  }

  private static String request(String url, String cookies) {
    try {
      Connection connection = Jsoup.connect(url).timeout(60 * 1000).method(Connection.Method.POST)
          .header("User-Agent",
              "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:51.0) Gecko/20100101 Firefox/51.0")
          .ignoreContentType(true);
      if (!StringUtils.isBlank(cookies)) {
        connection.header("Cookie", cookies);
      }
      Connection.Response response = connection.execute();
      if (response.statusCode() == 200 && !StringUtils.isBlank(response.body())) {
        logger.info("get reslut json url:{} data:{}", url, response.body());
        return response.body();
      }
    } catch (IOException e) {
      logger.error("request failure.url:{}", url, e);
    } catch (Exception e) {
      logger.error("request failure.url:{}", url, e);
    }
    return null;
  }
}
