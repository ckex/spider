package com.mljr.operators.task.chinamobile;

import com.google.common.base.Stopwatch;
import com.mljr.operators.common.constant.RequestInfoEnum;
import com.mljr.operators.common.utils.CookieUtils;
import com.mljr.operators.entity.model.operators.RequestInfo;
import com.mljr.operators.service.ChinaMobileParseService;
import com.mljr.operators.service.ChinaMobileService;
import com.mljr.operators.service.primary.operators.IBillInfoService;
import com.mljr.operators.service.primary.operators.IRequestInfoService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by songchi on 17/2/23.
 */
public class HisBillInfoTask implements Runnable {
  protected static final Logger logger = LoggerFactory.getLogger(CurrCallInfoTask.class);

  private ChinaMobileService chinaMobileService;

  private IBillInfoService billInfoService;

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
    this.billInfoService = context.getBean(IBillInfoService.class);
    this.requestInfoService = context.getBean(IRequestInfoService.class);

  }

  @Override
  public void run() {
    Stopwatch stopwatch = Stopwatch.createStarted();
    try {
      // 写历史数据
      String queryTime = DateFormatUtils.format(requestInfo.getStartDate(), "yyyy年MM月");
      Map<String, String> cMap = CookieUtils.stringToMap(cookies);
      String historyData = chinaMobileService.getHistoryBillInfo(cMap, queryTime);
      writeHistory(historyData, queryTime);
      requestInfoService.updateStatusBySign(requestInfo.getSign(), RequestInfoEnum.SUCCESS,
          RequestInfoEnum.INIT);

    } catch (Exception e) {
      logger.error("CurrBillInfoTask error", e);
      requestInfoService.updateStatusBySign(requestInfo.getSign(), RequestInfoEnum.ERROR,
          RequestInfoEnum.INIT);
    }
    logger.info("{} chinamobile history bill run use time {}", Thread.currentThread().getName(),
        stopwatch.elapsed(TimeUnit.MILLISECONDS));
  }

  public void writeHistory(String historyData, String queryTime) {
    try {
      billInfoService.insertByBatch(userInfoId, ChinaMobileParseService.parseHisBillInfo(historyData,queryTime,userInfoId));

    } catch (Exception e) {
      logger.error("CurrBillInfoTask write history data error", e);
      e.printStackTrace();
    }
  }

}
