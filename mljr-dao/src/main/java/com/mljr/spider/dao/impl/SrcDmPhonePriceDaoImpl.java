package com.mljr.spider.dao.impl;

import com.mljr.spider.dao.SrcDmPhonePriceDao;
import com.mljr.spider.model.SrcDmPhonePriceDo;
import common.page.util.PageList;
import common.page.util.PageQuery;
import common.page.util.Paginator;
import common.search.util.SearchMap;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository("srcDmPhonePriceDao")
public class SrcDmPhonePriceDaoImpl extends AbstractBasicDao implements SrcDmPhonePriceDao {

    @Override
    public SrcDmPhonePriceDo load(String itemUrl) {
         SearchMap map =  new SearchMap();
         map.add( "itemUrl",itemUrl);
        return (SrcDmPhonePriceDo) getSqlSessionTemplate().selectOne("Mapper.src_dm_phone_price.load" , map);
    }

    @Override
    public boolean delete(Long id) {
         SearchMap map =  new SearchMap();
         map.add( "id",id);
        return getSqlSessionTemplate().delete("Mapper.src_dm_phone_price.delete", map) > 0;
    }

    @Override
    public SrcDmPhonePriceDo create(SrcDmPhonePriceDo record) {
         getSqlSessionTemplate().insert("Mapper.src_dm_phone_price.create" , record);
        return record;
    }

    @Override
    public boolean update(SrcDmPhonePriceDo record) {
        return getSqlSessionTemplate().update("Mapper.src_dm_phone_price.update", record) > 0;
    }

    @Override
    public PageList<SrcDmPhonePriceDo>  listByPage(PageQuery pageQuery, Integer count){
         SearchMap map =  new SearchMap();
         map.add("startIndex",pageQuery.getStartIndex());
         map.add("pageSize", pageQuery.getPageSize());
         if( count == null || count.intValue() == 0 ) {
             count = (Integer)getSqlSessionTemplate().selectOne("Mapper.src_dm_phone_price.listByPageCount",map);
         }
         List<SrcDmPhonePriceDo>  list = Collections.emptyList();
         if( count != null && count.intValue() > 0 ) {
             list = getSqlSessionTemplate().selectList("Mapper.src_dm_phone_price.listByPage",map);
          }
         Paginator paginator =  new Paginator(pageQuery.getPageSize(), count == null ? 0 : count);
         paginator.setPage(pageQuery.getPageNum());
         PageList<SrcDmPhonePriceDo> result =  new PageList<SrcDmPhonePriceDo>(list);
         result.setPaginator(paginator);
         return result;
     }
}
