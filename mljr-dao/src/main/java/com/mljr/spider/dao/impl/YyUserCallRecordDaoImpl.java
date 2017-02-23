package com.mljr.spider.dao.impl;

import java.lang.Integer;
import java.util.List;
import java.util.Collections;

import org.springframework.stereotype.Repository;

import com.mljr.spider.dao.YyUserCallRecordDao;
import com.mljr.spider.model.YyUserCallRecordDo;
import common.search.util.SearchMap;
import common.page.util.PageQuery;
import common.page.util.PageList;
import common.page.util.Paginator;

/**
 * @author ckex created 2016-11-29 16:14:49:049
 * @explain -
 */
@Repository("yyUserCallRecordDao")
public class YyUserCallRecordDaoImpl extends AbstractBasicDao implements YyUserCallRecordDao {

  @Override
  public YyUserCallRecordDo load(Long id) {
    SearchMap map = new SearchMap();
    map.add("id", id);
    return (YyUserCallRecordDo) getSqlSessionTemplate().selectOne("Mapper.yy_user_call_record.load", map);
  }

  @Override
  public boolean delete(Long id) {
    SearchMap map = new SearchMap();
    map.add("id", id);
    return getSqlSessionTemplate().delete("Mapper.yy_user_call_record.delete", map) > 0;
  }

  @Override
  public YyUserCallRecordDo create(YyUserCallRecordDo record) {
    getSqlSessionTemplate().insert("Mapper.yy_user_call_record.create", record);
    return record;
  }

  @Override
  public boolean update(YyUserCallRecordDo record) {
    return getSqlSessionTemplate().update("Mapper.yy_user_call_record.update", record) > 0;
  }

  @Override
  public PageList<YyUserCallRecordDo> listByPage(PageQuery pageQuery, Integer count) {
    SearchMap map = new SearchMap();
    map.add("startIndex", pageQuery.getStartIndex());
    map.add("pageSize", pageQuery.getPageSize());
    if (count == null || count.intValue() == 0) {
      count = (Integer) getSqlSessionTemplate().selectOne("Mapper.yy_user_call_record.listByPageCount", map);
    }
    List<YyUserCallRecordDo> list = Collections.emptyList();
    if (count != null && count.intValue() > 0) {
      list = getSqlSessionTemplate().selectList("Mapper.yy_user_call_record.listByPage", map);
    }
    Paginator paginator = new Paginator(pageQuery.getPageSize(), count == null ? 0 : count);
    paginator.setPage(pageQuery.getPageNum());
    PageList<YyUserCallRecordDo> result = new PageList<YyUserCallRecordDo>(list);
    result.setPaginator(paginator);
    return result;
  }

  @Override
  public List<YyUserCallRecordDo> listById(long id, int limit) {
    SearchMap map = new SearchMap();
    map.add("id", id);
    map.add("limit", limit);
    return getSqlSessionTemplate().selectList("Mapper.yy_user_call_record.listById", map);
  }

}
