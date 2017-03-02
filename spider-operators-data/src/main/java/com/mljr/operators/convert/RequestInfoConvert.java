package com.mljr.operators.convert;

import com.google.common.collect.Lists;
import com.mljr.operators.common.constant.OperatorsUrlEnum;
import com.mljr.operators.common.constant.RequestInfoEnum;
import com.mljr.operators.common.utils.DateUtil;
import com.mljr.operators.entity.dto.operator.RequestInfoDTO;
import com.mljr.operators.entity.model.operators.RequestInfo;

import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/28
 */
public class RequestInfoConvert {

  private RequestInfoConvert() {}

  public static List<RequestInfo> convert(List<RequestInfoDTO> dtoList) {
    List<RequestInfo> list = Lists.newArrayList();
    dtoList.forEach(requestInfoDTO -> list.add(convert(requestInfoDTO, RequestInfoEnum.INIT)));
    return list;
  }

  public static RequestInfo convert(RequestInfoDTO dto, RequestInfoEnum requestInfoEnum) {
    RequestInfo entity = new RequestInfo();
//    OperatorsUrlEnum urlEnum = OperatorsUrlEnum.indexOf(dto.getUrlType().intValue());
//    entity.setMobile(dto.getMobile());
//    entity.setIdcard(dto.getIdcard());
//    entity.setOperatorsType(dto.getOperatorsType());
//    entity.setProvinceCode(urlEnum.getProvince().name());
//    entity.setUrlType(dto.getUrlType());
//    entity.setUrl(dto.getUrl());
//    entity.setStartDate(
//        DateUtil.stringToDate(dto.getStartDate(), DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss));
//    entity
//        .setEndDate(DateUtil.stringToDate(dto.getEndDate(), DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss));
//    entity.setSign(dto.getSign());
//    entity.setStatus(requestInfoEnum.getIndex());
    return entity;
  }


}
