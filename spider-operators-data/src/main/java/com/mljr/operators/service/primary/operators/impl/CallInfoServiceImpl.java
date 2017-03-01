package com.mljr.operators.service.primary.operators.impl;

import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.dao.primary.operators.CallInfoMapper;
import com.mljr.operators.entity.model.operators.CallInfo;
import com.mljr.operators.service.primary.BaseServiceImpl;
import com.mljr.operators.service.primary.operators.ICallInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/22
 */
@Service
public class CallInfoServiceImpl extends BaseServiceImpl<CallInfo, Long> implements ICallInfoService {

    @Autowired
    private CallInfoMapper callInfoMapper;

    @Override
    protected BaseMapper<CallInfo, Long> getMapper() {
        return callInfoMapper;
    }

    @Override
    public void insertByBatch(Long userInfoId, List<CallInfo> list) {
        callInfoMapper.insertByBatch(userInfoId, list);
    }

    @Override
    public List<CallInfo> selectByUid(Long userInfoId) {
        return callInfoMapper.selectByUid(userInfoId);
    }
}
