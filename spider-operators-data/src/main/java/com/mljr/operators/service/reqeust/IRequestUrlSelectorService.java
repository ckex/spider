package com.mljr.operators.service.reqeust;

import com.mljr.operators.common.constant.OperatorsEnum;
import com.mljr.operators.common.constant.ProvinceEnum;
import com.mljr.operators.entity.dto.operator.RequestInfoDTO;
import com.mljr.operators.entity.dto.operator.RequestUrlDTO;

import java.util.Date;
import java.util.List;

/**
 * @author gaoxi
 * @time 2017/3/2
 */
public interface IRequestUrlSelectorService {

  /**
   * 获取可用省份
   * 
   * @return
   */
  List<ProvinceEnum> availableProvince();

  /**
   * 获取请求URL
   * 
   * @param requestUrl 请求URL参数
   * @param filterDate 过滤日期
   * @return
   */
  List<RequestInfoDTO> getRequestUrl(RequestUrlDTO requestUrl, Date filterDate);

  /**
   * 运营商
   * 
   * @return
   */
  OperatorsEnum getOperator();

}
