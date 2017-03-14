package com.mljr.operators.service.statistics;

import com.mljr.operators.common.constant.OperatorsEnum;
import com.mljr.operators.entity.vo.statistics.CallNumberStatisticsVO;
import com.mljr.operators.entity.vo.statistics.call.ByAddressVO;
import com.mljr.operators.entity.vo.statistics.call.ByMonthVO;
import com.mljr.operators.entity.vo.statistics.call.MaxMinDateVO;

import java.util.List;

/**
 * Created by songchi on 17/3/13.
 */
public interface ICallStatisticsService {

  /**
   * 根据联系人电话统计
   *
   * @param userInfoId userInfoId
   * @param operatorsEnum 运营商类型
   * @return
   */
  List<CallNumberStatisticsVO> getStatisticsByNumber(long userInfoId, OperatorsEnum operatorsEnum);

  List<MaxMinDateVO> selectMaxMinDate(long userInfoId);

  List<ByMonthVO> selectByMonth(long userInfoId);

  List<ByAddressVO> selectByAddress(long userInfoId);

  /**
   * 根据法定假日统计电话
   * 
   * @param userInfoId userInfoId
   * @return
   */
  List<CallNumberStatisticsVO> getStatisticsByHoliday(long userInfoId);

}
