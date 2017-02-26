/**
 * DynamicDataSourceHolder.java by Ckex.zha
 */

package com.mljr.spider.dao.dynamic.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Ckex zha </br>
 *         2016年11月7日,下午1:37:14
 *
 */
public class DynamicDataSourceHolder {

  protected static transient Logger logger = LoggerFactory.getLogger(DynamicDataSourceHolder.class);

  private static final ThreadLocal<String> holder = new ThreadLocal<String>();

  public static void putDataSource(String name) {
    holder.set(name);
  }

  public static String getDataSouce() {
    String dataSourceKey = holder.get();
    if (logger.isDebugEnabled()) {
      logger.debug("Data source key =" + dataSourceKey);
    }
    return dataSourceKey;
  }
}
