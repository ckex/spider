package com.mljr.operators.service.statistics.impl;

import com.mljr.operators.common.utils.DateUtil;
import com.mljr.operators.dao.primary.statistics.CallStatisticsMapper;
import com.mljr.operators.entity.vo.statistics.CallNumberStatisticsVO;
import com.mljr.operators.service.statistics.ICallStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import com.mljr.operators.dao.primary.statistics.CallStatisticsMapper;
import com.mljr.operators.entity.vo.statistics.CallStatisticsVO;
import com.mljr.operators.service.statistics.ICallStatisticsService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by songchi on 17/3/13.
 */
@Service
public class CallStatisticsServiceImpl implements ICallStatisticsService {

  @Autowired
  private CallStatisticsMapper callStatisticsMapper;

  @Override
  public List<CallNumberStatisticsVO> getStatisticsByNumber(long userInfoId) {
    String nowDate =
        DateUtil.dateToString(LocalDateTime.now(), DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
    List<CallNumberStatisticsVO> statisticsList =
        callStatisticsMapper.getStatisticsByNumber(userInfoId, nowDate);
    if (null != statisticsList && statisticsList.size() > 0) {
      statisticsList.forEach(entity -> {
        entity.setThreeMonthAgoCount(entity.getTotalCount() - entity.getLastThreeMonthCount());
        entity.setLastThreeMonthCount(
            entity.getLastThreeMonthCount() - entity.getLastOneMonthCount());
        entity.setLastOneMonthCount(entity.getLastOneMonthCount() - entity.getLastWeekCount());
      });
    }
    return statisticsList;
  }

  @Override
  public CallStatisticsVO selectTimeByCallType(@Param("userInfoId") long userInfoId,
      @Param("callType") String callType) {
    return callStatisticsMapper.selectTimeByCallType(userInfoId, callType);
  }
}
