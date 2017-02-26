package com.mljr.spider.parser;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import us.codecraft.webmagic.selector.Html;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by songchi on 17/1/10.
 */
public class IautosParser extends AbstractXpathParser {

  public final static String HTML_FILE_PATH = "/data/html/iautos.com";
  public final static String CSV_FILE_PATH_PREFIX = "/Users/songchi/Desktop/iautos-csv-file/";

  public String getFullName(ParsePage page) {
    Html html = page.getHtml();
    String nav = html.xpath("//*[@id=\"bread\"]//html()").get();

    nav = nav.replaceAll("<([^>]*)>", "");
    nav = nav.replace("&gt", "");
    nav = nav.replace("\n", "");
    nav = nav.replace(":", "").replace(";;", "");
    nav = nav.replace("您所在的位置：", "").replace("首页", "").replace("车型秘档", "").trim().replace("  ", " ");

    List<String> navList = Splitter.on(" ").omitEmptyStrings().splitToList(nav);

    String str = Joiner.on("/").join(navList);

    return CSV_FILE_PATH_PREFIX + str + ".csv";
  }


  @Override
  public String[] getHeaders(ParsePage page) {
    Html html = page.getHtml();
    List<String> allHeaders = Lists.newArrayList("指导价", "市场价");

    List<String> header1 = html.xpath("//*[@id=\"webpage\"]//div[4]//div[2]//div[3]//div//table//tbody//tr//td[1]//text()").all();

    List<String> header2 = html.xpath("//*[@id=\"webpage\"]//div[4]//div[2]//div[4]//div//table//tbody//tr//td[1]//text()").all();
    List<String> header2s = html.xpath("//*[@id=\"webpage\"]//div[4]//div[2]//div[4]//div//table//tbody//tr//td[3]//text()").all();

    List<String> header3 = html.xpath("//*[@id=\"webpage\"]//div[4]//div[2]//div[5]//div//table//tbody//tr//td[1]//text()").all();
    List<String> header3s = html.xpath("//*[@id=\"webpage\"]//div[4]//div[2]//div[5]//div//table//tbody//tr//td[3]//text()").all();

    List<String> header4 = html.xpath("//*[@id=\"webpage\"]//div[4]//div[2]//div[6]//div//table//tbody//tr//td[1]//text()").all();
    List<String> header4s = html.xpath("//*[@id=\"webpage\"]//div[4]//div[2]//div[6]//div//table//tbody//tr//td[3]//text()").all();

    List<String> header5 = html.xpath("//*[@id=\"webpage\"]//div[4]//div[2]//div[7]//div//table//tbody//tr//td[1]//text()").all();
    List<String> header5s = html.xpath("//*[@id=\"webpage\"]//div[4]//div[2]//div[7]//div//table//tbody//tr//td[3]//text()").all();


    allHeaders.addAll(header1);
    allHeaders.addAll(header2);
    allHeaders.addAll(header2s);
    allHeaders.addAll(header3);
    allHeaders.addAll(header3s);
    allHeaders.addAll(header4);
    allHeaders.addAll(header4s);
    allHeaders.addAll(header5);
    allHeaders.addAll(header5s);

    // 特有属性
    List<String> header6 = html.xpath("[@id=\"webpage\"]//div[4]//div[2]//div[9]//div//table[1]//tbody//tr[@class='gxhpzConBg1']//td[1]").all();
    List<String> header6s = html.xpath("[@id=\"webpage\"]//div[4]//div[2]//div[9]//div//table[1]//tbody//tr[@class='gxhpzConBg1']//td[3]").all();

    if (header6 != null && header6.size() > 0) {
      allHeaders.addAll(header6);
    }

    if (header6s != null && header6s.size() > 0) {
      allHeaders.addAll(header6s);
    }
    for (Iterator<String> iterator = allHeaders.iterator(); iterator.hasNext();) {
      String s = iterator.next();
      if (s != null && s.trim().equals("1")) {
        iterator.remove();
      }
    }

    String[] headers = new String[allHeaders.size()];
    return allHeaders.toArray(headers);
  }

