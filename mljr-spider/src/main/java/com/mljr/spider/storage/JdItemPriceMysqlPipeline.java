/**
 *
 */
package com.mljr.spider.storage;

import com.mljr.spider.dao.DmPhonePriceDao;
import com.mljr.spider.dao.SrcDmPhonePriceDao;
import com.mljr.spider.model.DmPhonePriceDo;
import com.mljr.spider.model.SrcDmPhonePriceDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Date;

public class JdItemPriceMysqlPipeline implements Pipeline {

    private static final Logger logger = LoggerFactory.getLogger(JdItemPriceMysqlPipeline.class);

    private static SrcDmPhonePriceDao srcDmPhonePriceDao;

    private static DmPhonePriceDao dmPhonePriceDao;

    private static ApplicationContext ctx;

    public JdItemPriceMysqlPipeline() {
        if (ctx == null) {
            ctx = new ClassPathXmlApplicationContext("classpath*:/spring/dao.xml",
                    "classpath*:/spring/dao-datasource.xml",
                    "classpath*:/spring/applicationContext.xml");
        }
        if (srcDmPhonePriceDao == null) {
            srcDmPhonePriceDao = (SrcDmPhonePriceDao) ctx.getBean("srcDmPhonePriceDao");
        }

        if (dmPhonePriceDao == null) {
            dmPhonePriceDao = (DmPhonePriceDao) ctx.getBean("dmPhonePriceDao");
        }

    }

    @Override
    public void process(ResultItems resultItems, Task task) {

        String price = resultItems.get("price");
        String srcUrl = resultItems.getRequest().getUrl();
        String itemUrl = "https://item.jd.com/" +
                srcUrl.substring(srcUrl.lastIndexOf("_")+1) +
                ".html";
        SrcDmPhonePriceDo src = srcDmPhonePriceDao.load(itemUrl);
        DmPhonePriceDo priceDo = new DmPhonePriceDo();
        priceDo.setBrand(src.getBrand());
        priceDo.setTitle(src.getTitle());
        priceDo.setCreateDate(new Date());
        priceDo.setWebsite("JD");
        priceDo.setPrice(Float.parseFloat(price));
        dmPhonePriceDao.create(priceDo);

    }

}
