package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.entity.model.operators.SMSInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SMSInfoMapper extends BaseMapper<SMSInfo, Long> {

    int insertByBatch(List<SMSInfo> list);
}