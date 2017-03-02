package com.mljr.operators.service.reqeust;

import com.mljr.operators.entity.dto.operator.RequestInfoDTO;
import com.mljr.operators.entity.dto.operator.RequestUrlDTO;

import java.util.List;

/**
 * @author gaoxi
 * @time 2017/3/2
 */
public interface IOperatorRequestUrlService {

  /**
   * 根据运营商获取请求URL
   * 
   * @param requestUrl
   * @return
   */
  List<RequestInfoDTO> getAllUrlByOperator(RequestUrlDTO requestUrl);

}
