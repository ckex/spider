package com.mljr.operators.service.primary;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
public interface IBaseService<T,PK> {

    /**
     * 添加数据
     *
     * @param entity
     * @return
     */
    T save(T entity);

    /**
     * 根据主键ID删除
     *
     * @param id 主键
     * @return
     */
    boolean removeById(PK id);

    /**
     * 根据主键ID修改
     *
     * @param entity
     * @return
     */
    boolean updateById(T entity);

    /**
     * 根据主键ID查询
     *
     * @param id 主键
     * @return
     */
    T getById(PK id);
}
