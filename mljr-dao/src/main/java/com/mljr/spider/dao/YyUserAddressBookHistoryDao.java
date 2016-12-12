package com.mljr.spider.dao;

import java.lang.Integer;

import com.mljr.spider.dao.dynamic.datasource.DataSource;
import com.mljr.spider.model.YyUserAddressBookHistoryDo;
import common.page.util.PageList;
import common.page.util.PageQuery;

/**
 * @author ckex created 2016-11-29 16:14:48:048
 * @explain -
 */
@DataSource("rw")
public interface YyUserAddressBookHistoryDao {

    YyUserAddressBookHistoryDo load(Long id);

    boolean delete(Long id);

    YyUserAddressBookHistoryDo create(YyUserAddressBookHistoryDo record);

    boolean update(YyUserAddressBookHistoryDo record);

    PageList<YyUserAddressBookHistoryDo>  listByPage(PageQuery pageQuery, Integer count);
}