package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.entity.model.operators.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IUserInfoDAO {

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserInfo record);

    UserInfo getByMobile(@Param("mobile") String mobile);
}