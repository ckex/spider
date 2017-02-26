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
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo, Long> implements IUserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    protected BaseMapper<UserInfo, Long> getMapper() {
        return userInfoMapper;
    }

    @Override
    public UserInfo selectUniqUser(String mobile, String idcard) {
        if (StringUtils.isBlank(mobile) && StringUtils.isBlank(idcard)) {
            return null;
        }
        return userInfoMapper.selectUniqUser(mobile, idcard);
    }

    @Override
    public UserInfo insertIgnore(UserInfo userInfo) {
        boolean flag = userInfoMapper.insertIgnore(userInfo) == 1;
        return flag ? userInfo : null;
    }
}
