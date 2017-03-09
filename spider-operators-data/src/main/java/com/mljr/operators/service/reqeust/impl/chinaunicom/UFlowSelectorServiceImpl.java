package com.mljr.operators.service.reqeust.impl.chinaunicom;

import com.google.common.collect.Lists;
import com.mljr.operators.common.constant.OperatorsEnum;
import com.mljr.operators.common.constant.OperatorsUrlEnum;
import com.mljr.operators.common.utils.DateUtil;
import com.mljr.operators.entity.chinamobile.DatePair;
import com.mljr.operators.entity.dto.operator.RequestInfoDTO;
import com.mljr.operators.entity.dto.operator.RequestUrlDTO;
import com.mljr.operators.service.reqeust.AbstractRequestUrlSelectorService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author gaoxi
 * @time 2017/3/2
 */
@Service
public class UFlowSelectorServiceImpl extends AbstractRequestUrlSelectorService {

  @Override
  public List<RequestInfoDTO> getRequestUrl(RequestUrlDTO requestUrl, Date filterDate) {
    List<RequestInfoDTO> list = Lists.newArrayList();
//    List<DatePair> dayDatePairs = Lists.newArrayList();
//    getRecentMonth(requestUrl.getStartDate(), 3).forEach(datePair -> {
//      LocalDate startDate =
//          DateUtil.stringToLocalDate(datePair.getStartDate(), DateUtil.PATTERN_yyyy_MM_dd);
//      LocalDate endDate =
//          DateUtil.stringToLocalDate(datePair.getEndDate(), DateUtil.PATTERN_yyyy_MM_dd);
//      dayDatePairs.addAll(getEachDay(startDate, endDate));
//    });
//    dayDatePairs.forEach(datePair -> {
//      if (null == filterDate || null != filterDate && null != filterUrl(filterDate, datePair)) {
//        String url = getUrl(datePair, 1);
//        list.add(convert(requestUrl.getMobile(), requestUrl.getIdcard(), datePair, url));
//      }
//    });
    return list;
  }

  @Override
  public OperatorsEnum getOperator() {
    return OperatorsEnum.CHINAUNICOM;
  }

  @Override
  protected OperatorsUrlEnum getOperatorsUrl() {
    return OperatorsUrlEnum.CHINA_UNICOM_FLOW;
  }

  private String getUrl(DatePair datePair, int pageNo) {
    return String.format(getOperatorsUrl().getUrl(), pageNo, datePair.getStartDate(),
        datePair.getEndDate());
  }
}
