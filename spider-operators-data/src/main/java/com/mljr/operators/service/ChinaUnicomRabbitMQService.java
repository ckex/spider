package com.mljr.operators.service;

import com.alibaba.fastjson.JSON;
import com.mljr.operators.common.constant.MQConstant;
import com.mljr.operators.common.utils.RabbitMQUtil;
import com.mljr.operators.entity.model.operators.RequestInfo;
import com.mljr.operators.task.chinaunicom.ChinaUnicomTask;
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
 * @time 2017/3/3
 */
public class ChinaUnicomRabbitMQService {

  private static final Logger logger = LoggerFactory.getLogger(ChinaUnicomRabbitMQService.class);

  private static final AtomicInteger count = new AtomicInteger();

  private static final String[] queues = new String[] {MQConstant.OPERATOR_MQ_CHINAUNICOM_QUEUE,
      MQConstant.OPERATOR_MQ_CHINAMOBILE_QUEUE};

  private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 100,
      TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
          return new Thread(r, "chinaunicom-operators-task" + count.incrementAndGet());
        }
      }, new ThreadPoolExecutor.CallerRunsPolicy());

  private ApplicationContext context;

  private Channel channel;

  public ChinaUnicomRabbitMQService(ApplicationContext context)
      throws IOException, InterruptedException {
    this.context = context;
    channel = RabbitmqClient.newChannel();
  }

  public void run() {
    String message = RabbitMQUtil.pollMessage(channel, MQConstant.OPERATOR_MQ_CHINAUNICOM_QUEUE);
    if (StringUtils.isBlank(message)) {
      try {
        TimeUnit.SECONDS.sleep(5);
      } catch (InterruptedException e) {
      }
      return;
    }
    RequestInfo entity = JSON.parseObject(message, RequestInfo.class);
    executor.execute(new ChinaUnicomTask(context, entity));
  }


}
