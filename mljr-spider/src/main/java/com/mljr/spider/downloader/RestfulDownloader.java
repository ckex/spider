/**
 * 
 */
package com.mljr.spider.downloader;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.utils.HttpConstant;

import com.mljr.spider.request.RestfulReqeust;

/**
 * @author Ckex zha </br> 2016年11月7日,下午6:15:37
 *
 */
public class RestfulDownloader extends HttpClientDownloader {

  protected Logger logger = LoggerFactory.getLogger(getClass());

  public RestfulDownloader() {
    super();
  }

  @Override
  protected RequestBuilder selectRequestMethod(Request request) {
    if (request instanceof RestfulReqeust) {
      return selectRestfulRequest((RestfulReqeust) request);
    } else {
      return super.selectRequestMethod(request);
    }
  }

  private RequestBuilder selectRestfulRequest(RestfulReqeust request) {
    String method = request.getMethod();
    if (method.equalsIgnoreCase(HttpConstant.Method.POST)) {
      RequestBuilder requestBuilder = RequestBuilder.post();
      HttpEntity entry = new StringEntity(request.getParams(), ContentType.APPLICATION_JSON);
      requestBuilder.setEntity(entry);
      return requestBuilder;
    } else {
      return super.selectRequestMethod(request);
    }
  }

}
