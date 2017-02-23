package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.entity.model.operators.FlowInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FlowInfoMapper extends BaseMapper<FlowInfo, Long> {

}