/**
 * DynamicDataSource.java by Ckex.zha
 */

package com.mljr.spider.dao.dynamic.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author Ckex.zha <br/>
 *         2016年3月1日上午11:36:26
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

  public DynamicDataSource() {
    super();
  }

  @Override
  protected Object determineCurrentLookupKey() {
    return DynamicDataSourceHolder.getDataSouce();
  }

}
