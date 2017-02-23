package com.mljr.operators.service.primary;

import com.mljr.operators.dao.BaseMapper;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
public abstract class BaseServiceImpl<T, PK> implements IBaseService<T, PK> {

    protected abstract BaseMapper<T, PK> getMapper();

    @Override
    public T save(T entity) {
        getMapper().insertSelective(entity);
        return entity;
    }

    @Override
    public boolean removeById(PK id) {
        return getMapper().deleteByPrimaryKey(id) >= 0 ? true : false;
    }

    @Override
    public boolean updateById(T entity) {
        return getMapper().updateByPrimaryKeySelective(entity) >= 0 ? true : false;
    }

    @Override
    public T getById(PK id) {
        return getMapper().selectByPrimaryKey(id);
    }
}
