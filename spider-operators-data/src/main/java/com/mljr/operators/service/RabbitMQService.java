package com.mljr.operators.service;

import com.alibaba.fastjson.JSON;
import com.mljr.operators.common.constant.MQConstant;
import com.mljr.operators.common.constant.OperatorsUrlEnum;
import com.mljr.operators.entity.model.operators.RequestInfo;
import com.mljr.operators.entity.model.operators.UserInfo;
import com.mljr.operators.service.primary.operators.IUserInfoService;
import com.mljr.operators.task.chinamobile.*;
import com.mljr.operators.task.chinaunicom.ChinaUnicomTask;
import com.mljr.rabbitmq.RabbitmqClient;
import com.mljr.utils.RandomUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
 * @time 2017/3/3
 */
public class RabbitMQService {

  private static final Logger logger = LoggerFactory.getLogger(RabbitMQService.class);

  private static final AtomicInteger count = new AtomicInteger();

  private static final String[] queues = new String[] {MQConstant.OPERATOR_MQ_CHINAUNICOM_QUEUE,
      MQConstant.OPERATOR_MQ_CHINAMOBILE_QUEUE};

  private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 100,
      TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
          return new Thread(r, "spider-operators-task" + count.incrementAndGet());
        }
      }, new ThreadPoolExecutor.CallerRunsPolicy());

  private ApplicationContext context;

  private Channel channel;

  private IUserInfoService userInfoService;

  private ApiService apiService;

  private CurrFlowInfoTask currFlowInfoTask;

  private CurrBillInfoTask currBillInfoTask;

  private CurrCallInfoTask currCallInfoTask;

  private CurrSMSInfoTask currSMSInfoTask;

  private HisBillInfoTask hisBillInfoTask;

  private HisSMSInfoTask hisSMSInfoTask;

  private HisCallInfoTask hisCallInfoTask;

  private HisFlowInfoTask hisFlowInfoTask;

  private PackageInfoTask packageInfoTask;

  public RabbitMQService(ApplicationContext context) throws IOException, InterruptedException {
    this.context = context;
    channel = RabbitmqClient.newChannel();
    userInfoService = context.getBean(IUserInfoService.class);
    apiService = context.getBean(ApiService.class);
    currFlowInfoTask = context.getBean(CurrFlowInfoTask.class);
    currBillInfoTask = context.getBean(CurrBillInfoTask.class);
    currCallInfoTask = context.getBean(CurrCallInfoTask.class);
    currSMSInfoTask = context.getBean(CurrSMSInfoTask.class);
    hisBillInfoTask = context.getBean(HisBillInfoTask.class);
    hisSMSInfoTask = context.getBean(HisSMSInfoTask.class);
    hisCallInfoTask = context.getBean(HisCallInfoTask.class);
    hisFlowInfoTask = context.getBean(HisFlowInfoTask.class);
    packageInfoTask = context.getBean(PackageInfoTask.class);

  }

  public void run() {
    for (String queue : queues) {
      String message = pollMessage(queue);
      if (StringUtils.isBlank(message)) {
        try {
          TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
        }
        continue;
      }
      RequestInfo entity = JSON.parseObject(message, RequestInfo.class);
      if (queue.equals(MQConstant.OPERATOR_MQ_CHINAUNICOM_QUEUE)) {
        executor.execute(new ChinaUnicomTask(context, entity));
      } else if (queue.equals(MQConstant.OPERATOR_MQ_CHINAMOBILE_QUEUE)) {
        String cookies = apiService.findCookiesByCellphone(entity.getMobile());
        if (StringUtils.isNotBlank(cookies)) {
          UserInfo userInfo =
              userInfoService.selectUniqUser(entity.getMobile(), entity.getIdcard());
          OperatorsUrlEnum enums = OperatorsUrlEnum.indexOf(entity.getUrlType());
          switch (enums) {
            case CHINA_MOBILE_PACKAGE_INFO:
              packageInfoTask.setParams(userInfo.getId(), cookies, entity);
              executor.execute(packageInfoTask);
              break;
            case CHINA_MOBILE_CURRENT_BILL:
              currBillInfoTask.setParams(userInfo.getId(), cookies, entity);
              executor.execute(currBillInfoTask);
              break;
            case CHINA_MOBILE_CURRENT_CALL:
              currCallInfoTask.setParams(userInfo.getId(), cookies, entity);
              executor.execute(currCallInfoTask);
              break;
            case CHINA_MOBILE_CURRENT_FLOW:
              currFlowInfoTask.setParams(userInfo.getId(), cookies, entity);
              executor.execute(currFlowInfoTask);
              break;
            case CHINA_MOBILE_CURRENT_SMS:
              currSMSInfoTask.setParams(userInfo.getId(), cookies, entity);
              executor.execute(currSMSInfoTask);
              break;
            case CHINA_MOBILE_HISTORY_BILL:
              hisBillInfoTask.setParams(userInfo.getId(), cookies, entity);
              executor.execute(hisBillInfoTask);
              break;
            case CHINA_MOBILE_HISTORY_CALL:
              hisCallInfoTask.setParams(userInfo.getId(), cookies, entity);
              executor.execute(hisCallInfoTask);
              break;
            case CHINA_MOBILE_HISTORY_FLOW:
              hisFlowInfoTask.setParams(userInfo.getId(), cookies, entity);
              executor.execute(hisFlowInfoTask);
              break;
            case CHINA_MOBILE_HISTORY_SMS:
              hisSMSInfoTask.setParams(userInfo.getId(), cookies, entity);
              executor.execute(hisSMSInfoTask);
              break;
          }
        }
      }

    }
  }

  public String pollMessage(String queueId) {
    boolean autoAck = true;
    try {
      GetResponse response = RabbitmqClient.pollMessage(channel, queueId, autoAck);
      if (response == null) {
        if (RandomUtils.randomPrint(100)) {
          logger.debug("qid=" + queueId + "queue is empty.waitting message");
        }
        return null;
      }
      if (!autoAck) {
        channel.basicAck(response.getEnvelope().getDeliveryTag(), false);
      }
      return new String(response.getBody(), "UTF-8");
    } catch (Exception e) {
      if (logger.isDebugEnabled()) {
        e.printStackTrace();
      }
      logger.error("Push task error. qid:" + queueId + ", " + ExceptionUtils.getStackTrace(e));
    }
    return null;
  }
}
