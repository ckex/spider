package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.entity.model.operators.SMSInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ISMSInfoDAO {

    int insertSelective(SMSInfo record);

    SMSInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SMSInfo record);

}