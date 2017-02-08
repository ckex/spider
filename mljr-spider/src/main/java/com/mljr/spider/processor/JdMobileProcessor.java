/**
 *
 */
package com.mljr.spider.processor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mljr.spider.dao.DmPhonePriceDao;
import com.mljr.spider.dao.SrcDmPhonePriceDao;
import com.mljr.spider.model.SrcDmPhonePriceDo;
import com.mljr.spider.storage.JdMobileMysqlPipeline;
import common.page.util.PageList;
import common.page.util.PageQuery;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.PhantomJSDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.selector.Html;

import java.util.List;
import java.util.Map;


public class JdMobileProcessor extends AbstractPageProcessor {
    static SrcDmPhonePriceDao srcDmPhonePriceDao;

    private static Site site = Site.me().setDomain("item.jd.com").setSleepTime(300).setRetrySleepTime(2000).setRetryTimes(3).setUserAgent(
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

    public static Map<String, SrcDmPhonePriceDo> priceMap = Maps.newHashMap();


    @Override
    boolean onProcess(Page page) {
        Html html = page.getHtml();
        String price = html.xpath("//html//body//div[5]//div//div[2]//div[3]//div//div[1]//div[2]//span//span[2]//text()").get();
        if(StringUtils.isBlank(price)){
            page.setSkip(true);
            return true;
        }
//        System.out.println("price ====== " + price);
        page.putField("price", price);
        page.putField("srcDmPhonePriceDo", priceMap.get(page.getUrl().toString()));
        return true;
    }

    public JdMobileProcessor() {
        super(site);
    }

    public List<String> getItemUrls() {
        PageList<SrcDmPhonePriceDo> list = srcDmPhonePriceDao.listByPage(new PageQuery(0, 1000), null);
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        List<String> itemUrls = Lists.newArrayList();
        for (SrcDmPhonePriceDo priceDo : list) {
            itemUrls.add(priceDo.getItemUrl());
            priceMap.put(priceDo.getItemUrl(), priceDo);
        }
        return itemUrls;
    }

    public static ApplicationContext ctx;

    public static void main(String[] args) throws Exception {
        ctx = new ClassPathXmlApplicationContext(new String[] { "classpath*:/spring/dao.xml",
                "classpath*:/spring/dao-datasource.xml", "classpath*:/spring/applicationContext.xml" });
        srcDmPhonePriceDao = (SrcDmPhonePriceDao)ctx.getBean("srcDmPhonePriceDao");
        JdMobileProcessor processor = new JdMobileProcessor();
        processor.getItemUrls();

        Spider spider = Spider.create(new JdMobileProcessor())
                .setDownloader(new PhantomJSDownloader())
                .addUrl("https://item.jd.com/3899582.html")
                .addPipeline(new ConsolePipeline())
                .addPipeline(new JdMobileMysqlPipeline((DmPhonePriceDao)ctx.getBean("dmPhonePriceDao")))
                .thread((Runtime.getRuntime().availableProcessors() - 1) << 1);

        spider.run();

    }

}
