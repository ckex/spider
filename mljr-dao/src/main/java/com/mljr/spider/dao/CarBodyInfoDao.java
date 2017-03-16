package com.mljr.spider.dao;

import com.mljr.spider.dao.dynamic.datasource.DataSource;
import com.mljr.spider.model.CarBodyInfoDo;
import common.page.util.PageList;
import common.page.util.PageQuery;

/**
 * Created by fulin on 2017/1/24.
 */
@DataSource("udb")
public interface CarBodyInfoDao {
  CarBodyInfoDo create(CarBodyInfoDo record);

  PageList<CarBodyInfoDo> listByPage(PageQuery pageQuery, Integer count);

  CarBodyInfoDo  load(String carName);

  boolean update(CarBodyInfoDo record);

  //腾讯汽车操作
  CarBodyInfoDo createTencent(CarBodyInfoDo record);

  PageList<CarBodyInfoDo> listByTencentPage(PageQuery pageQuery, Integer count);

  CarBodyInfoDo  loadTencent(String carName);

  boolean updateTencent(CarBodyInfoDo record);
  //网易汽车操作
  CarBodyInfoDo createAuto(CarBodyInfoDo record);

  PageList<CarBodyInfoDo> listByAutoPage(PageQuery pageQuery, Integer count);

  CarBodyInfoDo  loadAuto(String carName);

  boolean updateAuto(CarBodyInfoDo record);

}
