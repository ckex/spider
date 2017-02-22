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

    @Autowired
    private AbstractTask jdTask;

    @Autowired
    private AbstractTask jdConsumeTask;

    @Autowired
    private AbstractTask autohomeFlagTask;

    @Scheduled(cron = "0/2 * * * * ?")
    private void startMobileTask() {
        mobileTask.run();
        logger.debug(mobileTask.toString());
    }

    @Scheduled(cron = "10 0/1 * * * ?")
    private void startGPSTask() {
        gpsTask.run();
        logger.debug(gpsTask.toString());
    }

    @Scheduled(cron = "20/5 * * * * ?")
    private void startBankCardLocationTask() {
        bankCardLocationTask.run();
        logger.debug(bankCardLocationTask.toString());
    }

    @Scheduled(cron = "30/5 * * * * ?")
    private void startMerchantInfoTask() {
        merchantInfoTask.run();
        logger.debug(merchantInfoTask.toString());
    }

    @Scheduled(cron = "40/5 * * * * ?")
    private void startLbsTask() {
        lbsTask.run();
        logger.debug(lbsTask.toString());
    }

    //10小时执行一次
    @Scheduled(cron = "10 0 0/10 * * ?")
    private void startGxSkyTask() {
        gxSkyTask.run();
        logger.debug(gxSkyTask.toString());
    }

    @Scheduled(cron = "0/10 * * * * ?")
    private void startQqNumberTask() {
        qqNumberTask.run();
        logger.debug(qqNumberTask.toString());
    }

    @Scheduled(cron = "0 0 2 * * ?")
    private void startJdTask() {
        jdTask.run();
        logger.debug(jdTask.toString());
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    private void startJdConsumeTask() {
        jdConsumeTask.run();
        logger.debug(jdConsumeTask.toString());
    }

    @Scheduled(cron = "0 0 0 1/7 * ?")
    private void startAutohomeFlagTask() {
        autohomeFlagTask.run();
        logger.debug(autohomeFlagTask.toString());
    }

}
