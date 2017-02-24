package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.entity.model.operators.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo, Long> {

    /**
     * 插入 数据库存在就不新增，不存在插入
     *
     * @param userInfo
     * @return
     */
    int insertIgnore(UserInfo userInfo);

    /**
     * 获取用户信息
     *
     * @param mobile 手机
     * @param idcard 身份证
     * @return
     */
    UserInfo selectUniqUser(@Param("mobile") String mobile, @Param("idcard") String idcard);
}