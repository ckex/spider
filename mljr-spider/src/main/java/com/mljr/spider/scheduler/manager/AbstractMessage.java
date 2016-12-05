/**
 * 
 */
package com.mljr.spider.scheduler.manager;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.mljr.spider.grpc.GrpcClient;

/**
 * @author Ckex zha </br>
 *         2016年11月10日,下午1:42:00
 *
 */
public abstract class AbstractMessage {

	protected transient Logger logger = LoggerFactory.getLogger(getClass());

	// GPS
	protected static final String GPS_RPC_QUEUE_ID = "gps";
	protected static final String GPS_QUEUE_ID = "umq-luj3bt";
	protected static final String GPS_LOG_NAME = "gps-data";
	// JUHE
	protected static final String JUHE_MOBILE_RPC_QUEUE_ID = "mobile";
	protected static final String JUHE_MOBILE_QUEUE_ID = "umq-pevtvl";
	protected static final String JUHE_MOBILE_LOG_NAME = "juhe-mobile-data";

	// RabbitMQ
	protected static final String RMQ_JUHE_MOBILE_QUEUE_ID = "mobile-juhe";
	protected static final String RMQ_BAIDU_MOBILE_QUEUE_ID = "mobile-baidu";
	protected static final String RMQ_SOGOU_MOBILE_QUEUE_ID="queue-fanout-3";

	public AbstractMessage() {
		super();
	}

	protected static final int CORE_SIZE = 5;
	protected static final int MAX_SIZE = 10;
	private static final String name = "spider-dw";
	private static final AtomicInteger count = new AtomicInteger();

	protected static final ThreadPoolExecutor DEFAULT_THREAD_POOL = new ThreadPoolExecutor(CORE_SIZE, MAX_SIZE, 100,
			TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10), new ThreadFactory() {

				@Override
				public Thread newThread(Runnable r) {
					return new Thread(r, Joiner.on("-").join(name, count.incrementAndGet()));
				}
			}, new ThreadPoolExecutor.CallerRunsPolicy());

	protected static final ThreadPoolExecutor newThreadPool(int coreSize, int maxSize) {
		return new ThreadPoolExecutor(coreSize, maxSize, 100, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(10), new ThreadFactory() {

					@Override
					public Thread newThread(Runnable r) {
						return new Thread(r, Joiner.on("-").join(name, count.incrementAndGet()));
					}
				}, new ThreadPoolExecutor.CallerRunsPolicy());
	}

	public interface PullMsgTask {
		public String pullMsg();

	}

	protected PullMsgTask getPullMsgTask(final String queueId) {
		return new PullMsgTask() {
			final String qid = queueId;

			@Override
			public String pullMsg() {
				String msg = null;
				try {
					msg = GrpcClient.getInstance().pullMsg(qid);
					if (Math.random() * 100 < 1) {
						if (logger.isDebugEnabled()) {
							logger.debug(String.format("qid:%s--->msg:%s", qid, msg));
						}
					}
				} catch (Throwable e) {
					logger.error("GRPC Error, " + ExceptionUtils.getStackTrace(e));
					if (logger.isDebugEnabled()) {
						e.printStackTrace();
					}
				}
				return msg;
			}
		};
	}
	// protected ConsumerMessage getConsumerMessage(final String queueId) {
	//
	// return new ConsumerMessage() {
	//
	// @Override
	// public Message getMessage() {
	// return getUMQMessage(queueId);
	// }
	//
	// @Override
	// public void confirmMsg(Message msg) {
	// aksMessage(queueId, msg.getMsgId());
	// }
	//
	// };
	// }

	// private Message getUMQMessage(String queueId) {
	// Stopwatch watch = Stopwatch.createStarted();
	// Message message = null;
	// try {
	// message =
	// UMQClient.getInstence().getMessage(ServiceAttributes.getRegion(),
	// ServiceAttributes.getRole(),
	// queueId);
	// watch.stop();
	// if (Math.random() * 100 < 1 && logger.isDebugEnabled()) {
	// logger.debug(String.format("[%s] usetime=%s-ms,pull msg =%s", queueId,
	// watch.elapsed(TimeUnit.MILLISECONDS), message));
	// }
	// } catch (Throwable e) {
	// logger.error("[" + queueId + "] pull message Error," +
	// ExceptionUtils.getStackTrace(e));
	// }
	// return message;
	// }

	// private void aksMessage(String queueId, String msgId) {
	// boolean succ = false;
	// try {
	// succ = UMQClient.getInstence().ackMsg(ServiceAttributes.getRegion(),
	// ServiceAttributes.getRole(), queueId,
	// msgId);
	// } catch (Exception e) {
	// logger.error("Ask message Error, " + ExceptionUtils.getStackTrace(e));
	// }
	// if (!succ) {
	// logger.warn("Ask message false," + queueId + " - " + msgId);
	// }
	//
	// }

}
