package com.mljr.spider.vo;

import java.io.Serializable;

/**
 * Created by xi.gao Date:2016/12/12
 */
public class JSONTransferVO implements Serializable {

  private static final long serialVersionUID = 418764149286362047L;

  private String url;

  private Object context;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Object getContext() {
    return context;
  }

  public void setContext(Object context) {
    this.context = context;
  }
}
