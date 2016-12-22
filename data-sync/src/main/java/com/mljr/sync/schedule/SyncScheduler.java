/**
 * 
 */
package com.mljr.sync.schedule;

import com.mljr.sync.task.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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

	@Scheduled(cron = "0/10 * * * * ?")
	private void startMobileTask() {
		MobileTask task = abstractTaskFactory.createMobileTask();
		task.setName("mobile");
		// task.run();
		logger.debug(task.toString());
	}

	@Scheduled(cron = "10 0 0/1 * * ?")
	private void startGPSTask() {
		GPSTask task = abstractTaskFactory.createGPSTask();
		task.setName("gps");
		task.run();
		logger.debug(task.toString());
	}

	@Scheduled(cron = "20/5 * * * * ?")
	private void startBankCardLocationTask() {
		BankCardLocationTask task = abstractTaskFactory.createBankCardLocationTask();
		task.setName("bankCard");
		task.run();
		logger.debug(task.toString());
	}

	@Scheduled(cron = "30/5 * * * * ?")
	private void startMerchantInfoTask() {
		MerchantInfoTask task = abstractTaskFactory.createMerchantInfoTask();
		task.setName("merchat");
		task.run();
		logger.debug(task.toString());
	}

	@Scheduled(cron = "40/5 * * * * ?")
	private void startLbsTask() {
		LbsTask task = abstractTaskFactory.createLbsTask();
		task.setName("lbs");
		task.run();
		logger.debug(task.toString());
	}

	//10小时执行一次
	@Scheduled(cron = "10 0 0/10 * * ?")
	private void startGxSkyTask() {
		GxSkyTask task = abstractTaskFactory.createGxSkyTask();
		task.run();
		logger.debug(task.toString());
	}

}
