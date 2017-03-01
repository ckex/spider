package com.mljr.operators.service.acquisition;

import com.mljr.operators.entity.dto.operator.RequestInfoDTO;

import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/28
 */
public interface IAcquisitionService {

  /**
   * 保存
   * 
   * @param list
   */
  List<RequestInfoDTO> saveRequestUrl(List<RequestInfoDTO> list);


}
