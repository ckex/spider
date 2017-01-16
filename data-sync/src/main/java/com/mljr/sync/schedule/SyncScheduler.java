/**
 *
 */
package com.mljr.sync.schedule;

import com.mljr.sync.task.AbstractTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author Ckex zha </br>
 *         2016年11月28日,下午1:20:02
 */
@Service
public class SyncScheduler {

    protected static transient Logger logger = LoggerFactory.getLogger(SyncScheduler.class);

//	@Autowired
//	private AbstractTaskFactory abstractTaskFactory;

    @Autowired
    private AbstractTask mobileTask;

    @Autowired
    private AbstractTask gpsTask;

    @Autowired
    private AbstractTask bankCardLocationTask;

    @Autowired
    private AbstractTask merchantInfoTask;

    @Autowired
    private AbstractTask lbsTask;

    @Autowired
    private AbstractTask gxSkyTask;

    @Autowired
    private AbstractTask qqNumberTask;

//    @Scheduled(cron = "0/2 * * * * ?")
    private void startMobileTask() {
//		MobileTask task = abstractTaskFactory.createMobileTask();
//		task.setName("mobile");
        mobileTask.run();
        logger.debug(mobileTask.toString());
    }

    //	@Scheduled(cron = "10 0 0/1 * * ?")
    private void startGPSTask() {
//		GPSTask task = abstractTaskFactory.createGPSTask();
//		task.setName("gps");
        gpsTask.run();
        logger.debug(gpsTask.toString());
    }

    //	@Scheduled(cron = "20/5 * * * * ?")
    private void startBankCardLocationTask() {
//		BankCardLocationTask task = abstractTaskFactory.createBankCardLocationTask();
//		task.setName("bankCard");
        bankCardLocationTask.run();
        logger.debug(bankCardLocationTask.toString());
    }

    //	@Scheduled(cron = "30/5 * * * * ?")
    private void startMerchantInfoTask() {
//		MerchantInfoTask task = abstractTaskFactory.createMerchantInfoTask();
//		task.setName("merchat");
        merchantInfoTask.run();
        logger.debug(merchantInfoTask.toString());
    }

    //	@Scheduled(cron = "40/5 * * * * ?")
    private void startLbsTask() {
//		LbsTask task = abstractTaskFactory.createLbsTask();
//		task.setName("lbs");
        lbsTask.run();
        logger.debug(lbsTask.toString());
    }

    //10小时执行一次
//	@Scheduled(cron = "10 0 0/10 * * ?")
    private void startGxSkyTask() {
//		GxSkyTask task = abstractTaskFactory.createGxSkyTask();
        gxSkyTask.run();
        logger.debug(gxSkyTask.toString());
    }

    @Scheduled(cron = "0/10 * * * * ?")
    private void startQqNumberTask() {
//		MobileTask task = abstractTaskFactory.createMobileTask();
//		task.setName("mobile");
        qqNumberTask.run();
        logger.debug(qqNumberTask.toString());
	}

}
