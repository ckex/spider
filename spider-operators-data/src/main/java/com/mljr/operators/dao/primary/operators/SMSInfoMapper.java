package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.entity.model.operators.SMSInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SMSInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SMSInfo record);

    int insertSelective(SMSInfo record);

    SMSInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SMSInfo record);

    int updateByPrimaryKey(SMSInfo record);

    int insertByBatch(List<SMSInfo> list);
}