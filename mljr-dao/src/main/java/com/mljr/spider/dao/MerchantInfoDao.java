package com.mljr.spider.dao;

import com.mljr.spider.dao.dynamic.datasource.DataSource;
import com.mljr.spider.model.MerchantInfoDo;
import com.mljr.spider.model.SpiderBankCardLocationDo;
import common.page.util.PageList;
import common.page.util.PageQuery;

import java.util.List;

/**
 * @author ckex created 2016-12-11 10:37:07:007
 * @explain -
 */
public interface MerchantInfoDao {

	@DataSource("rc")
	List<MerchantInfoDo> listById(String lastId, int limit);
}