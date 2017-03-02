package com.mljr.operators.service.reqeust.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mljr.operators.entity.dto.operator.RequestInfoDTO;
import com.mljr.operators.entity.dto.operator.RequestUrlDTO;
import com.mljr.operators.service.reqeust.IOperatorRequestUrlService;
import com.mljr.operators.service.reqeust.IRequestUrlSelectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author gaoxi
 * @time 2017/3/2
 */
@Service
public class OperatorRequestUrlServiceImpl implements IOperatorRequestUrlService {

  private ImmutableList<IRequestUrlSelectorService> requestUrlSelectorServices;

  @Autowired
  public OperatorRequestUrlServiceImpl(Map<String, IRequestUrlSelectorService> serviceMap) {
    Preconditions.checkNotNull(serviceMap, "Selector service is null");
    requestUrlSelectorServices = ImmutableList.copyOf(serviceMap.values());
  }

  @Override
  public List<RequestInfoDTO> getAllUrlByOperator(RequestUrlDTO requestUrl) {
    List<RequestInfoDTO> list = Lists.newArrayList();
    requestUrlSelectorServices.forEach(requestUrlSelectorService -> {
      if (requestUrlSelectorService.getOperator() == requestUrl.getOperators()
          && requestUrlSelectorService.availableProvince().contains(requestUrl.getProvince())) {
        list.addAll(requestUrlSelectorService.getRequestUrl(requestUrl));
      }
    });
    return list;
  }
}
