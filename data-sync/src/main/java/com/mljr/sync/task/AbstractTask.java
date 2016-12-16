/**
 * 
 */
package com.mljr.sync.task;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.sql.dialect.oracle.ast.clause.ModelClause.ReturnRowsClause;
import com.google.common.base.Stopwatch;

/**
 * @author Ckex zha </br>
 *         2016年11月28日,下午12:01:55
 *
 */
public abstract class AbstractTask implements Runnable {

	protected static transient Logger logger = LoggerFactory.getLogger(AbstractTask.class);

	private static final Map<String, AtomicReference<Status>> RUN_TASK = new Hashtable<>();

	private enum Status {
		runing, finished
	}

	private String name;

	public AbstractTask() {
		super();
	}

	abstract void execute();

	public synchronized void setName(String name) {
		this.name = name;
		RUN_TASK.put(name, new AtomicReference<>(Status.finished));
	}

	@Override
	public void run() {
		try {
			boolean isNext = checkRunning();
			if (!isNext) {
				logger.warn(name + ", Task is running... ");
				return;
			}
			Stopwatch watch = Stopwatch.createStarted();
			execute();
			watch.stop();
			logger.info("execute task, use time:" + watch.elapsed(TimeUnit.MILLISECONDS));
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("execute task error. " + ExceptionUtils.getStackTrace(ex));
		} finally {
			finishedTask();
		}
	}

	private synchronized void finishedTask() {
		if (StringUtils.isBlank(name)) {
			return;
		}
		RUN_TASK.get(name).set(Status.finished);
	}

	private synchronized boolean checkRunning() {
		if (StringUtils.isBlank(name)) {
			return true;
		}
		return RUN_TASK.get(name).compareAndSet(Status.finished, Status.runing);
	}

}
