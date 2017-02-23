package com.mljr.operators.service.primary.operators;

import com.mljr.operators.entity.model.operators.UserInfo;
import com.mljr.operators.service.primary.IBaseService;

/**
 * @author gaoxi
 * @time 2017/2/22
 */
public interface IUserInfoService extends IBaseService<UserInfo,Long>{

    /**
     * 根据手机查询
     *
     * @param mobile 手机号
     * @return
     */
    UserInfo getByMobile(String mobile);

}
