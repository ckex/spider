package com.mljr.spider.parser;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.selector.Html;

import java.io.File;
import java.util.List;

/**
 * Created by songchi on 17/1/12.
 */
public class AutoQqParser extends AbstractXpathParser {
  public final static String HTML_FILE_PATH = "/data/html/auto.qq.com";
  public final static String CSV_FILE_PATH_PREFIX = "/Users/songchi/Desktop/autoqq-csv-file/";

  // *[@id="header"]/div[2]/a[2]
  // *[@id="header"]/div[2]/a[3]
  public String getFullName(ParsePage page) {
    Html html = page.getHtml();
    String nav = html.xpath("//*[@id=\"header\"]//div[2]//tidyText()").get();
    String name = nav.replaceAll("<.*>", "").replace("当前位置：", "").replace("车型大全", "").replace("\n", "").replace(">", "").trim().replace("  ", " ")
        .replace(" ", "/");
    if (Splitter.on("/").splitToList(name).size() == 4) {
      name = name.substring(name.indexOf("/") + 1);
    }

    return CSV_FILE_PATH_PREFIX + name + ".csv";
  }

  @Override
  public String[] getHeaders(ParsePage page) {
    Html html = page.getHtml();
    List<String> srcHeaders = html.xpath("//*[@id=\"config_name\"]//li[@class!='bar']//tidyText()").all();
    List<String> headers = Lists.newArrayList("款式名称");
    for (String srcHeader : srcHeaders) {
      headers.add(srcHeader.replaceAll("<.*>", "").replace("\n", "").replace("*", "").trim());
    }
    return headers.toArray(new String[headers.size()]);
  }

  @Override
  public List<String[]> getContent(ParsePage page) {
    Html html = page.getHtml();
    List<String[]> allList = Lists.newArrayList();

    int size = getSize(html);
    if (size == 0) {
      return null;
    }
    // *[@id="model_name_0"]/a[1]
    for (int i = 0; i < size; i++) {
      String carXpath = "//*[@id='model_name_%d']/a[1]//text()";
      String carName = html.xpath(String.format(carXpath, i)).get();
      List<String> tmpList = Lists.newArrayList(carName);
      tmpList.addAll(html.xpath(String.format("//*[@id='car_%d']/ul/li[@class!='bar']//text()", i)).all());
      allList.add(tmpList.toArray(new String[tmpList.size()]));
    }

    return allList;
  }

  public int getSize(Html html) {
    return html.xpath("//*[@id=\"middle\"]//div[2]//ul//li").all().size();
  }

  @Override
  public void parseToCsv(ParsePage page) {
    String fullName = this.getFullName(page);
    String[] headers = this.getHeaders(page);
    List<String[]> content = this.getContent(page);
    if (StringUtils.isNotBlank(fullName) && headers != null && headers.length > 0 && CollectionUtils.isNotEmpty(content)) {
      this.write(fullName, headers, content);
    }

  }

  public static void main(String[] args) {
    AutoQqParser parser = new AutoQqParser();

    File[] htmlFiles = parser.getHtmlFiles(HTML_FILE_PATH);
    int i = 0;
    for (File htmlFile : htmlFiles) {
      ParsePage page = parser.readHtmlFile(htmlFile);
      if (page != null) {
        parser.parseToCsv(page);
        System.out.println(++i);
        if (i == 10) {
          // break;
        }
      }
    }
  }


}
