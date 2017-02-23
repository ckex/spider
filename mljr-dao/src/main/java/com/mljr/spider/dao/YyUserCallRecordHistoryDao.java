package com.mljr.spider.dao;

import com.mljr.spider.dao.dynamic.datasource.DataSource;
import com.mljr.spider.model.YyUserCallRecordHistoryDo;
import common.page.util.PageList;
import common.page.util.PageQuery;

import java.util.List;

/**
 * @author ckex created 2016-11-29 16:14:49:049
 * @explain -
 */
@DataSource("rw")
public interface YyUserCallRecordHistoryDao {

  YyUserCallRecordHistoryDo load(Long id);

  boolean delete(Long id);

  YyUserCallRecordHistoryDo create(YyUserCallRecordHistoryDo record);

  boolean update(YyUserCallRecordHistoryDo record);

  PageList<YyUserCallRecordHistoryDo> listByPage(PageQuery pageQuery, Integer count);

  List<YyUserCallRecordHistoryDo> listById(long id, int limit);
}
