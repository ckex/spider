package com.mljr.operators.service.primary.operators.impl;

import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.dao.primary.operators.PhoneInfoMapper;
import com.mljr.operators.entity.model.operators.PhoneInfo;
import com.mljr.operators.service.primary.BaseServiceImpl;
import com.mljr.operators.service.primary.operators.IPhoneInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by songchi on 17/3/15.
 */
public class PhoneInfoServiceImpl extends BaseServiceImpl<PhoneInfo, Long> implements IPhoneInfoService {

    @Autowired
    private PhoneInfoMapper phoneInfoMapper;

    @Override
    protected BaseMapper<PhoneInfo, Long> getMapper() {
        return phoneInfoMapper;
    }
}
