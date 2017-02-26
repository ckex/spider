package com.mljr.spider.dao.impl;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.mljr.spider.dao.AbstractIMljrDaoTest;
import com.mljr.spider.model.YyUserAddressBookDo;

/**
 * @author ckex created 2016-11-29 16:14:48:048
 * @explain -
 */
public class YyUserAddressBookDaoImplTest extends AbstractIMljrDaoTest {

  @Before
  @Ignore
  public void testSetup() {
    // YyUserAddressBookDo record = new YyUserAddressBookDo();
    // record.setId(id);
    // record.setSellerId(sellerId);
    // record = yyUserAddressBookDao.create(record);
  }

  @After
  @Ignore
  public void testTeardown() {
    // yyUserAddressBookDao.delete(id,sellerId);
  }

  @Test
  public void testLoad() {
    long id = 1;
    List<YyUserAddressBookDo> list = yyUserAddressBookDao.listById(id, 10);
    for (YyUserAddressBookDo yyUserAddressBookDo : list) {
      logger.debug(yyUserAddressBookDo.toString());
    }
    // Assert.assertNotNull(load);
    // Assert.assertEquals("feiyingtest", load.getXxxx());
    // logger.debug(load.toString());
  }

  @Test
  @Ignore
  public void testUpdate() {
    // YyUserAddressBookDo record = new YyUserAddressBookDo();
    // record.setId(id);
    // record.setSellerId(sellerId);
    // record.setXxxx("hellofeiying");
    // yyUserAddressBookDao.update(record);

    // YyUserAddressBookDo load = yyUserAddressBookDao.load(id,sellerId);
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
    // common.page.util.PageList<YyUserAddressBookDo> result =
    // yyUserAddressBookDao.listByPage(sellerId,pageQuery,count);
    // Assert.assertNotNull(result);
    // for (YyUserAddressBookDo bean : result) {
    // logger.debug(bean.toString());
    // }
  }
}
