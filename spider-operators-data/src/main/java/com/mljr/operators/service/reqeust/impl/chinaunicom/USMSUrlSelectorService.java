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

import java.util.List;

/**
 * @author gaoxi
 * @time 2017/3/2
 */
@Service
public class USMSUrlSelectorService extends AbstractRequestUrlSelectorService {

  @Override
  public List<RequestInfoDTO> getRequestUrl(RequestUrlDTO requestUrl) {
    List<RequestInfoDTO> list = Lists.newArrayList();
    getRecentMonth(requestUrl.getStartDate(), 5).forEach(datePair -> {
      String url = getUrl(datePair, 1);
      list.add(convert(requestUrl.getMobile(), requestUrl.getIdcard(), datePair, url));
    });
    return list;
  }

  @Override
  public OperatorsEnum getOperator() {
    return OperatorsEnum.CHINAUNICOM;
  }

  @Override
  protected OperatorsUrlEnum getOperatorsUrl() {
    return OperatorsUrlEnum.CHINA_UNICOM_SMS;
  }

  @Override
  protected String getPattern() {
    return DateUtil.PATTERN_yyyyMMdd;
  }

  private String getUrl(DatePair datePair, int pageNo) {
    return String.format(getOperatorsUrl().getUrl(), pageNo,
        datePair.getStartDate().replaceAll("-", ""), datePair.getEndDate().replaceAll("-", ""));
  }
}
