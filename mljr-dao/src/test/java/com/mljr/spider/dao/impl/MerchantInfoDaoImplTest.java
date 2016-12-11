package com.mljr.spider.dao.impl;

import com.mljr.spider.dao.AbstractIMljrDaoTest;
import com.mljr.spider.model.MerchantInfoDo;
import com.mljr.spider.model.SpiderBankCardLocationDo;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * @author ckex created 2016-12-11 10:37:07:007
 * @explain -
 */
public class MerchantInfoDaoImplTest extends AbstractIMljrDaoTest {

	@Test
	public void testListById() throws Exception {
		List<MerchantInfoDo> list = merchantInfoDao.listById("70000000000",10);
		logger.info("result ===> " + list);
	}
}
