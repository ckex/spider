package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.entity.model.operators.FlowInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IFlowInfoDAO {

    int insertSelective(FlowInfo record);

    FlowInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FlowInfo record);

}