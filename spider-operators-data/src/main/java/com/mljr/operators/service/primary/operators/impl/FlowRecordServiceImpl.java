package com.mljr.operators.service.primary.operators.impl;

import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.dao.primary.operators.FlowRecordMapper;
import com.mljr.operators.entity.model.operators.FlowRecord;
import com.mljr.operators.service.primary.BaseServiceImpl;
import com.mljr.operators.service.primary.operators.IFlowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/26
 */
@Service
public class FlowRecordServiceImpl extends BaseServiceImpl<FlowRecord,Long> implements IFlowRecordService {

    @Autowired
    private FlowRecordMapper flowRecordMapper;

    @Override
    protected BaseMapper<FlowRecord, Long> getMapper() {
        return flowRecordMapper;
    }

    @Override
    public void insertByBatch(Long userInfoId, List<FlowRecord> list) {
        flowRecordMapper.insertByBatch(userInfoId,list);
    }
}
