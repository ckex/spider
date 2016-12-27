/**
 * 
 */
package com.mljr.spider.main;

import com.mljr.spider.scheduler.zk.ZkScheduler;
import com.mljr.spider.task.LBSTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mljr.spider.scheduler.manager.Manager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Ckex zha </br>
 *         2016年11月6日,上午12:04:50
 */
public class Main {

    protected static transient Logger logger = LoggerFactory.getLogger(Main.class);

    private static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public Main() {
        super();
    }

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		logger.info("start...");
        startLBSTask();
		Manager manager = new Manager();
		manager.run();
		ZkScheduler zkScheduler =new ZkScheduler();
		zkScheduler.startZkMonitorTask();
		synchronized (Main.class) {
			try {
				Main.class.wait();
			} catch (InterruptedException e) {
			}
		}
	}

    private static void startLBSTask() {
        try {
            //每小时检查一次失效的Key
            executor.scheduleWithFixedDelay(new LBSTask(), 0, 1, TimeUnit.HOURS);
        } catch (Exception e) {
            logger.error("start lbs task failure.", e);
        }
    }

}
