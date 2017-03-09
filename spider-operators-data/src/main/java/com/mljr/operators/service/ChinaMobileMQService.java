package com.mljr.operators.service;

import com.alibaba.fastjson.JSON;
import com.mljr.operators.common.constant.MQConstant;
import com.mljr.operators.common.constant.OperatorsUrlEnum;
import com.mljr.operators.common.utils.RabbitMQUtil;
import com.mljr.operators.entity.model.operators.RequestInfo;
import com.mljr.operators.entity.model.operators.UserInfo;
import com.mljr.operators.service.primary.operators.IUserInfoService;
import com.mljr.operators.task.chinamobile.*;
import com.mljr.rabbitmq.RabbitmqClient;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gaoxi
 * @time 2017/3/7
 */
public class ChinaMobileMQService {

  private static final Logger logger = LoggerFactory.getLogger(ChinaMobileMQService.class);

  private static final AtomicInteger count = new AtomicInteger();

  private ApplicationContext context;

  private Channel channel;

  private ApiService apiService;

  private IUserInfoService userInfoService;

  private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 100,
      TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
          return new Thread(r, "chinamobile-operators-task" + count.incrementAndGet());
        }
      }, new ThreadPoolExecutor.CallerRunsPolicy());

  public ChinaMobileMQService(ApplicationContext context) throws IOException {
    this.context = context;
    this.channel = RabbitmqClient.newChannel();
    this.apiService = context.getBean(ApiService.class);
    userInfoService = context.getBean(IUserInfoService.class);
  }

  public void run() {
    String message = RabbitMQUtil.pollMessage(channel, MQConstant.OPERATOR_MQ_CHINAMOBILE_QUEUE);
    if (StringUtils.isBlank(message)) {
      try {
        TimeUnit.SECONDS.sleep(5);
      } catch (InterruptedException e) {

      }
      return;
    }
    RequestInfo entity = JSON.parseObject(message, RequestInfo.class);
    String cookies = apiService.findCookiesByCellphone(entity.getMobile());
    if (StringUtils.isNotBlank(cookies)) {
      UserInfo userInfo = userInfoService.selectUniqUser(entity.getMobile(), entity.getIdcard());
      OperatorsUrlEnum enums = OperatorsUrlEnum.indexOf(entity.getUrlType());
      switch (enums) {
        case CHINA_MOBILE_PACKAGE_INFO:
          PackageInfoTask packageInfoTask = new PackageInfoTask();
          packageInfoTask.setParams(userInfo.getId(), cookies, entity, context);
          executor.execute(packageInfoTask);
          break;
        case CHINA_MOBILE_CURRENT_BILL:
          CurrBillInfoTask currBillInfoTask = new CurrBillInfoTask();
          currBillInfoTask.setParams(userInfo.getId(), cookies, entity, context);
          executor.execute(currBillInfoTask);
          break;
        case CHINA_MOBILE_CURRENT_CALL:
          CurrCallInfoTask currCallInfoTask = new CurrCallInfoTask();
          currCallInfoTask.setParams(userInfo.getId(), cookies, entity, context);
          executor.execute(currCallInfoTask);
          break;
        case CHINA_MOBILE_CURRENT_FLOW:
          CurrFlowInfoTask currFlowInfoTask = new CurrFlowInfoTask();
          currFlowInfoTask.setParams(userInfo.getId(), cookies, entity, context);
          executor.execute(currFlowInfoTask);
          break;
        case CHINA_MOBILE_CURRENT_SMS:
          CurrSMSInfoTask currSMSInfoTask = new CurrSMSInfoTask();
          currSMSInfoTask.setParams(userInfo.getId(), cookies, entity, context);
          executor.execute(currSMSInfoTask);
          break;
        case CHINA_MOBILE_HISTORY_BILL:
          HisBillInfoTask hisBillInfoTask = new HisBillInfoTask();
          hisBillInfoTask.setParams(userInfo.getId(), cookies, entity, context);
          executor.execute(hisBillInfoTask);
          break;
        case CHINA_MOBILE_HISTORY_CALL:
          HisCallInfoTask hisCallInfoTask = new HisCallInfoTask();
          hisCallInfoTask.setParams(userInfo.getId(), cookies, entity, context);
          executor.execute(hisCallInfoTask);
          break;
        case CHINA_MOBILE_HISTORY_FLOW:
          HisFlowInfoTask hisFlowInfoTask = new HisFlowInfoTask();
          hisFlowInfoTask.setParams(userInfo.getId(), cookies, entity, context);
          executor.execute(hisFlowInfoTask);
          break;
        case CHINA_MOBILE_HISTORY_SMS:
          HisSMSInfoTask hisSMSInfoTask = new HisSMSInfoTask();
          hisSMSInfoTask.setParams(userInfo.getId(), cookies, entity, context);
          executor.execute(hisSMSInfoTask);
          break;
      }
    }
  }
}