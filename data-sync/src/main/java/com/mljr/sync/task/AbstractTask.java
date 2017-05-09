/**
 *
 */
package com.mljr.sync.task;

import com.google.common.base.Stopwatch;
import com.mljr.sync.common.email.EmailSender;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Ckex zha </br>
 *         2016年11月28日,下午12:01:55
 */
@Component
public abstract class AbstractTask implements Runnable {

    protected transient Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EmailSender emailSender;

    public AbstractTask() {
        super();
    }

    abstract void execute();

    @Override
    public synchronized void run() {
        try {
            Stopwatch watch = Stopwatch.createStarted();
            execute();
            watch.stop();
            logger.info("execute task, use time:" + watch.elapsed(TimeUnit.MILLISECONDS));
        } catch (Exception ex) {
            logger.error("execute task error. " + ExceptionUtils.getStackTrace(ex));
            if (ex instanceof com.rabbitmq.client.AlreadyClosedException) {
                emailSender.send(null, new String[]{"bee@mljr.com"}, "rabbitMq 连接异常", ExceptionUtils.getStackTrace(ex));
            }
        } finally {
            // finishedTask();
        }
    }

}
