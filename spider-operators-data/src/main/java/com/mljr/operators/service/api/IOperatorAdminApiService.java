package com.mljr.operators.service.api;

import com.mljr.operators.entity.dto.operator.RequestUrlDTO;

/**
 * @author gaoxi
 * @time 2017/3/2
 */
public interface IOperatorAdminApiService {

  /**
   * 提交采集任务
   * 
   * @param requestUrlDTO
   * @return
   */
  boolean submitAcquisitionTasks(RequestUrlDTO requestUrlDTO);

}
