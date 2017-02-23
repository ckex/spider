package com.mljr.spider.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ckex created 2016-11-29 16:14:49:049
 * @explain -
 */
public class YyUserCallRecordDo implements Serializable {
  private static final long serialVersionUID = 1480407289103L;

  private Long id;
  private Long userId;
  private String name;
  private String number;
  private String type;
  private Date callTime;
  private String duration;
  private Date createTime;
  private Date updateTime;

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getNumber() {
    return number;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public void setCallTime(Date callTime) {
    this.callTime = callTime;
  }

  public Date getCallTime() {
    return callTime;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public String getDuration() {
    return duration;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((userId == null) ? 0 : userId.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((number == null) ? 0 : number.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    result = prime * result + ((callTime == null) ? 0 : callTime.hashCode());
    result = prime * result + ((duration == null) ? 0 : duration.hashCode());
    result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
    result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    YyUserCallRecordDo other = (YyUserCallRecordDo) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (userId == null) {
      if (other.userId != null)
        return false;
    } else if (!userId.equals(other.userId))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (number == null) {
      if (other.number != null)
        return false;
    } else if (!number.equals(other.number))
      return false;
    if (type == null) {
      if (other.type != null)
        return false;
    } else if (!type.equals(other.type))
      return false;
    if (callTime == null) {
      if (other.callTime != null)
        return false;
    } else if (!callTime.equals(other.callTime))
      return false;
    if (duration == null) {
      if (other.duration != null)
        return false;
    } else if (!duration.equals(other.duration))
      return false;
    if (createTime == null) {
      if (other.createTime != null)
        return false;
    } else if (!createTime.equals(other.createTime))
      return false;
    if (updateTime == null) {
      if (other.updateTime != null)
        return false;
    } else if (!updateTime.equals(other.updateTime))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "YyUserCallRecordDo[id=" + id + ", userId=" + userId + ", name=" + name + ", number=" + number + ", type=" + type + ", callTime="
        + callTime + ", duration=" + duration + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
  }
}
