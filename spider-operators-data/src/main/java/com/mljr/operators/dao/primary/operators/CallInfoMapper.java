package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.entity.model.operators.CallInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CallInfoMapper extends BaseMapper<CallInfo, Long> {

    void insertByBatch(@Param("userInfoId") Long userInfoId, @Param("list") List<CallInfo> list);
}