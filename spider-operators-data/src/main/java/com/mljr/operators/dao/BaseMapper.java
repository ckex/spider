package com.mljr.operators.dao;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
public interface BaseMapper<T, PK> {

    int deleteByPrimaryKey(PK id);

    int insert(T entity);

    int insertSelective(T entity);

    T selectByPrimaryKey(PK id);

    int updateByPrimaryKeySelective(T entity);

    int updateByPrimaryKey(T entity);
}
