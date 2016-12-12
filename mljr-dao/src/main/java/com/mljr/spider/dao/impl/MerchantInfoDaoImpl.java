package com.mljr.spider.dao.impl;

import com.mljr.spider.dao.MerchantInfoDao;
import com.mljr.spider.dao.SpiderBankCardLocationDao;
import com.mljr.spider.dao.dynamic.datasource.DataSource;
import com.mljr.spider.model.MerchantInfoDo;
import com.mljr.spider.model.SpiderBankCardLocationDo;
import common.page.util.PageList;
import common.page.util.PageQuery;
import common.page.util.Paginator;
import common.search.util.SearchMap;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * @author ckex created 2016-12-11 10:37:07:007
 * @explain -
 */
@Repository("merchantInfoDao")
public class MerchantInfoDaoImpl extends AbstractBasicDao implements MerchantInfoDao {

    @Override
    public List<MerchantInfoDo> listById(String lastId, int limit) {
        SearchMap map =  new SearchMap();
        map.add( "id",lastId);
        map.add( "limit",limit);
        return getSqlSessionTemplate().selectList("Mapper.merchant_info.listById",map);
    }
 }
