package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.entity.model.operators.UserInfoDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserInfoDetail record);

    int insertSelective(UserInfoDetail record);

    UserInfoDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserInfoDetail record);

    int updateByPrimaryKey(UserInfoDetail record);
}