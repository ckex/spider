package com.mljr.operators.service.primary.operators;

import com.mljr.operators.entity.model.operators.PhoneInfo;
import com.mljr.operators.service.primary.IBaseService;
import org.springframework.stereotype.Service;



public interface IPhoneInfoService extends IBaseService<PhoneInfo, Long> {

    String selectByPhone(String mobile);
}
