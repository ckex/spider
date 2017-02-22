package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.entity.model.operators.BillInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IBillInfoDAO {

    int insertSelective(BillInfo record);

    BillInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillInfo record);
}