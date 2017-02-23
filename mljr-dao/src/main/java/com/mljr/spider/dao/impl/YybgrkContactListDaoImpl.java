package com.mljr.spider.dao.impl;

import com.mljr.spider.dao.YybgrkContactListDao;
import common.search.util.SearchMap;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by songchi on 17/1/3.
 */
@Repository("yybgrkContactListDao")
public class YybgrkContactListDaoImpl extends AbstractBasicDao implements YybgrkContactListDao {
  @Override
  public List<Map> listById(long lastId, int limit) {
    SearchMap map = new SearchMap();
    map.add("id", lastId);
    map.add("limit", limit);
    return getSqlSessionTemplate().selectList("Mapper.yybgrk_contact_list.listById", map);
  }

  @Override
  public List<Map> listHistoryById(long lastId, int limit) {
    SearchMap map = new SearchMap();
    map.add("id", lastId);
    map.add("limit", limit);
    return getSqlSessionTemplate().selectList("Mapper.yybgrk_contact_list.listHistoryById", map);
  }
}
