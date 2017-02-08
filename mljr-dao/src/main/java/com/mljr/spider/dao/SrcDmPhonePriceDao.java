package com.mljr.spider.dao;

import com.mljr.spider.dao.dynamic.datasource.DataSource;
import com.mljr.spider.model.SrcDmPhonePriceDo;
import common.page.util.PageList;
import common.page.util.PageQuery;

@DataSource("mljrdev")
public interface SrcDmPhonePriceDao {

    SrcDmPhonePriceDo load(String itemUrl);

    boolean delete(Long id);

    SrcDmPhonePriceDo create(SrcDmPhonePriceDo record);

    boolean update(SrcDmPhonePriceDo record);

    PageList<SrcDmPhonePriceDo>  listByPage(PageQuery pageQuery, Integer count);

}