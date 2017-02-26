package com.mljr.gps.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.mljr.common.ServiceConfig;
import com.mljr.gps.sender.HttpSender;
import entity.GpsData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by songchi on 17/01/18.
 */
@Service
public class GpsResendService {

  protected static transient Logger logger = LoggerFactory.getLogger(GpsResendService.class);

  public final static String GPS_FILE_DIR = ServiceConfig.getGpsFileDir();

  public final static String GPS_HISTORY_FILE_DIR = ServiceConfig.getGpsHistoryFileDir();

  public String[] listDirs() {
    return new File(GPS_FILE_DIR).list(new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return name.matches("\\d{4}\\-\\d{2}\\-\\d{2}");
      }
    });
  }

  public String[] listFilesByDir(String subDir) {
    return new File(GPS_FILE_DIR + "/" + subDir).list(new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return name.endsWith(".json") || name.endsWith(".log");
      }
    });
  }

  public String[] listHistoryFiles() {
    return new File(GPS_HISTORY_FILE_DIR).list(new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return name.endsWith(".json") || name.endsWith(".log");
      }
    });
  }

  public static Gson gson = new Gson();

  Pattern pattern = Pattern.compile("\\d{4}\\-\\d{2}\\-\\d{2}\\-\\d{2}");

  public Map<String, Object> parseJsonFile(File file) {
    Map<String, Object> returnMap = Maps.newHashMap();
    List<String> tmpList = Lists.newArrayList();
    try {
      String jsonStr = FileUtils.readFileToString(file, "utf-8");
      if (!jsonStr.startsWith("{")) {
        jsonStr = jsonStr.substring(jsonStr.indexOf("{"));
      }
      List<String> list = new JsonPathSelector("$.datas[*].list[*]").selectList(jsonStr);
      List<GpsData> datas = Lists.newArrayList();
      for (String str : list) {
        datas.add(JSONObject.parseObject(str, GpsData.class));
      }

      List<List<GpsData>> partLists = Lists.partition(datas, 5000);
      Matcher matcher = pattern.matcher(file.getName());
      matcher.find();
      String currentTime = matcher.group();
      for (List<GpsData> gpsDatas : partLists) {
        Map<String, Object> map = new HashMap<>();
        map.put("currentTime", currentTime);
        map.put("datas", gpsDatas);
        String json = JSONObject.toJSONString(map);
        tmpList.add(json);
      }
      returnMap.put("dataList", tmpList);
      returnMap.put("count", list.size());
      return returnMap;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return returnMap;
  }

  public Map<String, Object> resend(String fullName) {
    File file = new File(fullName);
    Map<String, Object> dataMap = parseJsonFile(file);
    List<String> dataList = (List<String>) dataMap.get("dataList");
    Integer count = (Integer) dataMap.get("count");
    HttpSender sender = new HttpSender();
    if (CollectionUtils.isNotEmpty(dataList)) {
      for (String s : dataList) {
        sender.sendData(s);
      }
    }
    Map<String, Object> map = new HashMap<>();
    map.put("msg", "success");
    map.put("fileName", file.getName());
    map.put("all", count);
    map.put("requests", HttpSender.COUNTER.getNum());
    map.put("failure", HttpSender.COUNTER.getFailure());
    HttpSender.COUNTER.clear();
    return map;

  }

}
