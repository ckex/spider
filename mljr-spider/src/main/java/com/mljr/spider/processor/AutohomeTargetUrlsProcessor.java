/**
 *
 */
package com.mljr.spider.processor;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.Set;

public class AutohomeTargetUrlsProcessor implements PageProcessor {

  private static Site site = Site.me().setDomain("autohome.com.cn").setSleepTime(300).setRetrySleepTime(2000).setRetryTimes(3)
      .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

  public final static String START_URL = "http://car.autohome.com.cn/AsLeftMenu/As_LeftListNew.ashx?typeId=2&brandId=0&fctId=0&seriesId=0";
  public final static String BRAND_URL_REGEX = "http://car\\.autohome\\.com\\.cn/price/brand.*";
  public final static String SERIES_URL_REGEX = "http://car\\.autohome\\.com\\.cn/price/series.*";
  public final static String PEIZHI_URL_REGEX = "http://car.autohome.com.cn/config/spec/.*";

  @Override
  public void process(Page page) {
    String currentUrl = page.getUrl().get();

    if (currentUrl.equals(START_URL)) {
      page.setSkip(true);
      List<String> picUrls = page.getHtml().links().all();
      List<String> brandUrls = Lists.newArrayList();
      for (String picUrl : picUrls) {
        brandUrls.add(picUrl.replace("pic", "price"));
      }
      page.addTargetRequests(brandUrls);
    } else if (currentUrl.matches(BRAND_URL_REGEX)) {
      page.setSkip(true);
      List<String> seriesUrls = page.getHtml().links().regex(SERIES_URL_REGEX).all();
      page.addTargetRequests(seriesUrls);
    } else if (currentUrl.matches(SERIES_URL_REGEX)) {
      List<String> peizhiUrls = page.getHtml().links().regex(PEIZHI_URL_REGEX).all();
      Set<String> set = Sets.newHashSet();
      for (String peizhiUrl : peizhiUrls) {
        if (peizhiUrl.contains("#")) {
          peizhiUrl = peizhiUrl.substring(0, peizhiUrl.indexOf("#"));
        }
        set.add(peizhiUrl);
      }
      page.putField("dataSet", set);

    }
  }

  @Override
  public Site getSite() {
    return site;
  }

  // public static void main(String[] args) throws Exception {
  //
  //
  // Spider spider = Spider.create(new AutohomeTargetUrlsProcessor())
  // .setPipelines(Lists.newArrayList(new AutohomeTargetUrlsPipeline()));
  //
  // spider.addUrl(START_URL);
  // spider.setExecutorService(DEFAULT_THREAD_POOL);
  // spider.thread(20);
  //
  //// final AbstractScheduler scheduler = new AutohomeTargetUrlsScheduler(spider, "autohome_flag");
  // Scheduler scheduler = new QueueScheduler();
  // scheduler.push(new Request(START_URL),spider);
  // spider.run();
  //
  //// try {
  //// FileUtils.writeLines(new File("/Users/songchi/Desktop/autohome_target_urls.txt"), set);
  //// } catch (IOException e) {
  //// e.printStackTrace();
  //// }
  // System.out.println("-------------------end ------------------");
  //
  // }
  //
  // public static final ThreadPoolExecutor DEFAULT_THREAD_POOL = new ThreadPoolExecutor(25, 25,
  // 100,
  // TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10), new ThreadFactory() {
  //
  // @Override
  // public Thread newThread(Runnable r) {
  // return new Thread(r);
  // }
  // }, new ThreadPoolExecutor.CallerRunsPolicy());


}
