package com.mljr.operators.service;

import com.alibaba.fastjson.JSON;
import com.mljr.operators.common.constant.MQConstant;
import com.mljr.operators.entity.model.operators.RequestInfo;
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

  public RabbitMQService(ApplicationContext context) throws IOException, InterruptedException {
    this.context = context;
    channel = RabbitmqClient.newChannel();
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
      executor.execute(new ChinaUnicomTask(context, entity));
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
