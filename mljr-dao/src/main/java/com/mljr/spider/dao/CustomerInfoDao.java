package com.mljr.spider.dao;

import com.mljr.spider.dao.dynamic.datasource.DataSource;

import java.util.List;
import java.util.Map;

/**
 * @author ckex created 2016-12-11 10:37:07:007
 * @explain -
 */
@DataSource("rc")
public interface CustomerInfoDao {

	List<Map> listById(String lastId, int limit);

}