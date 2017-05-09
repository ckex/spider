package com.mljr.monitor;

import com.mljr.monitor.task.ServersMonitorTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MonitorMain {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(MonitorMain.class, args);
        ServersMonitorTask task = context.getBean(ServersMonitorTask.class);
        task.start();
    }

}
