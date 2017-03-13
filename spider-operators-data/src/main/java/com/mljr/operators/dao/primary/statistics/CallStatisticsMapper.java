package com.mljr.operators.dao.primary.statistics;

import com.mljr.operators.entity.vo.statistics.CallNumberStatisticsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.mljr.operators.entity.vo.statistics.CallStatisticsVO;


/**
 * Created by songchi on 17/3/13.
 */
public interface CallStatisticsMapper {

  /**
   * 根据联系人电话统计
   *
   * @param userInfoId
   * @param nowDate 当前时间
   * @return
   */
  List<CallNumberStatisticsVO> getStatisticsByNumber(@Param("userInfoId") long userInfoId,
      @Param("nowDate") String nowDate);


  List<CallStatisticsVO.MaxMinDate> selectMaxMinDate(@Param("userInfoId") long userInfo);

  List selectByMonth(@Param("userInfoId") long userInfo);

  List selectByAddress(@Param("userInfoId") long userInfo);



}
