package com.mljr.operators.service.primary.operators.impl;

import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.dao.primary.operators.UserInfoMapper;
import com.mljr.operators.entity.model.operators.UserInfo;
import com.mljr.operators.service.primary.BaseServiceImpl;
import com.mljr.operators.service.primary.operators.IUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gaoxi
 * @time 2017/2/22
 */
@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo,Long> implements IUserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    protected BaseMapper<UserInfo, Long> getMapper() {
        return userInfoMapper;
    }

    @Override
    public UserInfo getByMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return null;
        }
        return userInfoMapper.getByMobile("18521705531");
    }
}
