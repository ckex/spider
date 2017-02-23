package com.mljr.operators.service.primary.operators.impl;

import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.dao.primary.operators.UserInfoDetailMapper;
import com.mljr.operators.entity.model.operators.UserInfoDetail;
import com.mljr.operators.service.primary.BaseServiceImpl;
import com.mljr.operators.service.primary.operators.IUserInfoDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
@Service
public class UserInfoDetailServiceImpl extends BaseServiceImpl<UserInfoDetail,Long> implements IUserInfoDetailService {

    @Autowired
    private UserInfoDetailMapper userInfoDetailMapper;

    @Override
    protected BaseMapper<UserInfoDetail, Long> getMapper() {
        return userInfoDetailMapper;
    }
}
