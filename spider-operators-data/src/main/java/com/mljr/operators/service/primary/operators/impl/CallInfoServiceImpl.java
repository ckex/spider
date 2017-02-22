package com.mljr.operators.service.primary.operators.impl;

import com.mljr.operators.dao.primary.operators.CallInfoMapper;
import com.mljr.operators.entity.model.operators.CallInfo;
import com.mljr.operators.service.primary.operators.ICallInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/22
 */
@Service
public class CallInfoServiceImpl implements ICallInfoService {

    @Autowired
    private CallInfoMapper callInfoMapper;

    @Override
    public void insertByBatch(Long userInfoId, List<CallInfo> list) {
        callInfoMapper.insertByBatch(userInfoId, list);
    }
}
