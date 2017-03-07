package com.mljr.operators.entity.dto.chinaunicom;

import java.io.Serializable;

/**
 * @author gaoxi
 * @time 2017/3/6
 */
public class HttpRespDTO<T> implements Serializable {

  private static final long serialVersionUID = 1746231958114388640L;

  private boolean succ;

  private T body;

  public boolean isSucc() {
    return succ;
  }

  public HttpRespDTO setSucc(boolean succ) {
    this.succ = succ;
    return this;
  }

  public T getBody() {
    return body;
  }

  public HttpRespDTO setBody(T body) {
    this.body = body;
    return this;
  }

  public static HttpRespDTO me() {
    return new HttpRespDTO();
  }



}
