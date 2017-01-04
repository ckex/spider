package com.mljr.spider.dao;

import com.mljr.spider.dao.dynamic.datasource.DataSource;

import java.util.List;
import java.util.Map;

/**
 * @author ckex created 2016-11-29 16:14:48:048
 * @explain -
 */
@DataSource("db139")
public interface YybgrkContactListDao {

	List<Map> listById(long lastId, int limit);

	List<Map> listHistoryById(long lastId, int limit);
}