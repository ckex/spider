package com.mljr.operators.service.primary.operators;

import com.mljr.operators.entity.model.operators.UserInfo;

/**
 * @author gaoxi
 * @time 2017/2/22
 */
public interface IUserInfoService {

    /**
     * 根据手机查询
     *
     * @param mobile 手机号
     * @return
     */
    UserInfo getByMobile(String mobile);

}
