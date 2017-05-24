/**
 * 
 */

package com.mljr.spider.request;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ckex zha <br>
 *         May 24, 2017,10:01:16 AM.
 *
 */
public class ExtReqesut extends AbstractRequest {

  private static final long serialVersionUID = 1L;

  private final Map<String, Object> data = new HashMap<>();

  public ExtReqesut() {
    super();
  }

  /**
   * Constructors.
   * 
   * @param url
   */
  public ExtReqesut(String url) {
    super(url);
  }

  public Map<String, Object> getData() {
    return data;
  }

  public void putData(String key, Object value) {
    this.data.put(key, value);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((data == null) ? 0 : data.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    ExtReqesut other = (ExtReqesut) obj;
    if (data == null) {
      if (other.data != null)
        return false;
    } else if (!data.equals(other.data))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "ExtReqesut [data=" + data + "]"+super.toString();
  }


}
