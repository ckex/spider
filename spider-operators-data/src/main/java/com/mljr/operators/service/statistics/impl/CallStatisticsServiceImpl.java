package com.mljr.operators.service.statistics.impl;

import com.google.common.collect.Maps;
import com.mljr.operators.common.constant.OperatorsEnum;
import com.mljr.operators.common.utils.DateUtil;
import com.mljr.operators.dao.primary.statistics.CallStatisticsMapper;
import com.mljr.operators.entity.vo.statistics.CallNumberStatisticsVO;
import com.mljr.operators.entity.vo.statistics.call.ByAddressVO;
import com.mljr.operators.entity.vo.statistics.call.ByMonthVO;
import com.mljr.operators.entity.vo.statistics.call.MaxMinDateVO;
import com.mljr.operators.service.statistics.ICallStatisticsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by songchi on 17/3/13.
 */
@Service
public class CallStatisticsServiceImpl implements ICallStatisticsService {

  @Autowired
  private CallStatisticsMapper callStatisticsMapper;

  @Override
  public List<CallNumberStatisticsVO> getStatisticsByNumber(long userInfoId,
      OperatorsEnum operatorsEnum) {
    String nowDate =
        DateUtil.dateToString(LocalDateTime.now(), DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
    List<CallNumberStatisticsVO> statisticsList = null;
    switch (operatorsEnum) {
      case CHINAUNICOM:
        statisticsList = callStatisticsMapper.getChinaUnicomStatisticsByNumber(userInfoId, nowDate);
        break;
      case CHINAMOBILE:
        statisticsList = callStatisticsMapper.getChinaMobileStatisticsByNumber(userInfoId, nowDate);
        break;
      case CHINATELECOM:
        break;
    }
    List<CallNumberStatisticsVO> holidayList = getStatisticsByHoliday(userInfoId);
    Map<String, Integer> holidayMap = listToMap(holidayList);
    if (null != statisticsList && statisticsList.size() > 0) {
      statisticsList.forEach(entity -> {
        entity.setThreeMonthAgoCount(entity.getTotalCount() - entity.getLastThreeMonthCount());
        entity.setLastThreeMonthCount(
            entity.getLastThreeMonthCount() - entity.getLastOneMonthCount());
        entity.setLastOneMonthCount(entity.getLastOneMonthCount() - entity.getLastWeekCount());
        if (holidayMap.containsKey(entity.getCallNumber())) {
          entity.setHolidayCount(holidayMap.get(entity.getCallNumber()));
        }
        if (StringUtils.isBlank(entity.getCallNumberAddress())) {
          entity.setCallNumberAddress("全国");
        }
      });
    }
    return statisticsList;
  }

  @Override
  public List<MaxMinDateVO> selectMaxMinDate(long userInfoId) {
    return callStatisticsMapper.selectMaxMinDate(userInfoId);
  }

  @Override
  public List<ByMonthVO> selectByMonth(long userInfoId) {
    return callStatisticsMapper.selectByMonth(userInfoId);
  }

  @Override
  public List<ByAddressVO> selectByAddress(long userInfoId) {
    return callStatisticsMapper.selectByAddress(userInfoId);
  }

  @Override
  public List<CallNumberStatisticsVO> getStatisticsByHoliday(long userInfoId) {
    return callStatisticsMapper.getStatisticsByHoliday(userInfoId);
  }

  private Map<String, Integer> listToMap(List<CallNumberStatisticsVO> list) {
    Map<String, Integer> map = Maps.newHashMap();
    if (null != list && list.size() > 0) {
      list.forEach(entity -> map.put(entity.getCallNumber(), entity.getHolidayCount()));
    }
    return map;
  }
}
