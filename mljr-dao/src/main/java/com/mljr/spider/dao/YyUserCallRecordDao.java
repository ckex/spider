package com.mljr.spider.dao;

import java.lang.Integer;
import java.util.List;

import com.mljr.spider.dao.dynamic.datasource.DataSource;
import com.mljr.spider.model.YyUserCallRecordDo;
import common.page.util.PageList;
import common.page.util.PageQuery;

/**
 * @author ckex created 2016-11-29 16:14:49:049
 * @explain -
 */
@DataSource("rw")
public interface YyUserCallRecordDao {

	YyUserCallRecordDo load(Long id);

	boolean delete(Long id);

	YyUserCallRecordDo create(YyUserCallRecordDo record);

	boolean update(YyUserCallRecordDo record);

	PageList<YyUserCallRecordDo> listByPage(PageQuery pageQuery, Integer count);

	List<YyUserCallRecordDo> listById(long id, int limit);
}