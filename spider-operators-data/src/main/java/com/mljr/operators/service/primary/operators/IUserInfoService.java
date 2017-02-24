package com.mljr.operators.service.primary.operators;

import com.mljr.operators.entity.model.operators.UserInfo;
import com.mljr.operators.service.primary.IBaseService;

/**
 * @author gaoxi
 * @time 2017/2/22
 */
public interface IUserInfoService extends IBaseService<UserInfo, Long> {

    /**
     * 获取用户信息
     *
     * @param mobile 手机
     * @param idcard 身份证
     * @return
     */
    UserInfo selectUniqUser(String mobile, String idcard);

    /**
     * 插入 数据库存在就不新增，不存在插入
     *
     * @param userInfo
     * @return
     */
    UserInfo insertIgnore(UserInfo userInfo);


}
