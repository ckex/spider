package com.mljr.operators.handler;

import com.mljr.operators.entity.dto.operator.AcquisitionDTO;
import com.mljr.operators.entity.dto.operator.RequestInfoDTO;

import java.util.List;

/**
 * @author gaoxi
 * @time 2017/3/1
 */
public interface IAcquisitionHandler {

  /**
   * 创建请求Url
   *
   * @param acquisition 数据传输层
   * @return
   */
  List<RequestInfoDTO> builderRequestUrl(AcquisitionDTO acquisition);
}
