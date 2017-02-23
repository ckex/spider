/**
 * 
 */
package com.mljr.spider.request;

/**
 * @author Ckex zha </br>
 *         2016年11月7日,下午6:18:44
 *
 */
public class RestfulReqeust extends AbstractRequest {

  private static final long serialVersionUID = 1L;

  private String params;

  public RestfulReqeust() {
    super();
  }

  public RestfulReqeust(String url) {
    super(url);
  }

  public RestfulReqeust(String url, String params) {
    super(url);
    this.params = params;
  }

  public String getParams() {
    return params;
  }

  @Override
  public String toString() {
    return "RestfulReqeust [params=" + params + "]" + super.toString();
  }

}
