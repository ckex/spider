package com.mljr.spider.dao.impl;

import com.mljr.spider.dao.AbstractIMljrDaoTest;
import com.mljr.spider.dao.YyUserAddressBookHistoryDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.mljr.spider.model.YyUserAddressBookHistoryDo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ckex created 2016-11-29 16:14:48:048
 * @explain -
 */
public class YyUserAddressBookHistoryDaoImplTest extends AbstractIMljrDaoTest {

    @Before
    public void testSetup() {
       // YyUserAddressBookHistoryDo record = new YyUserAddressBookHistoryDo();
       // record.setId(id);
       // record.setSellerId(sellerId);
       // record = yyUserAddressBookHistoryDao.create(record);
    }
    @After
    public void testTeardown() {
       // yyUserAddressBookHistoryDao.delete(id,sellerId);
    }
    @Test
    public void testLoad() {
       // YyUserAddressBookHistoryDo load = yyUserAddressBookHistoryDao.load(id,sellerId);
       // Assert.assertNotNull(load);
       // Assert.assertEquals("feiyingtest", load.getXxxx());
       // logger.debug(load.toString());
    }
    @Test
    public void testUpdate() {
       // YyUserAddressBookHistoryDo record = new YyUserAddressBookHistoryDo();
       // record.setId(id);
       // record.setSellerId(sellerId);
       // record.setXxxx("hellofeiying");
       // yyUserAddressBookHistoryDao.update(record);

       // YyUserAddressBookHistoryDo load = yyUserAddressBookHistoryDao.load(id,sellerId);
       // Assert.assertNotNull(load);
       // Assert.assertEquals("hellofeiying", load.getXxxx());
       // logger.debug(load.toString());
    }
    @Test
    public void testListByPage() {
        // common.page.util.PageQuery pageQuery = new common.page.util.PageQuery(0,10);
        // Integer count = null;
        // common.page.util.PageList<YyUserAddressBookHistoryDo> result =         yyUserAddressBookHistoryDao.listByPage(sellerId,pageQuery,count);
        // Assert.assertNotNull(result);
         // for (YyUserAddressBookHistoryDo bean : result) {
         //     logger.debug(bean.toString());
         // }
    }

    @Test
    public void testListById() throws Exception {
        System.out.println(yyUserAddressBookHistoryDao.listById(0,7));
    }
}
