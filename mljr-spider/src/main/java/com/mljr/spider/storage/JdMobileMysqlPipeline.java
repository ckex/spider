/**
 *
 */
package com.mljr.spider.storage;

import com.mljr.spider.dao.DmPhonePriceDao;
import com.mljr.spider.model.DmPhonePriceDo;
import com.mljr.spider.model.SrcDmPhonePriceDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Date;

public class JdMobileMysqlPipeline implements Pipeline {

    private static final Logger logger = LoggerFactory.getLogger(JdMobileMysqlPipeline.class);

    private DmPhonePriceDao dmPhonePriceDao;

    public JdMobileMysqlPipeline(DmPhonePriceDao dmPhonePriceDao) {
        this.dmPhonePriceDao = dmPhonePriceDao;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {

        String price = resultItems.get("price");
        SrcDmPhonePriceDo src = resultItems.get("srcDmPhonePriceDo");
        DmPhonePriceDo priceDo = new DmPhonePriceDo();
        priceDo.setBrand(src.getBrand());
        priceDo.setTitle(src.getTitle());
        priceDo.setCreateDate(new Date());
        priceDo.setWebsite("JD");
        priceDo.setPrice(Float.parseFloat(price));
        dmPhonePriceDao.create(priceDo);

    }

}
