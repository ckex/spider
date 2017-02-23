package com.mljr.spider.parser;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.selector.Html;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by songchi on 17/1/12.
 */
public class Auto163Parser extends AbstractXpathParser {
  public final static String HTML_FILE_PATH = "/data/html/auto163";
  public final static String CSV_FILE_PATH_PREFIX = "/Users/songchi/Desktop/auto163-csv-file/";

  public String getFullName(ParsePage page) {
    Html html = page.getHtml();
    String first = html.css("body > div.header_breadcrumb > div > ul > li:nth-child(7) > a", "text").get();
    String second = html.xpath("//a[@class='last']//text()").get();
    if (StringUtils.isBlank(first)) {
      first = html.css("#nav_channel > div.current-location > a:nth-child(3)", "text").get();
    }
    if (StringUtils.isBlank(second)) {
      second = html.css("#nav_channel > div.current-location > a:nth-child(4)", "text").get();
    }

    if (StringUtils.isBlank(first)) {
      System.out.println("first  nav is null " + page.getUrl());
      System.out.println("first  local file is " + page.getLocalFilePath());
      return null;
    }

    if (StringUtils.isBlank(second)) {
      System.out.println("second is null " + page.getUrl());
      System.out.println("second  local file is " + page.getLocalFilePath());
      return null;
    }

    return CSV_FILE_PATH_PREFIX + first + "/" + second + ".csv";
  }

  // *[@id="car_config_param_names"]/div[2]/div/span
  // *[@id="car_config_param_names"]/div[21]/div/span

  @Override
  public String[] getHeaders(ParsePage page) {
    Html html = page.getHtml();
    List<String> myHerders = Lists.newArrayList("款式名称", "指导价");
    List<String> srcHeaders = html.xpath("//*[@id=\"car_config_param_names\"]//div//div//span//text()").all();
    if (CollectionUtils.isNotEmpty(srcHeaders)) {
      myHerders.addAll(srcHeaders);
      return myHerders.toArray(new String[myHerders.size()]);
    }
    return null;
  }

  // *[@id="car_config_param_list"]/div[2]/div[1]/span
  // *[@id="car_config_param_list"]/div[15]/div[1]/span

  @Override
  public List<String[]> getContent(ParsePage page) {
    Html html = page.getHtml();
    List<String[]> allList = Lists.newArrayList();


    int size = getSize(html);
    for (int i = 1; i <= size; i++) {
      String carXpath = "//*[@id=\"car_config_param_head\"]//div//div//div[%d]//div//h3//a//text()";
      String priceXpath = "//*[@id=\"car_config_param_head\"]//div//div//div[%d]//div//div[1]//text()";
      String carName = html.xpath(String.format(carXpath, i)).get();
      if (StringUtils.isBlank(carName)) {
        return null;
      }
      String price = html.xpath(String.format(priceXpath, i)).get();
      if (price != null) {
        price = price.replace(" 指导价：", "").trim();
      }
      List<String> tmpList = Lists.newArrayList(carName, price);
      tmpList.addAll(html.xpath(String.format("//*[@id=\"car_config_param_list\"]//div//div[%d]//span//text()", i)).all());
      if (CollectionUtils.isNotEmpty(tmpList)) {
        allList.add(tmpList.toArray(new String[tmpList.size()]));
      }
    }

    return allList;
  }

  public int getSize(Html html) {
    int i = 0;
    Matcher matcher = Pattern.compile("询底价").matcher(html.toString());
    while (matcher.find()) {
      ++i;
    }
    return i;
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
    Auto163Parser parser = new Auto163Parser();

    File[] htmlFiles = parser.getHtmlFiles(HTML_FILE_PATH);
    int i = 0;
    for (File htmlFile : htmlFiles) {
      ParsePage page = parser.readHtmlFile(htmlFile);
      if (page != null) {
        parser.parseToCsv(page);
        System.out.println(++i);
      }
    }
  }
}
