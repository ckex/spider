package com.mljr.operators.service.primary.operators.impl;

import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.dao.primary.operators.FlowInfoMapper;
import com.mljr.operators.entity.model.operators.FlowInfo;
import com.mljr.operators.service.primary.BaseServiceImpl;
import com.mljr.operators.service.primary.operators.IFlowInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
@Service
public class FlowInfoServiceImpl extends BaseServiceImpl<FlowInfo, Long> implements IFlowInfoService {

    @Autowired
    private FlowInfoMapper flowInfoMapper;

    @Override
    protected BaseMapper<FlowInfo, Long> getMapper() {
        return flowInfoMapper;
    }
}