  @Override
  public List<String[]> getContent(ParsePage page) {
    // *[@id="webpage"]/div[4]/div[2]/div[1]/div[2]/div[1]/ul[1]/li[1]/b
    // *[@id="webpage"]/div[4]/div[2]/div[1]/div[2]/div[1]/ul[1]/li[2]/b
    Html html = page.getHtml();

    List<String> allContentList = Lists.newArrayList();
    allContentList.add(html.xpath("//*[@id='webpage']//div[4]//div[2]//div[1]//div[2]//div[1]//ul[1]//li[1]//b//text()").get());
    allContentList.add(html.xpath("//*[@id='webpage']//div[4]//div[2]//div[1]//div[2]//div[1]//ul[1]//li[2]//b//text()").get());

    List<String> row1 = html.xpath("//*[@id=\"webpage\"]//div[4]//div[2]//div[3]//div//table//tbody//tr//td[2]//text()").all();

    List<String> row2 = html.xpath("//*[@id=\"webpage\"]//div[4]//div[2]//div[4]//div//table//tbody//tr//td[2]//text()").all();
    List<String> row2s = html.xpath("//*[@id=\"webpage\"]//div[4]//div[2]//div[4]//div//table//tbody//tr//td[4]//text()").all();

    List<String> row3 = html.xpath("//*[@id=\"webpage\"]//div[4]//div[2]//div[5]//div//table//tbody//tr//td[2]//text()").all();
    List<String> row3s = html.xpath("//*[@id=\"webpage\"]//div[4]//div[2]//div[5]//div//table//tbody//tr//td[4]//text()").all();

    List<String> row4 = html.xpath("//*[@id=\"webpage\"]//div[4]//div[2]//div[6]//div//table//tbody//tr//td[2]//text()").all();
    List<String> row4s = html.xpath("//*[@id=\"webpage\"]//div[4]//div[2]//div[6]//div//table//tbody//tr//td[4]//text()").all();

    List<String> row5 = html.xpath("//*[@id=\"webpage\"]//div[4]//div[2]//div[7]//div//table//tbody//tr//td[2]//text()").all();
    List<String> row5s = html.xpath("//*[@id=\"webpage\"]//div[4]//div[2]//div[7]//div//table//tbody//tr//td[4]//text()").all();


    allContentList.addAll(row1);
    allContentList.addAll(row2);
    allContentList.addAll(row2s);
    allContentList.addAll(row3);
    allContentList.addAll(row3s);
    allContentList.addAll(row4);
    allContentList.addAll(row4s);
    allContentList.addAll(row5);
    allContentList.addAll(row5s);

    // 特有属性
    List<String> row6 = html.xpath("[@id=\"webpage\"]//div[4]//div[2]//div[9]//div//table[1]//tbody//tr[@class='gxhpzConBg1']//td[1]").all();
    List<String> row6s = html.xpath("[@id=\"webpage\"]//div[4]//div[2]//div[9]//div//table[1]//tbody//tr[@class='gxhpzConBg1']//td[3]").all();

    if (row6 != null && row6.size() > 0) {
      allContentList.addAll(row6);
    }

    if (row6s != null && row6s.size() > 0) {
      allContentList.addAll(row6s);
    }
    for (Iterator<String> iterator = allContentList.iterator(); iterator.hasNext();) {
      String s = iterator.next();
      if (s != null && s.trim().equals("1")) {
        iterator.remove();
      }
    }
    String[] rows = new String[allContentList.size()];
    List<String[]> list = Lists.newArrayList();
    list.add(allContentList.toArray(rows));
    return list;
  }

  @Override
  public void parseToCsv(ParsePage page) {
    this.write(this.getFullName(page), this.getHeaders(page), this.getContent(page));
  }

  public static void main(String[] args) {
    Map<Integer, Integer> map = new HashMap<>();
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
