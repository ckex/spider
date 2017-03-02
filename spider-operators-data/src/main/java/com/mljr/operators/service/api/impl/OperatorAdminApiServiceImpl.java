package com.mljr.operators.service.api.impl;

import com.mljr.operators.common.constant.RequestInfoEnum;
import com.mljr.operators.convert.RequestInfoConvert;
import com.mljr.operators.entity.dto.operator.RequestInfoDTO;
import com.mljr.operators.entity.dto.operator.RequestUrlDTO;
import com.mljr.operators.entity.model.operators.RequestInfo;
import com.mljr.operators.service.api.IOperatorAdminApiService;
import com.mljr.operators.service.primary.operators.IRequestInfoService;
import com.mljr.operators.service.reqeust.IOperatorRequestUrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gaoxi
 * @time 2017/3/2
 */
@Service
public class OperatorAdminApiServiceImpl implements IOperatorAdminApiService {

  private static final Logger LOGGER = LoggerFactory.getLogger(OperatorAdminApiServiceImpl.class);

  @Autowired
  private IOperatorRequestUrlService operatorRequestUrlService;

  @Autowired
  private IRequestInfoService requestInfoService;

  @Override
  public boolean submitAcquisitionTasks(RequestUrlDTO requestUrlDTO) {
    try {
      List<RequestInfoDTO> list = operatorRequestUrlService.getAllUrlByOperator(requestUrlDTO);
      if (null != list && list.size() > 0) {
        List<RequestInfo> requestInfoList = RequestInfoConvert.convert(list, RequestInfoEnum.INIT);
        requestInfoService.insertByBatch(requestInfoList);
      }
      return true;
    } catch (Exception e) {
      LOGGER.error("notify operator request failure.params:{}", requestUrlDTO.toString(), e);
    }
    return false;
  }
}
