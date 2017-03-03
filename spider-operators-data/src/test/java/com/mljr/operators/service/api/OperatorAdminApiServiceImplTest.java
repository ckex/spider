package com.mljr.operators.service.api;

import com.mljr.operators.common.constant.OperatorsEnum;
import com.mljr.operators.common.constant.ProvinceEnum;
import com.mljr.operators.entity.dto.operator.RequestUrlDTO;
import com.mljr.operators.service.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * @author gaoxi
 * @time 2017/3/3
 */
public class OperatorAdminApiServiceImplTest extends BaseTest {

  @Autowired
  private IOperatorAdminApiService operatorAdminApiService;

  @Test
  public void testSubmitAcquisitionTasks() {
    RequestUrlDTO entity =
        new RequestUrlDTO("18521705531", "429", OperatorsEnum.CHINAUNICOM, ProvinceEnum.SH);
    boolean flag = operatorAdminApiService.submitAcquisitionTasks(entity);
    Assert.state(flag);
  }
}
