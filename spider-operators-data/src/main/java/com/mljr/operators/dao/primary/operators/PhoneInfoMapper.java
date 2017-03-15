package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.entity.model.operators.PhoneInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PhoneInfoMapper extends BaseMapper<PhoneInfo, Long> {
    PhoneInfo selectByPhone(@Param("phone") String phone);
}