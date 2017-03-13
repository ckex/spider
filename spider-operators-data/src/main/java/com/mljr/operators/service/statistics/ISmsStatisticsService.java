package com.mljr.operators.service.statistics;

import com.mljr.operators.common.constant.SmsTypeEnum;
import com.mljr.operators.entity.vo.statistics.SmsStatisticsVO;

import java.util.List;

/**
 * Created by songchi on 17/3/13.
 */
public interface ISmsStatisticsService {

  /**
   * 根据短信接收类型获取最早和最近时间
   * 
   * @param userInfoId
   * @param smsTypeEnum 类型
   * @return
   */
  List<SmsStatisticsVO> getTimeBySmsType(long userInfoId, SmsTypeEnum smsTypeEnum);


}
