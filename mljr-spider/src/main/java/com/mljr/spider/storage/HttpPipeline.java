/**
 * 
 */
package com.mljr.spider.storage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.mljr.spider.http.AsyncHttpClient;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author Ckex zha </br>
 *         2016年11月29日,上午11:16:16
 *
 */
public class HttpPipeline implements Pipeline {

	protected static transient Logger logger = LoggerFactory.getLogger(HttpPipeline.class);

	private static final Counter COUNTER = new Counter();

	private static class Counter {
		private AtomicLong num = new AtomicLong(0);
		private AtomicLong failure = new AtomicLong(0);

		@Override
		public String toString() {
			return "Counter [all=" + num + ", failure=" + failure + ", failure rate=" + failure.get() / num.get() + "]";
		}

	}

	private final String url;
	private final Pipeline standbyPipeline;
	private final AsyncHttpClient httpclient;

	public HttpPipeline(String url, AsyncHttpClient httpclient, Pipeline standbyPipeline) {
		super();
		this.url = url;
		this.httpclient = httpclient;
		this.standbyPipeline = standbyPipeline;
	}

	@Override
	public void process(ResultItems resultItems, Task task) {
		final ResultItems items = resultItems;
		final Task t = task;
		Map<String, Object> result = resultItems.getAll();
		StringBuilder sb = new StringBuilder();
		for (Object obj : result.values()) {
			sb.append(obj);
		}
		String html = sb.toString();
		if (StringUtils.isBlank(html) || html.length() < 10) {
			logger.warn("Invalid result:" + html);
			return;
		}
		final AtomicBoolean flag = new AtomicBoolean(true);
		final Stopwatch watch = Stopwatch.createStarted();
		boolean success = sentContent(html, new FutureCallback<HttpResponse>() {

			@Override
			public void completed(HttpResponse result) {
				try {
					watch.stop();
					int code = result.getStatusLine().getStatusCode();
					String response = EntityUtils.toString(result.getEntity());
					// response 不以 0 开头则为失败
					if (code != 200 || !StringUtils.startsWith(response, "0")) {
						if (flag.compareAndSet(true, false)) {
							COUNTER.failure.incrementAndGet();
							standbyPipeline.process(items, t);
						}
						logger.error("response error code:" + code + ",useTime " + watch.elapsed(TimeUnit.MILLISECONDS)
								+ "," + COUNTER.toString() + " response:" + response);
						return;
					}
					logger.debug("response content:" + EntityUtils.toString(result.getEntity()));
					logger.debug("response code:" + code + ",useTime " + watch.elapsed(TimeUnit.MILLISECONDS) + ","
							+ COUNTER.toString() + " response:" + response);
				} catch (Exception e) {
					if (logger.isDebugEnabled()) {
						e.printStackTrace();
					}
					logger.error(ExceptionUtils.getStackTrace(e));
				} finally {
					try {
						EntityUtils.consume(result.getEntity());
					} catch (IOException e) {
					}
				}
			}

			@Override
			public void failed(Exception ex) {
				logger.debug("useTime " + watch.elapsed(TimeUnit.MILLISECONDS) + "," + COUNTER.toString());
				watch.stop();
				if (flag.compareAndSet(true, false)) {
					COUNTER.failure.incrementAndGet();
					// 记录到文件
					standbyPipeline.process(items, t);
				}
			}

			@Override
			public void cancelled() {
				if (flag.compareAndSet(true, false)) {
					COUNTER.failure.incrementAndGet();
					// 记录到文件
					standbyPipeline.process(items, t);
				}
			}

		});
		if (!success && flag.compareAndSet(true, false)) {
			// 记录到文件
			standbyPipeline.process(items, t);
		}

	}

	private boolean sentContent(String html, FutureCallback<HttpResponse> callback) {
		ContentType contentType = ContentType.create("text/html", Consts.UTF_8);
		HttpPost post = new HttpPost(url);
		post.addHeader("Content-Encoding", "gzip");
		ByteArrayOutputStream originalContent = null;
		ByteArrayOutputStream baos = null;
		GZIPOutputStream gzipOut = null;
		try {
			originalContent = new ByteArrayOutputStream();
			originalContent.write(html.getBytes(Charset.forName("UTF-8")));
			baos = new ByteArrayOutputStream();
			gzipOut = new GZIPOutputStream(baos);
			originalContent.writeTo(gzipOut);
			gzipOut.finish();
			post.setEntity(new ByteArrayEntity(baos.toByteArray(), contentType));

			// post.setEntity(EntityBuilder.create().setContentEncoding("UTF-8").setText(html).setContentType(contentType)
			// .gzipCompress().build());
			COUNTER.num.incrementAndGet();
			httpclient.post(post, callback, 3000);
			return true;
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace();
			}
			logger.error(ExceptionUtils.getStackTrace(e));
			return false;
		} finally {
			try {
				if (gzipOut != null) {
					gzipOut.close();
				}
			} catch (IOException e) {
			}
			try {
				if (baos != null) {
					baos.close();
				}
			} catch (IOException e) {
			}
			try {
				if (originalContent != null) {
					originalContent.close();
				}
			} catch (IOException e) {
			}
		}
	}

}
