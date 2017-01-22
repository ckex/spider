/**
 * 
 */
package com.mljr.gps.sender;

import com.google.common.base.Stopwatch;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.ManagedNHttpClientConnectionFactory;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.nio.conn.ManagedNHttpClientConnection;
import org.apache.http.nio.conn.NHttpClientConnectionManager;
import org.apache.http.nio.conn.NHttpConnectionFactory;
import org.apache.http.nio.protocol.BasicAsyncResponseConsumer;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.CodingErrorAction;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ckex zha </br>
 *         2016年11月29日,上午9:58:15
 *
 */
public class AsyncHttpClient {

	protected static transient Logger logger = LoggerFactory.getLogger(AsyncHttpClient.class);

	private static final AtomicInteger COUNT = new AtomicInteger(0);

	private class HttpThreadFactory implements ThreadFactory {

		private final String prefix;
		private final boolean daemon;

		private HttpThreadFactory(String prefix, boolean daemon) {
			super();
			this.prefix = prefix;
			this.daemon = daemon;
			logger.info("Instance iax thread factory,prefix=" + prefix + ",daemon=" + daemon);
		}

		@Override
		public Thread newThread(Runnable r) {
			String tname = prefix + "-" + Math.abs(COUNT.incrementAndGet());
			final Thread thread = new Thread(r, tname);
			thread.setDaemon(daemon);
			return thread;
		}

	}

	public RequestConfig defaultReqConfig;
	private ConnectingIOReactor ioreactor;
	private PoolingNHttpClientConnectionManager connManager;
	private CloseableHttpAsyncClient httpclient;
	private ScheduledThreadPoolExecutor evictor;

	public AsyncHttpClient() throws IOReactorException {

		int connectTimeout = 1000 * 60;
		int socketTimeout = 1500;
		int selectInterval = 50;
		boolean tcpNoDelay = true;

		IOReactorConfig ioReactorConfig = IOReactorConfig.custom().setConnectTimeout(connectTimeout)
				.setSoKeepAlive(true).setSoTimeout(socketTimeout).setSelectInterval(selectInterval)
				.setTcpNoDelay(tcpNoDelay).build();
		if (logger.isInfoEnabled()) {
			logger.info("ioReactorConfig" + ioReactorConfig.toString());
		}

		ioreactor = new DefaultConnectingIOReactor(ioReactorConfig, new HttpThreadFactory("IO-Reactor", true));
		NHttpConnectionFactory<ManagedNHttpClientConnection> connFactory = new ManagedNHttpClientConnectionFactory();

		connManager = new PoolingNHttpClientConnectionManager(ioreactor, connFactory);

		ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE)
				.setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8).setMessageConstraints(
						MessageConstraints.custom().setMaxHeaderCount(5000).setMaxLineLength(500000).build())
				.build();

		connManager.setDefaultConnectionConfig(connectionConfig);
		connManager.setDefaultMaxPerRoute(10);
		connManager.setMaxTotal(10);

		defaultReqConfig = RequestConfig.custom().setConnectionRequestTimeout(1000 * 30)
				.setConnectTimeout(connectTimeout).setExpectContinueEnabled(false)
				.setCookieSpec(CookieSpecs.IGNORE_COOKIES).setSocketTimeout(socketTimeout).build();
		if (logger.isInfoEnabled()) {
			logger.info("defaultReqConfig=" + defaultReqConfig.toString());
		}
		httpclient = HttpAsyncClients.custom().setConnectionManager(connManager)
				.setDefaultRequestConfig(defaultReqConfig).setUserAgent("mljr-spider")
				.setThreadFactory(new HttpThreadFactory("HTTP-CLIENT", true)).build();
		httpclient.start();
		evictor = new ScheduledThreadPoolExecutor(1, new HttpThreadFactory("Idle-Connection", true));
		startEvictor();
	}

	public void post(HttpPost post, FutureCallback<HttpResponse> callback, int timeout) {
		// post = new HttpPost("url");
		// post.setConfig(RequestConfig.copy(defaultReqConfig).build());
		// post.addHeader("Content-Encoding", "gzip");
		// post.setEntity(new StringEntity("entry",
		// ContentType.create("text/html", Consts.UTF_8)));
		BasicAsyncResponseConsumer consumer = new BasicAsyncResponseConsumer();
		Future<HttpResponse> result = httpclient.execute(HttpAsyncMethods.create(post), consumer, callback);
		try {
			result.get(timeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace();
			}
			logger.error(post.getURI() + " " + ExceptionUtils.getStackTrace(e));
		}
	}

	private void startEvictor() {
		evictor.scheduleWithFixedDelay(new IdleConnectionEvictor(connManager), 1, 1, TimeUnit.MINUTES);
	}

	private class IdleConnectionEvictor implements Runnable {
		private final NHttpClientConnectionManager connMgr;

		public IdleConnectionEvictor(NHttpClientConnectionManager connMgr) {
			super();
			this.connMgr = connMgr;
		}

		@Override
		public void run() {
			if (!evictor.isTerminating()) {
				Stopwatch watch = Stopwatch.createStarted();
				// Close expired connections
				connMgr.closeExpiredConnections();
				// Optionally, close connections
				// that have been idle longer than 5 sec
				// connMgr.closeIdleConnections(2, TimeUnit.MINUTES);
				logger.info("close idle connections,use time=" + watch.elapsed(TimeUnit.MILLISECONDS));
			}
		}
	}

	public void destory() {
		logger.info("###destory###");
		if (httpclient != null) {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(ExceptionUtils.getStackTrace(e));
			}
		}
		evictor.shutdownNow();
	}
}
