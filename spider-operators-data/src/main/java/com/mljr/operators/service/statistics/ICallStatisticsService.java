package com.mljr.operators.service.statistics;

import com.mljr.operators.entity.vo.statistics.CallNumberStatisticsVO;

import java.util.List;

import com.mljr.operators.entity.vo.statistics.CallStatisticsVO;
import org.apache.ibatis.annotations.Param;

/**
 * Created by songchi on 17/3/13.
 */
public interface ICallStatisticsService {

  /**
   * 根据联系人电话统计
   *
   * @param userInfoId
   * @return
   */
  List<CallNumberStatisticsVO> getStatisticsByNumber(long userInfoId);


    CallStatisticsVO selectTimeByCallType(@Param("userInfoId") long userInfoId,
                                          @Param("callType") String callType);
}
