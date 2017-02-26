package com.mljr.spider.model;

import java.io.Serializable;

public class SrcDmPhonePriceDo implements Serializable {
  private static final long serialVersionUID = 1486457285467L;

  private Long id;
  private String type;
  private String brand;
  private String title;
  private String jdId;
  private String itemUrl;

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getBrand() {
    return brand;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void setJdId(String jdId) {
    this.jdId = jdId;
  }

  public String getJdId() {
    return jdId;
  }

  public void setItemUrl(String itemUrl) {
    this.itemUrl = itemUrl;
  }

  public String getItemUrl() {
    return itemUrl;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    result = prime * result + ((brand == null) ? 0 : brand.hashCode());
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    result = prime * result + ((jdId == null) ? 0 : jdId.hashCode());
    result = prime * result + ((itemUrl == null) ? 0 : itemUrl.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    SrcDmPhonePriceDo other = (SrcDmPhonePriceDo) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (type == null) {
      if (other.type != null)
        return false;
    } else if (!type.equals(other.type))
      return false;
    if (brand == null) {
      if (other.brand != null)
        return false;
    } else if (!brand.equals(other.brand))
      return false;
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
      return false;
    if (jdId == null) {
      if (other.jdId != null)
        return false;
    } else if (!jdId.equals(other.jdId))
      return false;
    if (itemUrl == null) {
      if (other.itemUrl != null)
        return false;
    } else if (!itemUrl.equals(other.itemUrl))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "SrcDmPhonePriceDo[id=" + id + ", type=" + type + ", brand=" + brand + ", title=" + title + ", jdId=" + jdId + ", itemUrl=" + itemUrl
        + "]";
  }
}
