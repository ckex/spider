package com.mljr.monitor;

import com.mljr.monitor.task.ServersMonitorTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MonitorMain {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(MonitorMain.class, args);
    ServersMonitorTask task = new ServersMonitorTask();
    task.start();
  }

}
