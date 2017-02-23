package com.mljr.spider.dao;

import com.mljr.spider.dao.dynamic.datasource.DataSource;
import com.mljr.spider.model.CarBodyInfoDo;
import common.page.util.PageList;
import common.page.util.PageQuery;

/**
 * Created by fulin on 2017/1/24.
 */
@DataSource("mljrdev")
public interface CarBodyInfoDao {
  CarBodyInfoDo create(CarBodyInfoDo record);

  PageList<CarBodyInfoDo> listByPage(PageQuery pageQuery, Integer count);
}
