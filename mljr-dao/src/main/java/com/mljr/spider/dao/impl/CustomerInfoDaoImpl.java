package com.mljr.spider.dao.impl;

import com.mljr.spider.dao.CustomerInfoDao;
import common.search.util.SearchMap;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author ckex created 2016-12-11 10:37:07:007
 * @explain -
 */
@Repository("customerInfoDao")
public class CustomerInfoDaoImpl extends AbstractBasicDao implements CustomerInfoDao {

    @Override
    public List<Map> listById(String lastId, int limit) {
        SearchMap map =  new SearchMap();
        map.add( "id",lastId);
        map.add( "limit",limit);
        return getSqlSessionTemplate().selectList("Mapper.customer_info.listById",map);
    }

}
