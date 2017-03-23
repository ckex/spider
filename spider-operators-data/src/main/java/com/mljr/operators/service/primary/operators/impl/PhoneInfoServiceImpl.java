package com.mljr.operators.service.primary.operators.impl;

import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.dao.primary.operators.PhoneInfoMapper;
import com.mljr.operators.entity.model.operators.PhoneInfo;
import com.mljr.operators.service.primary.BaseServiceImpl;
import com.mljr.operators.service.primary.operators.IPhoneInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by songchi on 17/3/15.
 */
@Service
public class PhoneInfoServiceImpl extends BaseServiceImpl<PhoneInfo, Long> implements IPhoneInfoService {

    @Autowired
    private PhoneInfoMapper phoneInfoMapper;

    @Override
    protected BaseMapper<PhoneInfo, Long> getMapper() {
        return phoneInfoMapper;
    }

    @Override
    public String selectByPhone(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return "";
        }
        if (mobile.startsWith("400")) {
            return "";
        }
        if (mobile.startsWith("0086")) {
            mobile = mobile.replaceFirst("0086", "");
        }
        if (mobile.length() == 11) {
            mobile = mobile.substring(0, 7);
            PhoneInfo phoneInfo = phoneInfoMapper.selectByPhone(mobile);
            if (phoneInfo != null && phoneInfo.getProvince() != null && phoneInfo.getCity() != null) {
                return phoneInfo.getCity();
            }
        }
        return "";
    }

    @Override
    public PhoneInfo queryByPhone(String mobile) {
        if (StringUtils.isBlank(mobile)) return null;
        mobile = mobile.length() >= 7 ? mobile.substring(0, 7) : mobile;
        return phoneInfoMapper.selectByPhone(mobile);
    }
}
