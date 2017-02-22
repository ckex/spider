package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.entity.model.operators.BillInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BillInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BillInfo record);

    int insertSelective(BillInfo record);

    BillInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillInfo record);

    int updateByPrimaryKey(BillInfo record);
}