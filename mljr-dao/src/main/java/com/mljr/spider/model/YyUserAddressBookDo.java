package com.mljr.spider.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ckex created 2016-11-29 16:14:48:048
 * @explain -
 */
public class YyUserAddressBookDo implements Serializable {
  private static final long serialVersionUID = 1480407288606L;

  private Long id;
  private Long userId;
  private String name;
  private String company;
  private String number;
  private String homeAddress;
  private String email;
  private String relationship;
  private String nickName;
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

  public void setCompany(String company) {
    this.company = company;
  }

  public String getCompany() {
    return company;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getNumber() {
    return number;
  }

  public void setHomeAddress(String homeAddress) {
    this.homeAddress = homeAddress;
  }

  public String getHomeAddress() {
    return homeAddress;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setRelationship(String relationship) {
    this.relationship = relationship;
  }

  public String getRelationship() {
    return relationship;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getNickName() {
    return nickName;
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
    result = prime * result + ((company == null) ? 0 : company.hashCode());
    result = prime * result + ((number == null) ? 0 : number.hashCode());
    result = prime * result + ((homeAddress == null) ? 0 : homeAddress.hashCode());
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((relationship == null) ? 0 : relationship.hashCode());
    result = prime * result + ((nickName == null) ? 0 : nickName.hashCode());
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
    YyUserAddressBookDo other = (YyUserAddressBookDo) obj;
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
    if (company == null) {
      if (other.company != null)
        return false;
    } else if (!company.equals(other.company))
      return false;
    if (number == null) {
      if (other.number != null)
        return false;
    } else if (!number.equals(other.number))
      return false;
    if (homeAddress == null) {
      if (other.homeAddress != null)
        return false;
    } else if (!homeAddress.equals(other.homeAddress))
      return false;
    if (email == null) {
      if (other.email != null)
        return false;
    } else if (!email.equals(other.email))
      return false;
    if (relationship == null) {
      if (other.relationship != null)
        return false;
    } else if (!relationship.equals(other.relationship))
      return false;
    if (nickName == null) {
      if (other.nickName != null)
        return false;
    } else if (!nickName.equals(other.nickName))
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
    return "YyUserAddressBookDo[id=" + id + ", userId=" + userId + ", name=" + name + ", company=" + company + ", number=" + number + ", homeAddress="
        + homeAddress + ", email=" + email + ", relationship=" + relationship + ", nickName=" + nickName + ", createTime=" + createTime
        + ", updateTime=" + updateTime + "]";
  }
}
