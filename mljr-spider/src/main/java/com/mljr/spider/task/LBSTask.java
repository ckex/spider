package com.mljr.spider.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mljr.spider.util.KeyCacheUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xi.gao Date:2016/12/16
 */
public class LBSTask implements Runnable {

  private static final String ADDRESS = "三林地铁站";

  private static final String CITY = "上海市";

  private static final int SUCCESS_CODE = 200;

  protected static final Logger logger = LoggerFactory.getLogger(LBSTask.class);

  @Override
  public void run() {

    try {
      logger.info("=====start check lbs baidu key=====");
      baiduLBSRequest();
    } catch (IOException e) {
      logger.error("lbs baidu request key failure.", e);
    } catch (Exception ex) {
      logger.error("lbs baidu request key failure.", ex);
    } finally {
      logger.info("=====end check lbs baidu key=====");
    }

    try {
      logger.info("=====start check lbs amap key=====");
      amapLBSRequest();
    } catch (IOException e) {
      logger.error("lbs amap request key failure.", e);
    } catch (Exception ex) {
      logger.error("lbs amap request key failure.", ex);
    } finally {
      logger.info("=====end check lbs amap key=====");
    }


  }

  /**
   * 百度Key
   *
   * @throws IOException
   */
  private void baiduLBSRequest() throws IOException {

    final ConcurrentHashMap<String, Boolean> map = KeyCacheUtils.getAllKey(KeyCacheUtils.LBSKEY.BAIDU);

    if (null != map) {

      for (Map.Entry<String, Boolean> entry : map.entrySet()) {

        if (!entry.getValue().booleanValue()) { // 表示Key当天无效

          String url = String.format(KeyCacheUtils.DEFAUT_LBS_BAIDU_URL, entry.getKey(), ADDRESS, CITY);

          String json = getResponseData(url);

          if (null != json) {

            JSONObject jsonObject = JSON.parseObject(json);

            Integer status = jsonObject.getInteger("status");

            if (null != status && 0 == status.intValue()) { // 表示key有效了

              KeyCacheUtils.setInValidKey(KeyCacheUtils.LBSKEY.BAIDU, entry.getKey(), Boolean.TRUE);

              try {
                Thread.sleep(3000);
              } catch (InterruptedException e) {
                //
              }
            }
          }
        }
      }
    }
  }

  /**
   * 高德Key
   *
   * @throws IOException
   */
  private void amapLBSRequest() throws IOException {

    final ConcurrentHashMap<String, Boolean> map = KeyCacheUtils.getAllKey(KeyCacheUtils.LBSKEY.AMAP);

    if (null != map) {

      for (Map.Entry<String, Boolean> entry : map.entrySet()) {

        if (!entry.getValue().booleanValue()) { // 表示Key当天无效

          String url = String.format(KeyCacheUtils.DEFAUT_LBS_AMAP_URL, entry.getKey(), ADDRESS, CITY);

          String json = getResponseData(url);

          if (null != json) {

            JSONObject jsonObject = JSON.parseObject(json);

            String infocode = jsonObject.getString("infocode");

            if (StringUtils.isNotEmpty(infocode) && "10000".equalsIgnoreCase(infocode)) {
              KeyCacheUtils.setInValidKey(KeyCacheUtils.LBSKEY.AMAP, entry.getKey(), Boolean.TRUE);
              try {
                Thread.sleep(3000);
              } catch (InterruptedException e) {
                //
              }
            }
          }
        }
      }
    }
  }

  /**
   * 获取请求数据
   *
   * @param url url
   * @return 返回json数据
   * @throws IOException
   */
  private String getResponseData(String url) throws IOException {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpGet httpGet = new HttpGet(url);
    CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
    if (SUCCESS_CODE == httpResponse.getStatusLine().getStatusCode())
      return EntityUtils.toString(httpResponse.getEntity());
    if (logger.isDebugEnabled()) {
      logger.debug("lbs geo key request failure.url:{} response:{}", url, httpResponse.toString());
    }
    return null;
  }
}
