package com.mljr.operators.service.primary.operators.impl;

import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.dao.primary.operators.OperatorFeaturesMapper;
import com.mljr.operators.entity.model.operators.OperatorFeatures;
import com.mljr.operators.service.primary.BaseServiceImpl;
import com.mljr.operators.service.primary.operators.IOperatorFeaturesService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by songchi on 17/3/7.
 */
public class OperatorFeaturesServiceImpl extends BaseServiceImpl<OperatorFeatures, Long>
        implements IOperatorFeaturesService {
    @Autowired
    private OperatorFeaturesMapper operatorFeaturesMapper;

    @Override
    protected BaseMapper<OperatorFeatures, Long> getMapper() {
        return operatorFeaturesMapper;
    }

    @Override
    public OperatorFeatures selectUniqFeatures(String provinceCode, String operatorType) {
        return operatorFeaturesMapper.selectUniqFeatures(provinceCode, operatorType);
    }
}
