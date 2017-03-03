package com.mljr.operators.service.reqeust.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mljr.operators.common.utils.DateUtil;
import com.mljr.operators.entity.dto.operator.RequestInfoDTO;
import com.mljr.operators.entity.dto.operator.RequestUrlDTO;
import com.mljr.operators.service.primary.operators.IRequestInfoService;
import com.mljr.operators.service.reqeust.IOperatorRequestUrlService;
import com.mljr.operators.service.reqeust.IRequestUrlSelectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author gaoxi
 * @time 2017/3/2
 */
@Service
public class OperatorRequestUrlServiceImpl implements IOperatorRequestUrlService {

  private static final Logger LOGGER = LoggerFactory.getLogger(OperatorRequestUrlServiceImpl.class);

  private ImmutableList<IRequestUrlSelectorService> requestUrlSelectorServices;

  @Autowired
  private IRequestInfoService requestInfoService;

  @Autowired
  public OperatorRequestUrlServiceImpl(Map<String, IRequestUrlSelectorService> serviceMap) {
    Preconditions.checkNotNull(serviceMap, "Selector service is null");
    requestUrlSelectorServices = ImmutableList.copyOf(serviceMap.values());
  }

  @Override
  public List<RequestInfoDTO> getAllUrlByOperator(RequestUrlDTO requestUrl) {
    Date date = DateUtil.localDateToDate(LocalDate.of(2017,2,27));
//        requestInfoService.getPerRequestDate(requestUrl.getMobile(), requestUrl.getIdcard());
    List<RequestInfoDTO> list = Lists.newArrayList();
    requestUrlSelectorServices.forEach(requestUrlSelectorService -> {
      if (requestUrlSelectorService.getOperator() == requestUrl.getOperators()
          && requestUrlSelectorService.availableProvince().contains(requestUrl.getProvince())) {
        list.addAll(requestUrlSelectorService.getRequestUrl(requestUrl, date));
      }
    });
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("get all url.requestUrl:{},data:{}", requestUrl.toString(), JSON.toJSON(list));
    }
    return list;
  }
}
