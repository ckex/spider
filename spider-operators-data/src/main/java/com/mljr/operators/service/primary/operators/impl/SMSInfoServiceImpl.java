package com.mljr.operators.service.primary.operators.impl;

import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.dao.primary.operators.SMSInfoMapper;
import com.mljr.operators.entity.model.operators.SMSInfo;
import com.mljr.operators.service.primary.BaseServiceImpl;
import com.mljr.operators.service.primary.operators.ISMSInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
@Service
public class SMSInfoServiceImpl extends BaseServiceImpl<SMSInfo,Long> implements ISMSInfoService {

    @Autowired
    private SMSInfoMapper smsInfoMapper;

    @Override
    protected BaseMapper<SMSInfo, Long> getMapper() {
        return smsInfoMapper;
    }

    @Override
    public void insertByBatch(List<SMSInfo> list) {
        smsInfoMapper.insertByBatch(list);
    }
}
