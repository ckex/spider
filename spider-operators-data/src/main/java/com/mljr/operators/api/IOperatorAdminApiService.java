package com.mljr.operators.api;

import com.mljr.operators.entity.dto.operator.RequestUrlDTO;

/**
 * @author gaoxi
 * @time 2017/3/2
 */
public interface IOperatorAdminApiService {

  /**
   * 通知后台请求
   * 
   * @param requestUrlDTO
   * @return
   */
  boolean notifyRequest(RequestUrlDTO requestUrlDTO);

}
