package com.mljr.spider.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ckex created 2016-11-07 14:00:45:045
 * @explain -
 */
public class MljrTestDo implements Serializable {
  private static final long serialVersionUID = 1478498445845L;

  private Integer id;
  private String name;
  private Date gmtModified;
  private Date gmtCreate;

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setGmtModified(Date gmtModified) {
    this.gmtModified = gmtModified;
  }

  public Date getGmtModified() {
    return gmtModified;
  }

  public void setGmtCreate(Date gmtCreate) {
    this.gmtCreate = gmtCreate;
  }

  public Date getGmtCreate() {
    return gmtCreate;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((gmtModified == null) ? 0 : gmtModified.hashCode());
    result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    MljrTestDo other = (MljrTestDo) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (gmtModified == null) {
      if (other.gmtModified != null)
        return false;
    } else if (!gmtModified.equals(other.gmtModified))
      return false;
    if (gmtCreate == null) {
      if (other.gmtCreate != null)
        return false;
    } else if (!gmtCreate.equals(other.gmtCreate))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "MljrTestDo[id=" + id + ", name=" + name + ", gmtModified=" + gmtModified + ", gmtCreate=" + gmtCreate + "]";
  }
}
