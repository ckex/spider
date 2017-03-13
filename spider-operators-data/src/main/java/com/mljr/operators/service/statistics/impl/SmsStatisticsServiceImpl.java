package com.mljr.operators.service.statistics.impl;

import com.mljr.operators.common.constant.SmsTypeEnum;
import com.mljr.operators.common.utils.DateUtil;
import com.mljr.operators.dao.primary.statistics.SmsStatisticsMapper;
import com.mljr.operators.entity.vo.statistics.SmsStatisticsVO;
import com.mljr.operators.service.statistics.ISmsStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by songchi on 17/3/13.
 */
@Service
public class SmsStatisticsServiceImpl implements ISmsStatisticsService {

  @Autowired
  private SmsStatisticsMapper smsStatisticsMapper;

  @Override
  public List<SmsStatisticsVO> getTimeBySmsType(long userInfoId, SmsTypeEnum smsTypeEnum) {
    String smsType = null;
    if (smsTypeEnum != SmsTypeEnum.ALL) {
      smsType = smsTypeEnum.getDesc();
    }
    List<SmsStatisticsVO> list = smsStatisticsMapper.getTimeBySmsType(userInfoId, smsType);
    if (null != list && list.size() > 0) {
      list.forEach(entity -> {
        entity.setMaxDateTimeStr(
            DateUtil.dateToString(entity.getMaxDateTime(), DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss));
        entity.setMinDateTimeStr(
            DateUtil.dateToString(entity.getMinDateTime(), DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss));
      });
    }
    return list;
  }
}
