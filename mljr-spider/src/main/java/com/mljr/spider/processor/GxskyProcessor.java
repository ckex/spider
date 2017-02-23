

package com.mljr.spider.processor;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.mljr.spider.vo.JSONTransferVO;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Selectable;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fulin on 2016/12/19.
 */
public class GxskyProcessor extends AbstractPageProcessor {
  private static String TARGET_URL = "http://idcard.gxsky.com/more_card.asp?page=%s&city=%s";

  @Override
  boolean onProcess(Page page) {
    if (page.getUrl().toString().contains("city") && page.getUrl().toString().contains("page")) {
      if (!page.getHtml().toString().contains("姓名")) {
        return true;
      }
      // 抓取页面特定信息
      processOneCity(page);
    } else {
      List<String> cityNames = getCityNames(page);
      for (String cityName : cityNames) {
        for (int i = 1; i < 37; i++) {
          String targetUrl = String.format(TARGET_URL, i, cityName);
          page.addTargetRequest(targetUrl);
        }
      }
    }
    return true;
  }

  // 构造函数
  public GxskyProcessor() {
    super(site);
  }

  private static final Site site = Site.me().setDomain("idcard.gxsky.com").setSleepTime(1000).setRetrySleepTime(1000).setCharset("gb2312")
      .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")// bianma
      .setRetryTimes(3)
      .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

  // 获取城市名称
  private List<String> getCityNames(Page page) {
    List<String> allLinks = page.getHtml().links().all();
    List<String> cityNames = new ArrayList<>();
    for (String link : allLinks) {
      if (link.contains("?city=")) {
        String name = link.substring(link.indexOf("=") + 1);
        cityNames.add(URLEncoder.encode(name));
      }
    }
    return cityNames;
  }

  // 提取一个页面的数据
  public void processOneCity(Page page) {
    List<Selectable> list = page.getHtml()
        .xpath("/html/body/table/tbody/tr[2]/td/div/table[2]/tbody/tr[2]/td/div/table/tbody/tr/td/table[1]/tbody/tr/td/table/tbody").nodes();
    // 如果存在就遍历读取节点
    JSONTransferVO json = new JSONTransferVO();
    List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
    Map<String, Object> map = null;
    if (null != list && list.size() > 0) {
      for (int i = 0; i < list.size(); i++) {
        map = Maps.newHashMap();
        map.put("title", list.get(i).xpath("//tr[1]/td/text()"));
        map.put("name", list.get(i).xpath("//tr[2]/td/font[2]/text()"));
        map.put("id_number", list.get(i).xpath("//tr[3]/td/font[2]/text()"));
        map.put("location", list.get(i).xpath("//tr[4]/td/font[2]/text()"));
        map.put("sign_date", list.get(i).xpath("//tr[5]/td/font[2]/text()"));
        map.put("publish_date", list.get(i).xpath("//tr[6]/td/font[2]/text()"));
        listmap.add(map);
      }
      json.setContext(listmap);
      page.putField("", JSON.toJSON(json));
    }
  }

}
