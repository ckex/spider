package com.mljr.gps.controller;

import com.google.common.collect.Lists;
import com.mljr.gps.service.GpsResendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by songchi on 17/01/18.
 */
@RestController
public class GpsResendController {

  @Autowired
  private GpsResendService gpsResendService;

  @RequestMapping("/")
  public ModelAndView index() {
    return new ModelAndView("index");
  }

  @RequestMapping("/dirs")
  public ModelAndView listDirs() {
    ModelAndView mav = new ModelAndView("dirs");
    List<String> list = Arrays.asList(gpsResendService.listDirs());
    Collections.sort(list);
    List<List<String>> dataList = Lists.partition(list, 3);
    mav.addObject("dataList", dataList);
    return mav;
  }

  @RequestMapping("/files/{subDir}")
  public ModelAndView listFiles(@PathVariable("subDir") String subDir) {

    ModelAndView mav = new ModelAndView("files");
    List<String> list = Arrays.asList(gpsResendService.listFilesByDir(subDir));
    Collections.sort(list);
    List<List<String>> dataList = Lists.partition(list, 3);
    mav.addObject("dataList", dataList);
    mav.addObject("subDir", subDir);
    return mav;
  }

  //
  @RequestMapping("/history")
  public ModelAndView listHistoryFiles() {
    ModelAndView mav = new ModelAndView("historyFiles");
    List<String> list = Arrays.asList(gpsResendService.listHistoryFiles());
    Collections.sort(list);
    List<List<String>> dataList = Lists.partition(list, 3);
    mav.addObject("dataList", dataList);
    return mav;
  }

  @RequestMapping(value = "/resend/{subDir}/{fileName}")
  public Map<String, Object> resend(@PathVariable("subDir") String subDir, @PathVariable("fileName") String fileName) {
    String fullName = GpsResendService.GPS_FILE_DIR + "/" + subDir + "/" + fileName + ".json";
    return gpsResendService.resend(fullName);
  }

  @RequestMapping(value = "/resendHistory/{fileName}")
  @ResponseBody
  public Map<String, Object> resendHistory(@PathVariable("fileName") String fileName) {
    String fullName = GpsResendService.GPS_HISTORY_FILE_DIR + "/" + fileName + ".log";
    return gpsResendService.resend(fullName);
  }


}
