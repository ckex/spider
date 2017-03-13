package com.mljr.operators.dao.statistics;

import com.mljr.operators.entity.vo.statistics.SmsStatisticsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by songchi on 17/3/13.
 */
@Mapper
public interface SmsStatisticsMapper {

    List<SmsStatisticsVO> getTimeBySmsType(@Param("userInfoId") long userInfoId);
}
