package com.mljr.operators.service.request;

import com.mljr.operators.common.constant.OperatorsEnum;
import com.mljr.operators.common.constant.ProvinceEnum;
import com.mljr.operators.entity.dto.operator.RequestInfoDTO;
import com.mljr.operators.entity.dto.operator.RequestUrlDTO;
import com.mljr.operators.service.BaseTest;
import com.mljr.operators.service.reqeust.IOperatorRequestUrlService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author gaoxi
 * @time 2017/3/2
 */
public class RequestUrlSelectorServiceImplTest extends BaseTest {

  @Autowired
  private IOperatorRequestUrlService requestUrlChooseService;

  @Test
  public void testSelectorService() {
    RequestUrlDTO requestUrl=new RequestUrlDTO("18521705531","idcard",OperatorsEnum.CHINATELECOM,ProvinceEnum.SH);
    List<RequestInfoDTO> list =requestUrlChooseService.getAllUrlByOperator(requestUrl);

    System.out.println();
  }
}
