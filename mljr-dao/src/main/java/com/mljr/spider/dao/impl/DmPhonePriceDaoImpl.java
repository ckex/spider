package com.mljr.spider.dao.impl;

import com.mljr.spider.dao.DmPhonePriceDao;
import com.mljr.spider.model.DmPhonePriceDo;
import common.page.util.PageList;
import common.page.util.PageQuery;
import common.page.util.Paginator;
import common.search.util.SearchMap;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository("dmPhonePriceDao")
public class DmPhonePriceDaoImpl extends AbstractBasicDao implements DmPhonePriceDao {

    @Override
    public DmPhonePriceDo load(Long id) {
         SearchMap map =  new SearchMap();
         map.add( "id",id);
        return (DmPhonePriceDo) getSqlSessionTemplate().selectOne("Mapper.dm_phone_price.load" , map);
    }

    @Override
    public boolean delete(Long id) {
         SearchMap map =  new SearchMap();
         map.add( "id",id);
        return getSqlSessionTemplate().delete("Mapper.dm_phone_price.delete", map) > 0;
    }

    @Override
    public DmPhonePriceDo create(DmPhonePriceDo record) {
         getSqlSessionTemplate().insert("Mapper.dm_phone_price.create" , record);
        return record;
    }

    @Override
    public boolean update(DmPhonePriceDo record) {
        return getSqlSessionTemplate().update("Mapper.dm_phone_price.update", record) > 0;
    }

    @Override
    public PageList<DmPhonePriceDo>  listByPage(PageQuery pageQuery, Integer count){
         SearchMap map =  new SearchMap();
         map.add("startIndex",pageQuery.getStartIndex());
         map.add("pageSize", pageQuery.getPageSize());
         if( count == null || count.intValue() == 0 ) {
             count = (Integer)getSqlSessionTemplate().selectOne("Mapper.dm_phone_price.listByPageCount",map);
         }
         List<DmPhonePriceDo>  list = Collections.emptyList();
         if( count != null && count.intValue() > 0 ) {
             list = getSqlSessionTemplate().selectList("Mapper.dm_phone_price.listByPage",map);
          }
         Paginator paginator =  new Paginator(pageQuery.getPageSize(), count == null ? 0 : count);
         paginator.setPage(pageQuery.getPageNum());
         PageList<DmPhonePriceDo> result =  new PageList<DmPhonePriceDo>(list);
         result.setPaginator(paginator);
         return result;
     }
}
