package com.mljr.spider.dao.impl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.mljr.spider.dao.AbstractIMljrDaoTest;
import com.mljr.spider.model.SpiderBankCardLocationDo;

import java.util.List;

/**
 * @author ckex created 2016-12-11 10:37:07:007
 * @explain -
 */
public class SpiderBankCardLocationDaoImplTest extends AbstractIMljrDaoTest {

  @Before
  @Ignore
  public void testSetup() {
    // SpiderBankCardLocationDo record = new SpiderBankCardLocationDo();
    // record.setBank_card_11_digits(bank_card_11_digits);
    // record.setSellerId(sellerId);
    // record = spiderBankCardLocationDao.create(record);
  }

  @After
  @Ignore
  public void testTeardown() {
    // spiderBankCardLocationDao.delete(bank_card_11_digits,sellerId);
  }

  @Test
  public void testLoad() {
    SpiderBankCardLocationDo load = spiderBankCardLocationDao.load("10366991112");
    logger.info("result ===> " + load);
    // Assert.assertNotNull(load);
    // Assert.assertEquals("feiyingtest", load.getXxxx());
    // logger.debug(load.toString());
  }

  @Test
  @Ignore
  public void testUpdate() {
    // SpiderBankCardLocationDo record = new SpiderBankCardLocationDo();
    // record.setBank_card_11_digits(bank_card_11_digits);
    // record.setSellerId(sellerId);
    // record.setXxxx("hellofeiying");
    // spiderBankCardLocationDao.update(record);

    // SpiderBankCardLocationDo load =
    // spiderBankCardLocationDao.load(bank_card_11_digits,sellerId);
    // Assert.assertNotNull(load);
    // Assert.assertEquals("hellofeiying", load.getXxxx());
    // logger.debug(load.toString());
  }

  @Test
  @Ignore
  public void testListByPage() {
    // common.page.util.PageQuery pageQuery = new
    // common.page.util.PageQuery(0,10);
    // Integer count = null;
    // common.page.util.PageList<SpiderBankCardLocationDo> result =
    // spiderBankCardLocationDao.listByPage(sellerId,pageQuery,count);
    // Assert.assertNotNull(result);
    // for (SpiderBankCardLocationDo bean : result) {
    // logger.debug(bean.toString());
    // }
  }

  @Test
  public void testListFromDMById() throws Exception {
    List<String> list = spiderBankCardLocationDao.listFromDMById("0", 10);
    logger.info("result ===> " + list);
  }

}
