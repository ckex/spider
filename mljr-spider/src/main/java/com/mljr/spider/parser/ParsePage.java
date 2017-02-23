package com.mljr.spider.parser;

import us.codecraft.webmagic.selector.Html;

/**
 * Created by songchi on 17/1/12.
 */
public class ParsePage {
  private String url;
  private Html html;
  private String localFilePath;

  public String getLocalFilePath() {
    return localFilePath;
  }

  public void setLocalFilePath(String localFilePath) {
    this.localFilePath = localFilePath;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Html getHtml() {
    return html;
  }

  public void setHtml(Html html) {
    this.html = html;
  }

  @Override
  public String toString() {
    return "ParsePage{" + "url='" + url + '\'' + ", html=" + html + ", localFilePath='" + localFilePath + '\'' + '}';
  }


  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    ParsePage parsePage = (ParsePage) o;

    if (!url.equals(parsePage.url))
      return false;
    if (!html.equals(parsePage.html))
      return false;
    return localFilePath.equals(parsePage.localFilePath);

  }

  @Override
  public int hashCode() {
    int result = url.hashCode();
    result = 31 * result + html.hashCode();
    result = 31 * result + localFilePath.hashCode();
    return result;
  }
}
