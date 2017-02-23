package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.entity.model.operators.BillInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BillInfoMapper extends BaseMapper<BillInfo, Long> {

}