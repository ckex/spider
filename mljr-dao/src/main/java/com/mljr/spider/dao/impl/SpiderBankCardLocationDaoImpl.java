package com.mljr.spider.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mljr.spider.dao.SpiderBankCardLocationDao;
import com.mljr.spider.model.SpiderBankCardLocationDo;

import common.page.util.PageList;
import common.page.util.PageQuery;
import common.page.util.Paginator;
import common.search.util.SearchMap;

/**
 * @author ckex created 2016-12-11 10:37:07:007
 * @explain -
 */
@Repository("spiderBankCardLocationDao")
public class SpiderBankCardLocationDaoImpl extends AbstractBasicDao implements SpiderBankCardLocationDao {

    @Override
    public SpiderBankCardLocationDo load( java.lang.String bank_card_11_digits) {
         SearchMap map =  new SearchMap();
         map.add( "bank_card_11_digits",bank_card_11_digits);
        return (SpiderBankCardLocationDo) getSqlSessionTemplate().selectOne("Mapper.spider_bank_card_location.load" , map);
    }

	@Override
    public boolean delete(java.lang.String bank_card_11_digits) {
         SearchMap map =  new SearchMap();
         map.add( "bank_card_11_digits",bank_card_11_digits);
        return getSqlSessionTemplate().delete("Mapper.spider_bank_card_location.delete", map) > 0;
    }

    @Override
    public SpiderBankCardLocationDo create(SpiderBankCardLocationDo record) {
         getSqlSessionTemplate().insert("Mapper.spider_bank_card_location.create" , record);
        return record;
    }

    @Override
    public boolean update(SpiderBankCardLocationDo record) {
        return getSqlSessionTemplate().update("Mapper.spider_bank_card_location.update", record) > 0;
    }

    @Override
    public PageList<SpiderBankCardLocationDo>  listByPage(PageQuery pageQuery, Integer count){
         SearchMap map =  new SearchMap();
         map.add("startIndex",pageQuery.getStartIndex());
         map.add("pageSize", pageQuery.getPageSize());
         if( count == null || count.intValue() == 0 ) {
             count = (Integer)getSqlSessionTemplate().selectOne("Mapper.spider_bank_card_location.listByPageCount",map);
         }
         List<SpiderBankCardLocationDo>  list = Collections.emptyList();
         if( count != null && count.intValue() > 0 ) {
             list = getSqlSessionTemplate().selectList("Mapper.spider_bank_card_location.listByPage",map);
          }
         Paginator paginator =  new Paginator(pageQuery.getPageSize(), count == null ? 0 : count);
         paginator.setPage(pageQuery.getPageNum());
         PageList<SpiderBankCardLocationDo> result =  new PageList<SpiderBankCardLocationDo>(list);
         result.setPaginator(paginator);
         return result;
     }

    @Override
    public List<String> listById(String lastId,int limit) {
        SearchMap map =  new SearchMap();
        map.add( "id",lastId);
        map.add( "limit",limit);
        return getSqlSessionTemplate().selectList("Mapper.spider_bank_card_location.listById",map);
    }
 }
