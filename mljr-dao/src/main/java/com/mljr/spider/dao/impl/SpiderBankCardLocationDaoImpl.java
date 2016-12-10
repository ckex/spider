package com.mljr.spider.dao.impl;

import com.mljr.spider.dao.SpiderBankCardLocationDao;
import com.mljr.spider.dao.YyUserAddressBookDao;
import com.mljr.spider.model.SpiderBankCardLocationDo;
import common.page.util.PageList;
import common.page.util.PageQuery;
import common.page.util.Paginator;
import common.search.util.SearchMap;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository("spiderBankCardLocationDao")
public class SpiderBankCardLocationDaoImpl extends AbstractBasicDao implements SpiderBankCardLocationDao {

	@Override
	public List<String> listById(String lastId,int limit) {
		SearchMap map =  new SearchMap();
        map.add( "id",lastId);
        map.add( "limit",limit);
        return getSqlSessionTemplate().selectList("Mapper.spider_bank_card_location.listById",map);
	}
 }
