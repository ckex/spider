/**
 * 
 */
package com.mljr.spider.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

/**
 * @author Ckex zha </br>
 *         2016年11月6日,下午7:21:12
 *
 */
public class PatternTests {

  /**
   * 
   */
  public PatternTests() {
    // TODO Auto-generated constructor stub
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    String url = "http://op.juhe.cn/onebox/phone/query?tel= 031185923183\n" + "&key=d3baaded0db0ea2dd0a359fb485e3d60";
    System.out.println(url);
    url = CharMatcher.WHITESPACE.replaceFrom(CharMatcher.anyOf("\r\n\t").replaceFrom(url, ""), "");
    System.out.println(url);
    for (int i = 0; i < 100; i++) {
      if (Math.random() * 100 < 5) {
        System.out.println("xxx");
      }
    }

    Pattern pat = Pattern.compile("\'(.*?)\'");
    pat = Pattern.compile("(?<=\\()(.+?)(?=\\))"); // 宽断言,(?<=exp)匹配exp后面的位置
    String str = "<a href=\"#\" onClick=\"return open_new_window( '/patroninfo~S0*chx/1069163/modpinfo' )\">";
    str =
        "querycallback({\"Mobile\":\"15601662655\",\"QueryResult\":\"True\",\"TO\":\"中国联通\",\"Corp\":\"中国联通\",\"Province\":\"上海\",\"City\":\"上海\",\"AreaCode\":\"021\",\"PostCode\":\"200000\",\"VNO\":\"\",\"Card\":\"\"});";
    Matcher mat = pat.matcher(str);
    if (mat.find()) {
      System.out.println(mat.group(1));
    }
    System.out.println(" --------- ");
    String s = " 上海  中国联通";
    System.out.println(Splitter.onPattern("  ").omitEmptyStrings().trimResults().splitToList(s).size());
  }

}
