package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.entity.model.operators.FlowInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FlowInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FlowInfo record);

    int insertSelective(FlowInfo record);

    FlowInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FlowInfo record);

    int updateByPrimaryKey(FlowInfo record);

    int insertByBatch(List<FlowInfo> list);
}