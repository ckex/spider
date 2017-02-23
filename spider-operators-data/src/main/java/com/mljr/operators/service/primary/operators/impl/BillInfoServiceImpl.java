package com.mljr.operators.service.primary.operators.impl;

import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.dao.primary.operators.BillInfoMapper;
import com.mljr.operators.entity.model.operators.BillInfo;
import com.mljr.operators.service.primary.BaseServiceImpl;
import com.mljr.operators.service.primary.operators.IBillInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
@Service
public class BillInfoServiceImpl extends BaseServiceImpl<BillInfo, Long> implements IBillInfoService {

    @Autowired
    private BillInfoMapper billInfoMapper;

    @Override
    protected BaseMapper<BillInfo, Long> getMapper() {
        return billInfoMapper;
    }
}
