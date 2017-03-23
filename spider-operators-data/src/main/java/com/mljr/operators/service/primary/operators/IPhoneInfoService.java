package com.mljr.operators.service.primary.operators;

import com.mljr.operators.entity.model.operators.PhoneInfo;
import com.mljr.operators.service.primary.IBaseService;


public interface IPhoneInfoService extends IBaseService<PhoneInfo, Long> {

    /**
     * 根据手机查询
     *
     * @param mobile 手机号
     * @return
     */
    String selectByPhone(String mobile);

    /**
     * 根据手机号查询
     *
     * @param mobile 手机号
     * @return
     */
    PhoneInfo queryByPhone(String mobile);
}
