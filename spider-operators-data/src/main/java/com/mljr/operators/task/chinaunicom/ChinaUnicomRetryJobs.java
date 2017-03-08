package com.mljr.operators.task.chinaunicom;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.mljr.operators.common.constant.MQConstant;
import com.mljr.operators.common.constant.OperatorsEnum;
import com.mljr.operators.common.constant.RequestInfoEnum;
import com.mljr.operators.common.utils.RabbitMQUtil;
import com.mljr.operators.entity.model.operators.RequestInfo;
import com.mljr.operators.service.primary.operators.IRequestInfoService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author gaoxi
 * @time 2017/3/7
 */
@Component
public class ChinaUnicomRetryJobs {

  public final static long ONE_Minute = 60 * 1000;

  private static final Logger LOGGER = LoggerFactory.getLogger(ChinaUnicomRetryJobs.class);

  @Autowired
  private IRequestInfoService requestInfoService;

  // 任务执行完成后 5分钟后执行
  @Scheduled(fixedDelay = 5 * ONE_Minute)
  public void retry() {
    Stopwatch stopwatch = Stopwatch.createStarted();
    try {
//      List<RequestInfo> retryList =
//          requestInfoService.retry(OperatorsEnum.CHINAUNICOM, RequestInfoEnum.ERROR);
//      if (null != retryList && retryList.size() > 0) {
//        List<Long> ids = Lists.newArrayList();
//        retryList.forEach(requestInfo -> ids.add(requestInfo.getId()));
//
//        boolean flag = requestInfoService.batchUpdateStatusById(RequestInfoEnum.INIT,
//            RequestInfoEnum.ERROR, ids);
//
//        if (flag) {
//          RabbitMQUtil.sendMessage(MQConstant.OPERATOR_MQ_EXCHANGE,
//              MQConstant.OPERATOR_MQ_CHINAUNICOM_ROUTING_KEY, retryList);
//        }
//      }
    } catch (Exception e) {
      LOGGER.error("chinaunicom retry jobs exception.msg:{}", ExceptionUtils.getStackTrace(e));
    }
    LOGGER.info("chinaunicom retry jobs end...time:{}", stopwatch.elapsed(TimeUnit.MILLISECONDS));

  }
}
