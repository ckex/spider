package com.mljr.spider.dao.impl;

import com.mljr.spider.dao.YyUserCallRecordHistoryDao;
import com.mljr.spider.model.YyUserCallRecordHistoryDo;
import common.page.util.PageList;
import common.page.util.PageQuery;
import common.page.util.Paginator;
import common.search.util.SearchMap;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * @author ckex created 2016-11-29 16:14:49:049
 * @explain -
 */
@Repository("yyUserCallRecordHistoryDao")
public class YyUserCallRecordHistoryDaoImpl extends AbstractBasicDao implements YyUserCallRecordHistoryDao {

  @Override
  public YyUserCallRecordHistoryDo load(Long id) {
    SearchMap map = new SearchMap();
    map.add("id", id);
    return (YyUserCallRecordHistoryDo) getSqlSessionTemplate().selectOne("Mapper.yy_user_call_record_history.load", map);
  }

  @Override
  public boolean delete(Long id) {
    SearchMap map = new SearchMap();
    map.add("id", id);
    return getSqlSessionTemplate().delete("Mapper.yy_user_call_record_history.delete", map) > 0;
  }

  @Override
  public YyUserCallRecordHistoryDo create(YyUserCallRecordHistoryDo record) {
    getSqlSessionTemplate().insert("Mapper.yy_user_call_record_history.create", record);
    return record;
  }

  @Override
  public boolean update(YyUserCallRecordHistoryDo record) {
    return getSqlSessionTemplate().update("Mapper.yy_user_call_record_history.update", record) > 0;
  }

  @Override
  public PageList<YyUserCallRecordHistoryDo> listByPage(PageQuery pageQuery, Integer count) {
    SearchMap map = new SearchMap();
    map.add("startIndex", pageQuery.getStartIndex());
    map.add("pageSize", pageQuery.getPageSize());
    if (count == null || count.intValue() == 0) {
      count = (Integer) getSqlSessionTemplate().selectOne("Mapper.yy_user_call_record_history.listByPageCount", map);
    }
    List<YyUserCallRecordHistoryDo> list = Collections.emptyList();
    if (count != null && count.intValue() > 0) {
      list = getSqlSessionTemplate().selectList("Mapper.yy_user_call_record_history.listByPage", map);
    }
    Paginator paginator = new Paginator(pageQuery.getPageSize(), count == null ? 0 : count);
    paginator.setPage(pageQuery.getPageNum());
    PageList<YyUserCallRecordHistoryDo> result = new PageList<YyUserCallRecordHistoryDo>(list);
    result.setPaginator(paginator);
    return result;
  }

  @Override
  public List<YyUserCallRecordHistoryDo> listById(long id, int limit) {
    SearchMap map = new SearchMap();
    map.add("id", id);
    map.add("limit", limit);
    return getSqlSessionTemplate().selectList("Mapper.yy_user_call_record_history.listById", map);
  }
}
