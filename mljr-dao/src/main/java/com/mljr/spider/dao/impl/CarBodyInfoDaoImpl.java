package com.mljr.spider.dao.impl;

import com.mljr.spider.dao.CarBodyInfoDao;
import com.mljr.spider.model.CarBodyInfoDo;
import common.page.util.PageList;
import common.page.util.PageQuery;
import common.page.util.Paginator;
import common.search.util.SearchMap;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * Created by fulin on 2017/1/24.
 */
@Repository("carBodyInfoDao")
public class CarBodyInfoDaoImpl extends AbstractBasicDao implements CarBodyInfoDao {

  @Override
  public CarBodyInfoDo create(CarBodyInfoDo record) {
    getSqlSessionTemplate().insert("Mapper.car_home_merge_info.create", record);
    return record;
  }

  @Override
  public PageList<CarBodyInfoDo> listByPage(PageQuery pageQuery, Integer count) {
    SearchMap map = new SearchMap();
    map.add("startIndex", pageQuery.getStartIndex());
    map.add("pageSize", pageQuery.getPageSize());
    if (count == null || count.intValue() == 0) {
      count = (Integer) getSqlSessionTemplate().selectOne("Mapper.car_home_merge_info.listByPageCount", map);
    }
    List<CarBodyInfoDo> list = Collections.emptyList();
    if (count != null && count.intValue() > 0) {
      map.add("pageSize", count.intValue());
      list = getSqlSessionTemplate().selectList("Mapper.car_home_merge_info.listByPage", map);
    }
    Paginator paginator = new Paginator(pageQuery.getPageSize(), count == null ? 0 : count);
    paginator.setPage(pageQuery.getPageNum());
    PageList<CarBodyInfoDo> result = new PageList<CarBodyInfoDo>(list);
    result.setPaginator(paginator);
    return result;
  }

  @Override
  public CarBodyInfoDo load(String carName) {
    SearchMap map = new SearchMap();
    map.add("car_name",carName);
    return (CarBodyInfoDo)getSqlSessionTemplate().selectOne("Mapper.car_home_merge_info.listByName",map);
  }

  @Override
  public boolean update(CarBodyInfoDo record) {
    return getSqlSessionTemplate().update("Mapper.car_home_merge_info.update", record) > 0;
  }
  //腾讯汽车
  @Override
  public CarBodyInfoDo createTencent(CarBodyInfoDo record) {
    getSqlSessionTemplate().insert("Mapper.tencent_qq_car_info.createTencent", record);
    return record;
  }

  @Override
  public PageList<CarBodyInfoDo> listByTencentPage(PageQuery pageQuery, Integer count) {
    SearchMap map = new SearchMap();
    map.add("startIndex", pageQuery.getStartIndex());
    map.add("pageSize", pageQuery.getPageSize());
    if (count == null || count.intValue() == 0) {
      count = (Integer) getSqlSessionTemplate().selectOne("Mapper.tencent_qq_car_info.listByPageCount", map);
    }
    List<CarBodyInfoDo> list = Collections.emptyList();
    if (count != null && count.intValue() > 0) {
      map.add("pageSize", count.intValue());
      list = getSqlSessionTemplate().selectList("Mapper.tencent_qq_car_info.listByTencentPage", map);
    }
    Paginator paginator = new Paginator(pageQuery.getPageSize(), count == null ? 0 : count);
    paginator.setPage(pageQuery.getPageNum());
    PageList<CarBodyInfoDo> result = new PageList<CarBodyInfoDo>(list);
    result.setPaginator(paginator);
    return result;
  }

  @Override
  public CarBodyInfoDo loadTencent(String carName) {
    SearchMap map = new SearchMap();
    map.add("car_name",carName);
    return (CarBodyInfoDo)getSqlSessionTemplate().selectOne("Mapper.tencent_qq_car_info.listByName",map);
  }

  @Override
  public boolean updateTencent(CarBodyInfoDo record) {
    return getSqlSessionTemplate().update("Mapper.tencent_qq_car_info.updateTencent", record) > 0;
  }
  //网易汽车
  @Override
  public CarBodyInfoDo createAuto(CarBodyInfoDo record) {
    getSqlSessionTemplate().insert("Mapper.auto_163_car_info.createAuto", record);
    return record;
  }

  @Override
  public PageList<CarBodyInfoDo> listByAutoPage(PageQuery pageQuery, Integer count) {
    SearchMap map = new SearchMap();
    map.add("startIndex", pageQuery.getStartIndex());
    map.add("pageSize", pageQuery.getPageSize());
    if (count == null || count.intValue() == 0) {
      count = (Integer) getSqlSessionTemplate().selectOne("Mapper.auto_163_car_info.listByPageCount", map);
    }
    List<CarBodyInfoDo> list = Collections.emptyList();
    if (count != null && count.intValue() > 0) {
      map.add("pageSize", count.intValue());
      list = getSqlSessionTemplate().selectList("Mapper.auto_163_car_info.listByAutoPage", map);
    }
    Paginator paginator = new Paginator(pageQuery.getPageSize(), count == null ? 0 : count);
    paginator.setPage(pageQuery.getPageNum());
    PageList<CarBodyInfoDo> result = new PageList<CarBodyInfoDo>(list);
    result.setPaginator(paginator);
    return result;
  }

  @Override
  public CarBodyInfoDo loadAuto(String carName) {
    SearchMap map = new SearchMap();
    map.add("car_name",carName);
    return (CarBodyInfoDo)getSqlSessionTemplate().selectOne("Mapper.auto_163_car_info.listByName",map);
  }

  @Override
  public boolean updateAuto(CarBodyInfoDo record) {
    return getSqlSessionTemplate().update("Mapper.auto_163_car_info.updateAuto", record) > 0;
  }
}
