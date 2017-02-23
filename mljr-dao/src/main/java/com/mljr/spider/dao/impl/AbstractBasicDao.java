package com.mljr.spider.dao.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractBasicDao extends SqlSessionDaoSupport {

  protected static transient Logger logger = LoggerFactory.getLogger(AbstractBasicDao.class);

  @Resource(name = "sqlSessionFactory")
  @Override
  public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
    super.setSqlSessionFactory(sqlSessionFactory);
  }

  protected SqlSession getSqlSessionTemplate() {
    return getSqlSession();
  }

}
