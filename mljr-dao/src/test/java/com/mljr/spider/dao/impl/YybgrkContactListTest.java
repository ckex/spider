package com.mljr.spider.dao.impl;

import com.mljr.spider.dao.AbstractIMljrDaoTest;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class YybgrkContactListTest extends AbstractIMljrDaoTest {

  @Test
  public void testListById() throws Exception {
    List<Map> list = yybgrkContactListDao.listById(1L, 10);
    for (Map map : list) {
      System.out.println(map.toString());
    }
  }

  @Test
  public void testHistoryListById() throws Exception {
    List<Map> list = yybgrkContactListDao.listHistoryById(1L, 10);
    for (Map map : list) {
      System.out.println(map.toString());
    }
  }


}
