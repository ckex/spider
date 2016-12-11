package com.mljr.spider.dao;

import java.lang.Integer;

import com.mljr.spider.dao.dynamic.datasource.DataSource;
import com.mljr.spider.model.SpiderBankCardLocationDo;
import common.page.util.PageList;
import common.page.util.PageQuery;

/**
 * @author ckex created 2016-12-11 10:37:07:007
 * @explain -
 */
public interface SpiderBankCardLocationDao {

	@DataSource("RC")
	SpiderBankCardLocationDo load(java.lang.String bank_card_11_digits);

	boolean delete(java.lang.String bank_card_11_digits);

	SpiderBankCardLocationDo create(SpiderBankCardLocationDo record);

	boolean update(SpiderBankCardLocationDo record);

	PageList<SpiderBankCardLocationDo> listByPage(PageQuery pageQuery, Integer count);
}