package com.mljr.spider.parser;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.selector.Html;

import java.io.File;
import java.util.List;

/**
 * Created by songchi on 17/1/17.
 */
public class KachezhijiaParser extends AbstractXpathParser {
  public final static String HTML_FILE_PATH = "/Users/songchi/Desktop/360che-html-file/";
  public static final String CSV_FILE_PATH_PREFIX = "/Users/songchi/Desktop/360che-csv-file/";

  public String getFullName(ParsePage page) {
    Html html = page.getHtml();
    List<String> navs = html.xpath("//*[@id=\"mybody\"]//div[4]//div[1]//div[2]//div//a//text()").all();

    String brand = "";
    String fileName = "";

    if (navs.size() == 5) {
      brand = navs.get(2).replaceAll("<.*>", "").trim() + "/" + navs.get(3).replaceAll("<.*>", "").trim();
      fileName = navs.get(4).replaceAll("<.*>", "").trim();
    } else {
      throw new RuntimeException("the nav size is " + navs.size());
    }
    String fullName = brand + "/" + fileName;

    return CSV_FILE_PATH_PREFIX + fullName + ".csv";
  }

  @Override
  public String[] getHeaders(ParsePage page) {
    Html html = page.getHtml();
    List<String> headers = Lists.newArrayList("款式", "厂商指导价");
    List<String> srcHeaders = html.xpath("//*[@id=\"mybody\"]//div[4]//div[4]//table//tbody//tr//td[1]//tidyText()").all();
    for (String header : srcHeaders) {
      headers.add(header.trim().substring(0, header.length() - 1));
    }
    return headers.toArray(new String[headers.size()]);
  }

  @Override
  public List<String[]> getContent(ParsePage page) {
    Html html = page.getHtml();
    List<String[]> rows = Lists.newArrayList();
    String pattern1 = "//*[@id=\"mybody\"]//div[4]//div[4]//table//thead//tr[1]//th[%d]//div//h5//a//text()";

    String pattern2 = "//*[@id=\"mybody\"]//div[4]//div[4]//table//thead//tr[2]//td[%d]//text()";

    String pattern3 = "//*[@id=\"mybody\"]//div[4]//div[4]//table//tbody//tr//td[%d]//div//text()";

    int carNum = html.xpath("//*[@id=\"mybody\"]//div[4]//div[4]//table//tbody//tr[2]//td").all().size();

    for (int i = 2; i <= carNum; i++) {

      List<String> srcData = Lists.newArrayList();
      String carModel = html.xpath(String.format(pattern1, i)).get();
      String price = html.xpath(String.format(pattern2, i)).get();

      srcData.add(carModel);
      srcData.add(price);
      srcData.addAll(html.xpath(String.format(pattern3, i)).all());

      List<String> destData = Lists.newArrayList();
      for (String src : srcData) {
        if (src.contains("参数纠错")) {
          continue;
        }
        if (src.contains("<")) {
          src = src.substring(0, src.indexOf("<"));
        }
        destData.add(src);
      }
      String[] arr = new String[destData.size()];
      rows.add(destData.toArray(arr));
    }
    return rows;
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
    IautosParser parser = new IautosParser();
    int i = 0;
    File[] htmlFiles = parser.getHtmlFiles(HTML_FILE_PATH);
    for (File htmlFile : htmlFiles) {
      ParsePage page = parser.readHtmlFile(htmlFile);
      if (page != null) {
        parser.parseToCsv(page);
        System.out.println(++i);
      }
    }
  }
}
