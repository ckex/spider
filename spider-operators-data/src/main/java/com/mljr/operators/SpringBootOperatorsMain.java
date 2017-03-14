package com.mljr.operators;

import com.mljr.operators.service.ChinaMobileMQService;
import com.mljr.operators.service.ChinaUnicomRabbitMQService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties
public class SpringBootOperatorsMain {

  private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootOperatorsMain.class);

  public static void main(String[] args) throws Exception {
    new SpringBootOperatorsMain().startRun(args);
  }

  private void startRun(String... args) {
    ApplicationContext context = SpringApplication.run(SpringBootOperatorsMain.class, args);
    try {
      startTask(context);
    } catch (Exception e) {
      LOGGER.error("load failure.", ExceptionUtils.getStackTrace(e));
      System.exit(-1);
    }
  }

  private void startTask(final ApplicationContext context) throws Exception {
    final ChinaUnicomRabbitMQService chinaUnicomRabbitMQService =
        new ChinaUnicomRabbitMQService(context);
    new Thread(new Runnable() {
      @Override
      public void run() {
        while (true) {
          try {
            chinaUnicomRabbitMQService.run();
          } catch (Exception e) {

          }
        }
      }
    }).start();
    final ChinaMobileMQService chinaMobileMQService = new ChinaMobileMQService(context);
    new Thread(new Runnable() {
      @Override
      public void run() {
        while (true) {
          try {
            chinaMobileMQService.run();
          } catch (Exception e) {

          }
        }
      }
    }).start();
  }
}
