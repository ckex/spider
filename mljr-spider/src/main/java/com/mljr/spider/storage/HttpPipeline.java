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

import com.mljr.spider.vo.Result;
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
import org.slf4j.MarkerFactory;

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

    byte[] body = html.getBytes(Charset.forName("UTF-8"));
    HttpPost post = buildPost(body);

    final AtomicBoolean flag = new AtomicBoolean(true);
    final Stopwatch watch = Stopwatch.createStarted();

    if (post == null && flag.compareAndSet(true, false)) {
      standbyPipeline.process(items, t); // 记录到文件
      return;
    }

    final long length = body.length;
    final long gzipLen = post.getEntity().getContentLength();

    sentContent(post, new FutureCallback<HttpResponse>() {

      @Override
      public void completed(HttpResponse result) {
        try {
          watch.stop();
          int code = result.getStatusLine().getStatusCode();
          String response = EntityUtils.toString(result.getEntity());
          if (code != 200 || !Result.isSucc(response)) {
            boolean isWrite = flag.compareAndSet(true, false);
            if (isWrite) {
              COUNTER.failure.incrementAndGet();
              standbyPipeline.process(items, t);
            }
            logger.error(MarkerFactory.getMarker("warn-email"),String.format("HTTP code:%s,isWrite:%s,time:%s,len:%s, gzipLen:%s, %s, %s", code, isWrite,
                watch.elapsed(TimeUnit.MILLISECONDS), length, gzipLen, COUNTER.toString(), response));
            logger.error(String.format("HTTP code:%s,isWrite:%s,time:%s,len:%s, gzipLen:%s, %s, %s", code, isWrite,
                watch.elapsed(TimeUnit.MILLISECONDS), length, gzipLen, COUNTER.toString(), response));
            return;
          }
          if (logger.isDebugEnabled()) {
            logger.debug(String.format("HTTP code:%s,time:%s,len:%s, gzipLen:%s, %s, %s", code, watch.elapsed(TimeUnit.MILLISECONDS), length, gzipLen,
                COUNTER.toString(), response));
          }
        } catch (Exception e) {
          if (logger.isDebugEnabled()) {
            e.printStackTrace();
          }
          logger.error(ExceptionUtils.getStackTrace(e));
          logger.error(MarkerFactory.getMarker("warn-email"),ExceptionUtils.getStackTrace(e));
        } finally {
          try {
            EntityUtils.consume(result.getEntity());
          } catch (IOException e) {
          }
        }
      }

      @Override
      public void failed(Exception ex) {
        boolean isWrite = flag.compareAndSet(true, false);
        logger.debug("useTime=" + watch.elapsed(TimeUnit.MILLISECONDS) + ", isWrite=" + isWrite + ", " + COUNTER.toString());
        logger.error(MarkerFactory.getMarker("warn-email"),"useTime=" + watch.elapsed(TimeUnit.MILLISECONDS) + ", isWrite=" + isWrite + ", " + COUNTER.toString()+">"+ExceptionUtils.getStackTrace(ex));
        watch.stop();
        if (isWrite) {
          COUNTER.failure.incrementAndGet();
          standbyPipeline.process(items, t); // 记录到文件
        }
      }

      @Override
      public void cancelled() {
        boolean isWrite = flag.compareAndSet(true, false);
        if (isWrite) {
          COUNTER.failure.incrementAndGet();
          standbyPipeline.process(items, t); // 记录到文件
        }
      }

    });

  }

  private void sentContent(HttpPost post, FutureCallback<HttpResponse> callback) {
    COUNTER.num.incrementAndGet();
    httpclient.post(post, callback, 1000*50*10);
  }

  private HttpPost buildPost(byte[] body) {
    ContentType contentType = ContentType.create("text/html", Consts.UTF_8);
    HttpPost post = new HttpPost(url);
    post.addHeader("Content-Encoding", "gzip");

    ByteArrayOutputStream originalContent = new ByteArrayOutputStream();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    GZIPOutputStream gzipOut = null;
    try {
      originalContent.write(body);
      gzipOut = new GZIPOutputStream(baos);
      originalContent.writeTo(gzipOut);
      gzipOut.finish();
      post.setEntity(new ByteArrayEntity(baos.toByteArray(), contentType));
      return post;
    } catch (IOException e) {
      if (logger.isDebugEnabled()) {
        e.printStackTrace();
      }
      logger.error("build http post error. " + ExceptionUtils.getStackTrace(e));
      return null; // Exception .
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
