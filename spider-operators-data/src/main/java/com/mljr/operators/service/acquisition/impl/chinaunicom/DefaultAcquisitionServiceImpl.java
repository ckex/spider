package com.mljr.operators.service.acquisition.impl.chinaunicom;

import com.mljr.operators.entity.dto.operator.RequestInfoDTO;
import com.mljr.operators.service.acquisition.impl.AbstractAcquisitionServiceImpl;
import com.mljr.operators.service.primary.operators.IRequestInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author gaoxi
 * @time 2017/3/1
 */
public class DefaultAcquisitionServiceImpl extends AbstractAcquisitionServiceImpl {

  @Autowired
  private IRequestInfoService requestInfoService;

  @Override
  public List<RequestInfoDTO> saveRequestUrl(List<RequestInfoDTO> list) {

    return null;
  }
}
