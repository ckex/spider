package com.mljr.spider.dao.impl;

import java.lang.Integer;
import java.util.List;
import java.util.Collections;

import org.springframework.stereotype.Repository;

import com.mljr.spider.dao.YyUserAddressBookHistoryDao;
import com.mljr.spider.model.YyUserAddressBookHistoryDo;
import common.search.util.SearchMap;
import common.page.util.PageQuery;
import common.page.util.PageList;
import common.page.util.Paginator;

/**
 * @author ckex created 2016-11-29 16:14:48:048
 * @explain -
 */
@Repository("yyUserAddressBookHistoryDao")
public class YyUserAddressBookHistoryDaoImpl extends AbstractBasicDao implements YyUserAddressBookHistoryDao {

    @Override
    public YyUserAddressBookHistoryDo load( Long id) {
         SearchMap map =  new SearchMap();
         map.add( "id",id);
        return (YyUserAddressBookHistoryDo) getSqlSessionTemplate().selectOne("Mapper.yy_user_address_book_history.load" , map);
    }

    @Override
    public boolean delete(Long id) {
         SearchMap map =  new SearchMap();
         map.add( "id",id);
        return getSqlSessionTemplate().delete("Mapper.yy_user_address_book_history.delete", map) > 0;
    }

    @Override
    public YyUserAddressBookHistoryDo create(YyUserAddressBookHistoryDo record) {
         getSqlSessionTemplate().insert("Mapper.yy_user_address_book_history.create" , record);
        return record;
    }

    @Override
    public boolean update(YyUserAddressBookHistoryDo record) {
        return getSqlSessionTemplate().update("Mapper.yy_user_address_book_history.update", record) > 0;
    }

    @Override
    public PageList<YyUserAddressBookHistoryDo>  listByPage(PageQuery pageQuery, Integer count){
         SearchMap map =  new SearchMap();
         map.add("startIndex",pageQuery.getStartIndex());
         map.add("pageSize", pageQuery.getPageSize());
         if( count == null || count.intValue() == 0 ) {
             count = (Integer)getSqlSessionTemplate().selectOne("Mapper.yy_user_address_book_history.listByPageCount",map);
         }
         List<YyUserAddressBookHistoryDo>  list = Collections.emptyList();
         if( count != null && count.intValue() > 0 ) {
             list = getSqlSessionTemplate().selectList("Mapper.yy_user_address_book_history.listByPage",map);
          }
         Paginator paginator =  new Paginator(pageQuery.getPageSize(), count == null ? 0 : count);
         paginator.setPage(pageQuery.getPageNum());
         PageList<YyUserAddressBookHistoryDo> result =  new PageList<YyUserAddressBookHistoryDo>(list);
         result.setPaginator(paginator);
         return result;
     }

    @Override
    public List<YyUserAddressBookHistoryDo> listById(long lastId, int limit) {
        SearchMap map =  new SearchMap();
        map.add( "id",lastId);
        map.add( "limit",limit);
        return getSqlSessionTemplate().selectList("Mapper.yy_user_address_book_history.listById",map);
    }
}
