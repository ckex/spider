package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.entity.model.operators.CallInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CallInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CallInfo record);

    int insertSelective(CallInfo record);

    CallInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CallInfo record);

    int updateByPrimaryKey(CallInfo record);

    void insertByBatch(@Param("userInfoId") Long userInfoId, @Param("list") List<CallInfo> list);
}