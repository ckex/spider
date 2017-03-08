package com.mljr.operators.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Stopwatch;
import com.mljr.operators.entity.dto.chinaunicom.*;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author gaoxi
 * @time 2017/3/2
 */
public class ChinaUnicomUtil {

  private static final Logger logger = LoggerFactory.getLogger(ChinaUnicomUtil.class);

  private ChinaUnicomUtil() {}

  public static HttpRespDTO<UserInfoDTO> validatePackageInfo(String cookies, String url) {
    HttpRespDTO respDTO = HttpRespDTO.me().setSucc(false);
    try {
      String json = request(url, cookies);
      if (null != json) {
        JSONObject jsonObject = JSON.parseObject(json);
        if (jsonObject.containsKey("packageInfo")) {
          JSONObject packageObj = (JSONObject) jsonObject.get("packageInfo");
          if (packageObj.containsKey("respCode")
              && "0000".equals(packageObj.getString("respCode"))) {
            respDTO.setSucc(true).setBody(parse(json, UserInfoDTO.class));
          }
        }
      }
    } catch (Exception e) {
      logger.error("validate chinaunicom data.url:{}", url, ExceptionUtils.getStackTrace(e));
    }
    return respDTO;
  }

  public static HttpRespDTO<CallDTO> validateCall(String cookies, String url) {
    HttpRespDTO respDTO = HttpRespDTO.me().setSucc(false);
    try {
      String json = request(url, cookies);
      if (null != json) {
        JSONObject jsonObject = JSON.parseObject(json);
        if (jsonObject.containsKey("isSuccess")
            && true == jsonObject.getBooleanValue("isSuccess")) {
          respDTO.setSucc(true).setBody(parse(json, CallDTO.class));
        }
      }
    } catch (Exception e) {
      logger.error("validate chinaunicom data.url:{}", url, ExceptionUtils.getStackTrace(e));
    }
    return respDTO;
  }

  public static HttpRespDTO<BillDTO> validateBill(String cookies, String url) {
    HttpRespDTO respDTO = HttpRespDTO.me().setSucc(false);
    try {
      String json = request(url, cookies);
      if (null != json) {
        JSONObject jsonObject = JSON.parseObject(json);
        if (jsonObject.containsKey("rspcode") && "0000".equals(jsonObject.getString("rspcode"))) {
          respDTO.setSucc(true).setBody(parse(json, BillDTO.class));
        }
      }
    } catch (Exception e) {
      logger.error("validate chinaunicom data.url:{}", url, ExceptionUtils.getStackTrace(e));
    }
    return respDTO;
  }

  public static HttpRespDTO<SMSDTO> validateSMS(String cookies, String url) {
    HttpRespDTO respDTO = HttpRespDTO.me().setSucc(false);
    try {
      String json = request(url, cookies);
      if (null != json) {
        JSONObject jsonObject = JSON.parseObject(json);
        if (jsonObject.containsKey("isSuccess")
            && true == (jsonObject.getBooleanValue("isSuccess"))) {
          respDTO.setSucc(true).setBody(parse(json, SMSDTO.class));
        }
      }
    } catch (Exception e) {
      logger.error("validate chinaunicom data.url:{}", url, ExceptionUtils.getStackTrace(e));
    }
    return respDTO;
  }

  public static HttpRespDTO<FlowDetailDTO> validateFlow(String cookies, String url) {
    HttpRespDTO respDTO = HttpRespDTO.me().setSucc(false);
    try {
      String json = request(url, cookies);
      if (null != json) {
        JSONObject jsonObject = JSON.parseObject(json);
        if (jsonObject.containsKey("rspcode") && "0000".equals(jsonObject.getString("rspcode"))) {
          respDTO.setSucc(true).setBody(parse(json, FlowDetailDTO.class));
        }
      }
    } catch (Exception e) {
      logger.error("validate chinaunicom data.url:{}", url, ExceptionUtils.getStackTrace(e));
    }
    return respDTO;
  }

  public static HttpRespDTO<FlowRecordDTO> validateFlowRecord(String cookies, String url) {
    HttpRespDTO respDTO = HttpRespDTO.me().setSucc(false);
    try {
      String json = request(url, cookies);
      if (null != json) {
        JSONObject jsonObject = JSON.parseObject(json);
        if (jsonObject.containsKey("isSuccess")
            && true == jsonObject.getBooleanValue("isSuccess")) {
          respDTO.setSucc(true).setBody(parse(json, FlowRecordDTO.class));
        }
      }
    } catch (Exception e) {
      logger.error("validate chinaunicom data.url:{}", url, ExceptionUtils.getStackTrace(e));
    }
    return respDTO;
  }


  public static <T> T parse(String json, Class<T> c) {
    return JSON.parseObject(json, c);
  }

  private static String request(String url, String cookies) {
    int i = 0;
    String json = null;
    do {
      Stopwatch stopwatch = Stopwatch.createStarted();
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
          json = response.body();
        }
      } catch (IOException e) {
        logger.error("request failure.url:{}", url, e);
      } catch (Exception e) {
        logger.error("request failure.url:{}", url, e);
      }
      logger.info(String.format("chinaunicom request url {%s} use time:{%s}  count:{%s}", url,
          stopwatch.elapsed(TimeUnit.MILLISECONDS), i));
      i++;
      if (i > 0) { // 失败重试之后 线sleep一下
        try {
          TimeUnit.MILLISECONDS.sleep(RandomUtils.nextLong(1000L, 2000L));
        } catch (InterruptedException e) {

        }
      }
    } while (i < 3 && null == json);
    return json;
  }
}
