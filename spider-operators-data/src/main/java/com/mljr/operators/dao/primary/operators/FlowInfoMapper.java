package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.entity.model.operators.FlowInfo;

public interface FlowInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FlowInfo record);

    int insertSelective(FlowInfo record);

    FlowInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FlowInfo record);

    int updateByPrimaryKey(FlowInfo record);
}