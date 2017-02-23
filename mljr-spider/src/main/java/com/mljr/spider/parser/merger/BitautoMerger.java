package com.mljr.spider.parser.merger;

import com.csvreader.CsvReader;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mljr.spider.parser.AbstractXpathParser;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by songchi on 17/1/21. 易车网合并例子
 */
public class BitautoMerger {
  public final static String CSV_FILE_PATH = "/Users/songchi/Desktop/美利金融存档/车型库/bitauto-csv-file";
  public static List<File> allFiles = Lists.newArrayList();

  // 递归取所有csv文件
  public static void getFileList(String directory) {
    File f = new File(directory);
    File[] files = f.listFiles();
    for (int i = 0; i < files.length; i++) {
      if (files[i].isFile()) {
        if (files[i].getName().endsWith(".csv")) {
          allFiles.add(files[i]);
        }
      } else {
        getFileList(files[i].getAbsolutePath());
      }
    }
  }

  // 取所有header的并集
  public static List<String> getAllHearders() throws Exception {
    List<String> allHeaders = Lists.newArrayList();
    for (File file : allFiles) {
      CsvReader reader = new CsvReader(new FileInputStream(file), Charset.forName("GBK"));
      String[] headers = {};
      if (reader.readHeaders()) {
        headers = reader.getHeaders();
      }

      for (int i = 0; i < headers.length; i++) {
        String header = headers[i];
        if (!allHeaders.contains(header)) {
          allHeaders.add(header);
        }
      }
      reader.close();
    }
    return allHeaders;
  }

  // 把二维表装换为List<Map>结构
  public static List<Map<String, String>> getListMaps() throws Exception {
    List<Map<String, String>> listMaps = Lists.newArrayList();
    for (File file : allFiles) {
      CsvReader reader = new CsvReader(new FileInputStream(file), Charset.forName("GBK"));
      String[] headers = {};
      if (reader.readHeaders()) {
        headers = reader.getHeaders();
        List<String> list = Arrays.asList(headers);
        List<String> newList = Lists.newArrayList();
        for (String s : list) {
          if (!StringUtils.isBlank(s)) {
            newList.add(s);
          }
        }
        headers = newList.toArray(new String[newList.size()]);
      }
      while (reader.readRecord()) {
        String[] values = reader.getValues();
        if (headers.length != values.length) {
          System.out.println("headers:values " + headers.length + " " + values.length);
          throw new RuntimeException("headers和values长度不一样,请处理");
        }
        Map<String, String> map = Maps.newHashMap();
        for (int i = 0; i < headers.length; i++) {
          if (i > values.length - 1) {
            map.put(headers[i], "");
          } else {
            map.put(headers[i], values[i]);
          }

        }
        listMaps.add(map);
      }
      reader.close();
    }
    return listMaps;

  }


  public static void main(String[] args) throws Exception {
    getFileList(CSV_FILE_PATH);
    List<String> allHeaders = getAllHearders();

    List<Map<String, String>> listMaps = getListMaps();

    List<String[]> values = Lists.newArrayList();
    for (Map<String, String> map : listMaps) {
      List<String> list = Lists.newArrayList();
      for (String header : allHeaders) {
        // 清除空行
        if (header.equals("款式") && StringUtils.isBlank(map.get(header))) {
          break;
        }
        list.add(map.getOrDefault(header, ""));
      }
      values.add(list.toArray(new String[list.size()]));
    }
    String[] headArr = allHeaders.toArray(new String[allHeaders.size()]);
    AbstractXpathParser.write("/Users/songchi/Desktop/易车网(全部).csv", headArr, values);
  }
}
