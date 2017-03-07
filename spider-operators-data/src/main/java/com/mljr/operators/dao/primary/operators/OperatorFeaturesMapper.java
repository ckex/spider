package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.entity.model.operators.OperatorFeatures;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OperatorFeaturesMapper extends BaseMapper<OperatorFeatures, Long> {
    OperatorFeatures selectUniqFeatures(@Param("provinceCode") String provinceCode,
                                        @Param("operatorType") String operatorType);

}