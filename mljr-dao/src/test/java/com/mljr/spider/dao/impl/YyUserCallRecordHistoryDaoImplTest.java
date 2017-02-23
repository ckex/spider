package com.mljr.spider.dao.impl;

import com.mljr.spider.dao.AbstractIMljrDaoTest;
import com.mljr.spider.dao.YyUserCallRecordHistoryDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.mljr.spider.model.YyUserCallRecordHistoryDo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ckex created 2016-11-29 16:14:49:049
 * @explain -
 */
public class YyUserCallRecordHistoryDaoImplTest extends AbstractIMljrDaoTest {

  @Before
  public void testSetup() {
    // YyUserCallRecordHistoryDo record = new YyUserCallRecordHistoryDo();
    // record.setId(id);
    // record.setSellerId(sellerId);
    // record = yyUserCallRecordHistoryDao.create(record);
  }

  @After
  public void testTeardown() {
    // yyUserCallRecordHistoryDao.delete(id,sellerId);
  }

  @Test
  public void testLoad() {
    // YyUserCallRecordHistoryDo load = yyUserCallRecordHistoryDao.load(id,sellerId);
    // Assert.assertNotNull(load);
    // Assert.assertEquals("feiyingtest", load.getXxxx());
    // logger.debug(load.toString());
  }

  @Test
  public void testUpdate() {
    // YyUserCallRecordHistoryDo record = new YyUserCallRecordHistoryDo();
    // record.setId(id);
    // record.setSellerId(sellerId);
    // record.setXxxx("hellofeiying");
    // yyUserCallRecordHistoryDao.update(record);

    // YyUserCallRecordHistoryDo load = yyUserCallRecordHistoryDao.load(id,sellerId);
    // Assert.assertNotNull(load);
    // Assert.assertEquals("hellofeiying", load.getXxxx());
    // logger.debug(load.toString());
  }

  @Test
  public void testListByPage() {
    // common.page.util.PageQuery pageQuery = new common.page.util.PageQuery(0,10);
    // Integer count = null;
    // common.page.util.PageList<YyUserCallRecordHistoryDo> result =
    // yyUserCallRecordHistoryDao.listByPage(sellerId,pageQuery,count);
    // Assert.assertNotNull(result);
    // for (YyUserCallRecordHistoryDo bean : result) {
    // logger.debug(bean.toString());
    // }
  }

  @Test
  public void testListById() throws Exception {
    System.out.println(yyUserCallRecordHistoryDao.listById(0, 5));

  }
}
