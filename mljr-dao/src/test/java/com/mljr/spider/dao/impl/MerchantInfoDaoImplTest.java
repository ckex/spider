package com.mljr.spider.dao.impl;

import com.mljr.spider.dao.AbstractIMljrDaoTest;
import com.mljr.spider.model.MerchantInfoDo;
import com.mljr.spider.model.SpiderBankCardLocationDo;
import com.mljr.spider.model.YyUserAddressBookDo;

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
		List<MerchantInfoDo> list = merchantInfoDao.listById("70000000000", 10);
		logger.info("result ===> " + list);
		logger.info("---------------------------------------------------华丽分割线。");
		long id = 1;
		List<YyUserAddressBookDo> list2 = yyUserAddressBookDao.listById(id, 10);
		for (YyUserAddressBookDo yyUserAddressBookDo : list2) {
			logger.debug(yyUserAddressBookDo.toString());
		}
	}

	@Test
	public void testListById2() throws Exception {
		long id = 1;
		List<YyUserAddressBookDo> list2 = yyUserAddressBookDao.listById(id, 10);
		for (YyUserAddressBookDo yyUserAddressBookDo : list2) {
			logger.debug(yyUserAddressBookDo.toString());
		}
		logger.info("---------------------------------------------------华丽分割线。");
		List<MerchantInfoDo> list = merchantInfoDao.listById("70000000000", 10);
		logger.info("result ===> " + list);
	}

	@Test
	public void testListAddress() throws Exception {
		System.out.println(merchantInfoDao.listAddressById("000",10));

	}
}
