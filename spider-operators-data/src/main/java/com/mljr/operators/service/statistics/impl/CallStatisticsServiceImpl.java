package com.mljr.operators.service.statistics.impl;

import com.mljr.operators.dao.primary.statistics.CallStatisticsMapper;
import com.mljr.operators.entity.vo.statistics.CallStatisticsVO;
import com.mljr.operators.service.statistics.ICallStatisticsService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by songchi on 17/3/13.
 */
public class CallStatisticsServiceImpl implements ICallStatisticsService {
    @Autowired
    CallStatisticsMapper callStatisticsMapper;

    @Override
    public CallStatisticsVO selectTimeByCallType(@Param("userInfoId") long userInfoId,
                                                 @Param("callType") String callType) {
        return callStatisticsMapper.selectTimeByCallType(userInfoId, callType);
    }
}
