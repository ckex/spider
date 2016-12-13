package com.mljr.spider.dao;

import com.mljr.spider.dao.dynamic.datasource.DataSource;
import com.mljr.spider.model.MerchantInfoDo;

import java.util.HashMap;
import java.util.List;

/**
 * @author ckex created 2016-12-11 10:37:07:007
 * @explain -
 */
@DataSource("rc")
public interface MerchantInfoDao {

	List<MerchantInfoDo> listById(String lastId, int limit);

	List<HashMap> listAddressById(String lastId, int limit);
}