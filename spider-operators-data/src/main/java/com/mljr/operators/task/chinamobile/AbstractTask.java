package com.mljr.operators.task.chinamobile;

import com.google.common.base.Stopwatch;
import com.mljr.operators.entity.chinamobile.DatePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by songchi on 17/2/24.
 */
public abstract class AbstractTask implements Runnable {
    protected static final Logger logger = LoggerFactory.getLogger(CallInfoTask.class);
    public Long userInfoId;

    public Map<String, String> cookies;

    public void setParams(Long userInfoId, Map<String, String> cookies) {
        this.userInfoId = userInfoId;
        this.cookies = cookies;
    }

    abstract void doWork(DatePair pair) throws Exception;

    @Override
    public void run() {
        for (DatePair pair : DatePair.getLatestDatePair(6)) {
            try {
                Stopwatch watch = Stopwatch.createStarted();
                doWork(pair);
                watch.stop();
                logger.info("execute task, use time:" + watch.elapsed(TimeUnit.MILLISECONDS));
            } catch (Exception e) {
                logger.error("doWork error", e);
            }
        }

    }
}
