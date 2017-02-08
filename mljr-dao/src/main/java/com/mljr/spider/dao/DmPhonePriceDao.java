package com.mljr.spider.dao;

import com.mljr.spider.dao.dynamic.datasource.DataSource;
import com.mljr.spider.model.DmPhonePriceDo;
import common.page.util.PageList;
import common.page.util.PageQuery;
@DataSource("mljrdev")
public interface DmPhonePriceDao {

    DmPhonePriceDo load(Long id);

    boolean delete(Long id); 

    DmPhonePriceDo create(DmPhonePriceDo record);

    boolean update(DmPhonePriceDo record);

    PageList<DmPhonePriceDo>  listByPage(PageQuery pageQuery, Integer count);

}