package com.mljr.operators.task.chinamobile;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mljr.operators.common.constant.RequestInfoEnum;
import com.mljr.operators.common.utils.CookieUtils;
import com.mljr.operators.entity.chinamobile.DatePair;
import com.mljr.operators.entity.model.operators.FlowInfo;
import com.mljr.operators.entity.model.operators.RequestInfo;
import com.mljr.operators.service.ChinaMobileService;
import com.mljr.operators.service.CommonService;
import com.mljr.operators.service.primary.operators.IFlowInfoService;
import com.mljr.operators.service.primary.operators.IRequestInfoService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by songchi on 17/2/23.
 */
public class HisFlowInfoTask implements Runnable {

  protected static final Logger logger = LoggerFactory.getLogger(HisFlowInfoTask.class);

  private ChinaMobileService chinaMobileService;

  private IFlowInfoService flowInfoService;

  private IRequestInfoService requestInfoService;

  public Long userInfoId;

  public String cookies;

  public RequestInfo requestInfo;


  public void setParams(Long userInfoId, String cookies, RequestInfo requestInfo,
      ApplicationContext context) {
    this.userInfoId = userInfoId;
    this.cookies = cookies;
    this.requestInfo = requestInfo;
    this.chinaMobileService = context.getBean(ChinaMobileService.class);
    this.flowInfoService = context.getBean(IFlowInfoService.class);
    this.requestInfoService = context.getBean(IRequestInfoService.class);
  }

  @Override
  public void run() {
    Stopwatch stopwatch = Stopwatch.createStarted();
    try {
      DatePair pair = new DatePair(DateFormatUtils.format(requestInfo.getStartDate(), "yyyy-MM-dd"),
          DateFormatUtils.format(requestInfo.getEndDate(), "yyyy-MM-dd"));
      Map<String, String> cMap = CookieUtils.stringToMap(cookies);
      String data = chinaMobileService.getHistoryFlowInfo(cMap, pair);
      writeToDb(data, pair);
      requestInfoService.updateStatusBySign(requestInfo.getSign(), RequestInfoEnum.SUCCESS,
          RequestInfoEnum.INIT);
    } catch (Exception e) {
      logger.error("HisFlowInfoTask error", e);
      requestInfoService.updateStatusBySign(requestInfo.getSign(), RequestInfoEnum.ERROR,
          RequestInfoEnum.INIT);
    }
    logger.info("{} chinamobile history flow run use time {}", Thread.currentThread().getName(),
        stopwatch.elapsed(TimeUnit.MILLISECONDS));
  }

  void writeToDb(String data, DatePair pair) throws Exception {
    String flowInfoStr = data.substring(data.indexOf("[["), data.lastIndexOf("]]") + 2);
    List<List<String>> list =
        new Gson().fromJson(flowInfoStr, new TypeToken<List<List<String>>>() {}.getType());
    List<FlowInfo> fiList = Lists.newArrayList();
    for (List<String> subList : list) {

      String startTime = subList.get(1);
      String homeArea = subList.get(2);
      String onlinePattern = subList.get(3); // 上网方式
      String duration = subList.get(4); // 时长
      String totalBytes = subList.get(5); // 流量
      String svcName = subList.get(6);
      String fee = subList.get(7);
      String netType = subList.get(8);

      String year = pair.getStartDate().substring(0, 4);

      FlowInfo fi = new FlowInfo();
      fi.setUserInfoId(userInfoId);
      fi.setForwardType(true);

      fi.setStartTime(DateUtils.parseDate(year + "-" + startTime, "yyyy-MM-dd HH:mm:ss"));
      fi.setHomeArea(homeArea);
      fi.setOnlinePattern(onlinePattern);
      fi.setDuration(CommonService.toSecond(duration)+"");
      fi.setTotalBytes(parseBytes(totalBytes));
      fi.setSvcName(svcName);
      fi.setFee(new BigDecimal(fee));
      fi.setNetType(netType);
      fi.setCreateTime(new Date());
      fi.setUpdateTime(new Date());

      fiList.add(fi);
    }
    flowInfoService.insertByBatch(fiList);
  }

  private BigDecimal parseBytes(String totalBytes) {
    if (totalBytes.contains("GB")) {
      totalBytes = totalBytes.replace("GB", "");
      return new BigDecimal(Integer.parseInt(totalBytes) * 1024 * 1024);
    } else if (totalBytes.contains("MB")) {
      totalBytes = totalBytes.replace("MB", "");
      return new BigDecimal(Integer.parseInt(totalBytes) * 1024);
    } else if (totalBytes.contains("KB")) {
      totalBytes = totalBytes.replace("KB", "");
      return new BigDecimal(Integer.parseInt(totalBytes));
    } else {
      throw new RuntimeException("流量单位处理错误  " + totalBytes);
    }
  }


}
