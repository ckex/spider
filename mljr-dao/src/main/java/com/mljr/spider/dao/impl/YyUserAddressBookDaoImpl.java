package com.mljr.spider.dao.impl;

import java.lang.Integer;
import java.util.List;
import java.util.Collections;

import org.springframework.stereotype.Repository;

import com.mljr.spider.dao.YyUserAddressBookDao;
import com.mljr.spider.dao.dynamic.datasource.DataSource;
import com.mljr.spider.model.YyUserAddressBookDo;
import common.search.util.SearchMap;
import common.page.util.PageQuery;
import common.page.util.PageList;
import common.page.util.Paginator;

/**
 * @author ckex created 2016-11-29 16:14:48:048
 * @explain -
 */
@Repository("yyUserAddressBookDao")
public class YyUserAddressBookDaoImpl extends AbstractBasicDao implements YyUserAddressBookDao {

    @Override
    public YyUserAddressBookDo load( Long id) {
         SearchMap map =  new SearchMap();
         map.add( "id",id);
        return (YyUserAddressBookDo) getSqlSessionTemplate().selectOne("Mapper.yy_user_address_book.load" , map);
    }

    @Override
    public boolean delete(Long id) {
         SearchMap map =  new SearchMap();
         map.add( "id",id);
        return getSqlSessionTemplate().delete("Mapper.yy_user_address_book.delete", map) > 0;
    }

    @Override
    public YyUserAddressBookDo create(YyUserAddressBookDo record) {
         getSqlSessionTemplate().insert("Mapper.yy_user_address_book.create" , record);
        return record;
    }

    @Override
    public boolean update(YyUserAddressBookDo record) {
        return getSqlSessionTemplate().update("Mapper.yy_user_address_book.update", record) > 0;
    }

    @Override
    public PageList<YyUserAddressBookDo>  listByPage(PageQuery pageQuery, Integer count){
         SearchMap map =  new SearchMap();
         map.add("startIndex",pageQuery.getStartIndex());
         map.add("pageSize", pageQuery.getPageSize());
         if( count == null || count.intValue() == 0 ) {
             count = (Integer)getSqlSessionTemplate().selectOne("Mapper.yy_user_address_book.listByPageCount",map);
         }
         List<YyUserAddressBookDo>  list = Collections.emptyList();
         if( count != null && count.intValue() > 0 ) {
             list = getSqlSessionTemplate().selectList("Mapper.yy_user_address_book.listByPage",map);
          }
         Paginator paginator =  new Paginator(pageQuery.getPageSize(), count == null ? 0 : count);
         paginator.setPage(pageQuery.getPageNum());
         PageList<YyUserAddressBookDo> result =  new PageList<YyUserAddressBookDo>(list);
         result.setPaginator(paginator);
         return result;
     }

	@Override
	public List<YyUserAddressBookDo> listById(long lastId,int limit) {
		SearchMap map =  new SearchMap();
        map.add( "id",lastId);
        map.add( "limit",limit);
        return getSqlSessionTemplate().selectList("Mapper.yy_user_address_book.listById",map);
	}
 }
