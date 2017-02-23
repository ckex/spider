/**
 * 
 */
package com.mljr.sync.task;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

/**
 * @author Ckex zha </br>
 *         2016年11月28日,下午12:01:55
 *
 */
public abstract class AbstractTask implements Runnable {

  protected transient Logger logger = LoggerFactory.getLogger(getClass());

  // private static final Map<String, AtomicReference<Status>> RUN_TASK = new Hashtable<>();

  // private enum Status {
  // runing, finished
  // }

  // private String name;

  public AbstractTask() {
    super();
  }

  abstract void execute();

  // public synchronized void setName(String name) {
  // this.name = name;
  // RUN_TASK.put(name, new AtomicReference<>(Status.finished));
  // }

  @Override
  public synchronized void run() {
    try {
      // boolean isNext = checkRunning();
      // if (!isNext) {
      // logger.warn(name + ", Task is running... ");
      // return;
      // }
      Stopwatch watch = Stopwatch.createStarted();
      execute();
      watch.stop();
      logger.info("execute task, use time:" + watch.elapsed(TimeUnit.MILLISECONDS));
    } catch (Exception ex) {
      ex.printStackTrace();
      logger.error("execute task error. " + ExceptionUtils.getStackTrace(ex));
    } finally {
      // finishedTask();
    }
  }

  // private synchronized void finishedTask() {
  // if (StringUtils.isBlank(name)) {
  // return;
  // }
  // RUN_TASK.get(name).set(Status.finished);
  // }

  // private synchronized boolean checkRunning() {
  // if (StringUtils.isBlank(name)) {
  // return true;
  // }
  // return RUN_TASK.get(name).compareAndSet(Status.finished, Status.runing);
  // }

}
