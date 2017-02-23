package com.mljr.spider.dao.impl;

import com.mljr.spider.dao.AbstractIMljrDaoTest;
import com.mljr.spider.model.DmPhonePriceDo;
import common.page.util.PageQuery;
import org.junit.Test;

import java.util.Date;

/**
 * Created by songchi on 17/2/7.
 */
public class DmPhonePriceDaoImplTest extends AbstractIMljrDaoTest {
  @Test
  public void dummy() {
    DmPhonePriceDo dm = new DmPhonePriceDo();
    dm.setBrand("OPPO");
    dm.setCreateDate(new Date());
    dm.setPrice(2799.00F);
    dm.setTitle("shouji");
    dm.setWebsite("JD");
    dmPhonePriceDao.create(dm);
  }

  @Test
  public void testName() {
    dmPhonePriceDao.load(999L);
  }

  @Test
  public void listByPage() {
    dmPhonePriceDao.listByPage(new PageQuery(0, 10), null);
  }

  @Test
  public void listByPage2() {
    srcDmPhonePriceDao.listByPage(new PageQuery(0, 10), null);
  }
}
