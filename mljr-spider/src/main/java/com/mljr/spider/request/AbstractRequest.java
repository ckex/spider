/**
 * 
 */
package com.mljr.spider.request;

import us.codecraft.webmagic.Request;

/**
 * @author Ckex zha </br>
 *         2016年11月7日,下午9:13:26
 *
 */
public abstract class AbstractRequest extends Request {

  private static final long serialVersionUID = -3710386893157330970L;

  public static final String POST = "POST";

  public static final String GET = "GET";

  public AbstractRequest() {
    super();
  }

  public AbstractRequest(String url) {
    super(url);
  }

}
