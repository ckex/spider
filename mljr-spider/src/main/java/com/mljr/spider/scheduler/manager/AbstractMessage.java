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

	protected static final String FILE_PATH = "/data/html";

	// GPS
	protected static final String GPS_RPC_QUEUE_ID = "gps";
	protected static final String GPS_QUEUE_ID = "umq-luj3bt";
	protected static final String GPS_LOG_NAME = "gps-data";

	// JUHE
	protected static final String JUHE_MOBILE_RPC_QUEUE_ID = "mobile";
	protected static final String JUHE_MOBILE_QUEUE_ID = "umq-pevtvl";

	// Log pipeline
	protected static final String JUHE_MOBILE_LOG_NAME = "juhe-mobile-data";


	protected static final String GUABU_BANK_CARD_LOG_NAME="guabu-bc-data";
	protected static final String HCP_BANK_CARD_LOG_NAME="hcp-bc-data";
	protected static final String CHA67_BANK_CARD_LOG_NAME="cha67-bc-data";
	protected static final String YHK388_BANK_CARD_LOG_NAME="yhk388-bc-data";
	protected static final String CHAYHK_BANK_CARD_LOG_NAME="chayhk-bc-data";
	protected static final String LBS_AMAP_GEO_LOG_NAME="lbs-amap-geo-data";
	protected static final String LBS_AMAP_REGEO_LOG_NAME="lbs-amap-regeo-data";
	protected static final String LBS_BAIDU_GEO_LOG_NAME="lbs-baidu-geo-data";
	protected static final String LBS_BAIDU_REGEO_LOG_NAME="lbs-baidu-regeo-data";

	// RabbitMQ
	protected static final String RMQ_JUHE_MOBILE_QUEUE_ID = "mobile-juhe";
	protected static final String RMQ_BAIDU_MOBILE_QUEUE_ID = "mobile-baidu";
	protected static final String RMQ_IP138_MOBILE_QUEUE_ID = "mobile-ip138";
	protected static final String RMQ_HUOCHE114_MOBILE_QUEUE_ID = "mobile-114huoche";
	protected static final String RMQ_GUISHUSHOWJI_MOBILE_QUEUE_ID = "mobile-showji";
	protected static final String RMQ_SOGOU_MOBILE_QUEUE_ID = "mobile-sogou";

	protected static final String RMQ_TIANYANCHA_QUEUE_ID = "tianyancha";

	protected static final String RMQ_GUABU_BANK_CARD_QUEUE_ID="bc-guabu";
	protected static final String RMQ_HCP_BANK_CARD_QUEUE_ID="bc-hcp";
	protected static final String RMQ_CHA67_BANK_CARD_QUEUE_ID="bc-cha67";
	protected static final String RMQ_YHK388_BANK_CARD_QUEUE_ID="bc-yhk388";
	protected static final String RMQ_CHAYHK_BANK_CARD_QUEUE_ID="bc-chayhk";

	protected static final String RMQ_LBS_AMAP_GEO_QUEUE_ID="lbs-amap-geo";
	protected static final String RMQ_LBS_AMAP_REGEO_QUEUE_ID="lbs-amap-regeo";
	protected static final String RMQ_LBS_BAIDU_GEO_QUEUE_ID="lbs-baidu-geo";
	protected static final String RMQ_LBS_BAIDU_REGEO_QUEUE_ID="lbs-baidu-regeo";

	// Downloader error listener
	protected static final String SAIGE_GPS_LISTENER_LOG_NAME = "saige-gps-downloader";
	protected static final String QICHACHA_LISTENER_LOG_NAME = "qichacha-downloader";
	protected static final String JUHE_MOBILE_LISTENER_LOG_NAME = "juhe-mobile-downloader";
	protected static final String BAIDU_MOBILE_LISTENER_LOG_NAME = "baidu-mobile-downloader";
	protected static final String SOGOU_MOBILE_LISTENER_LOG_NAME = "sogou-mobile-downloader";
	protected static final String IP138_MOBILE_LISTENER_LOG_NAME = "ip138-mobile-downloader";
	protected static final String HUOCHE114_MOBILE_LISTENER_LOG_NAME = "huoche114-mobile-downloader";
	protected static final String GUISHU_MOBILE_LISTENER_LOG_NAME = "guishu-mobile-downloader";
	protected static final String TIANYANCHA_LISTENER_LOG_NAME = "tianyancha-downloader";

	protected static final String GUABU_BANK_CARD_LISTENER_LOG_NAME="guabu-bc-downloader";
	protected static final String HUOCHEPIAO_BANK_CARD_LISTENER_LOG_NAME="huochepiao-bc-downloader";
	protected static final String CHA67_BANK_CARD_LISTENER_LOG_NAME="cha67-bc-downloader";
	protected static final String YHK388_BANK_CARD_LISTENER_LOG_NAME="yhk388-bc-downloader";
	protected static final String CHAYHK_BANK_CARD_LISTENER_LOG_NAME="chayhk-bc-downloader";

	protected static final String LBS_AMAP_GEO_LISTENER_LOG_NAME="lbs-amap-geo-downloader";
	protected static final String LBS_AMAP_REGEO_LISTENER_LOG_NAME="lbs-amap-regeo-downloader";
	protected static final String LBS_BAIDU_GEO_LISTENER_LOG_NAME="lbs-baidu-geo-downloader";
	protected static final String LBS_BAIDU_REGEO_LISTENER_LOG_NAME="lbs-baidu-regeo-downloader";

	public AbstractMessage() {
		super();
	}

	protected static final int CORE_SIZE = 5;
	protected static final int MAX_SIZE = 5;
	private static final String name = "spider-dw";
	private static final AtomicInteger count = new AtomicInteger();

	protected static final ThreadPoolExecutor DEFAULT_THREAD_POOL = new ThreadPoolExecutor(CORE_SIZE, MAX_SIZE, 100,
			TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10), new ThreadFactory() {

				@Override
				public Thread newThread(Runnable r) {
					return new Thread(r, Joiner.on("-").join(name, count.incrementAndGet()));
				}
			}, new ThreadPoolExecutor.CallerRunsPolicy());

	protected static final ThreadPoolExecutor newThreadPool(int coreSize, int maxSize, final String tname) {
		return new ThreadPoolExecutor(coreSize, maxSize, 100, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(10), new ThreadFactory() {

					@Override
					public Thread newThread(Runnable r) {
						return new Thread(r, Joiner.on("-").join(name, tname, count.incrementAndGet()));
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
