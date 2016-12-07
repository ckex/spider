/**
 * 
 */
package com.mljr.sync.schedule;

import com.mljr.sync.task.GPSTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mljr.sync.task.AbstractTaskFactory;
import com.mljr.sync.task.MobileTask;

/**
 * @author Ckex zha </br>
 *         2016年11月28日,下午1:20:02
 *
 */
@Service
public class SyncScheduler {

	protected static transient Logger logger = LoggerFactory.getLogger(SyncScheduler.class);

	@Autowired
	private AbstractTaskFactory abstractTaskFactory;

	@Scheduled(cron = "0/5 * * * * ?")
	private void startMobileTask() {
		MobileTask task = abstractTaskFactory.createMobileTask();
		task.run();
		logger.debug(task.toString());
	}

	@Scheduled(cron = "0 0 0/1 * * ?")
	private void startGPSTask() {
		GPSTask task = abstractTaskFactory.createGPSTask();
		task.run();
		logger.debug(task.toString());
	}

}
