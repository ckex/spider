package com.mljr.operators.service.statistics;

import com.mljr.operators.entity.vo.statistics.CallNumberStatisticsVO;

import java.util.List;

import com.mljr.operators.entity.vo.statistics.CallStatisticsVO;
import com.mljr.operators.entity.vo.statistics.call.ByAddressVO;
import com.mljr.operators.entity.vo.statistics.call.ByMonthVO;
import com.mljr.operators.entity.vo.statistics.call.MaxMinDateVO;
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

  List<MaxMinDateVO> selectMaxMinDate(long userInfoId);

  List<ByMonthVO> selectByMonth( long userInfoId);

  List<ByAddressVO> selectByAddress(long userInfoId);
}
