package com.mljr.gps.sender;

import com.google.common.base.Stopwatch;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.GZIPOutputStream;

/**
 * @author Ckex zha </br>
 *         2016年11月29日,上午11:16:16
 */
public class HttpSender {

  protected static transient Logger logger = LoggerFactory.getLogger(HttpSender.class);

  public static final Counter COUNTER = new Counter();

  public static class Counter {
    private AtomicLong num = new AtomicLong(0);
    private AtomicLong failure = new AtomicLong(0);

    public void clear() {
      num = new AtomicLong(0);
      failure = new AtomicLong(0);
    }

    public AtomicLong getNum() {
      return num;
    }

    public AtomicLong getFailure() {
      return failure;
    }

    @Override
    public String toString() {
      if (num.get() == 0) {
        return "num is 0";
      }
      return "Counter [all=" + num + ", failure=" + failure + ", failure rate=" + failure.get() / num.get() + "]";
    }

  }

  private AsyncHttpClient httpclient;

  public HttpSender() {
    super();
    try {
      if (this.httpclient == null) {
        this.httpclient = new AsyncHttpClient();
      }
    } catch (IOReactorException e) {
      e.printStackTrace();
    }
  }

  public void sendData(String jsonStr) {
    if (StringUtils.isBlank(jsonStr) || jsonStr.length() < 5) {
      logger.warn("Invalid result:" + jsonStr);
      return;
    }

    byte[] body = jsonStr.getBytes(Charset.forName("UTF-8"));
    HttpPost post = buildPost(body);

    final AtomicBoolean flag = new AtomicBoolean(true);
    final Stopwatch watch = Stopwatch.createStarted();

    if (post == null && flag.compareAndSet(true, false)) {
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
            }
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
        watch.stop();
        if (isWrite) {
          COUNTER.failure.incrementAndGet();
        }
      }

      @Override
      public void cancelled() {
        boolean isWrite = flag.compareAndSet(true, false);
        if (isWrite) {
          COUNTER.failure.incrementAndGet();
        }
      }

    });

  }

  private void sentContent(HttpPost post, FutureCallback<HttpResponse> callback) {
    COUNTER.num.incrementAndGet();
    httpclient.post(post, callback, 3000);
  }

  public static final String GPS_URL = "http://117.121.103.178:8088/mljr_pub_api/json/gps/new";

  private HttpPost buildPost(byte[] body) {
    ContentType contentType = ContentType.create("text/html", Consts.UTF_8);
    HttpPost post = new HttpPost(GPS_URL);
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
