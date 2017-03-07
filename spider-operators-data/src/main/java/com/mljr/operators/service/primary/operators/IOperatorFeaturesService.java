package com.mljr.operators.service.primary.operators;

import com.mljr.operators.entity.model.operators.OperatorFeatures;
import com.mljr.operators.service.primary.IBaseService;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
public interface IOperatorFeaturesService extends IBaseService<OperatorFeatures, Long> {

    OperatorFeatures selectUniqFeatures(String provinceCode, String operatorType);

}
