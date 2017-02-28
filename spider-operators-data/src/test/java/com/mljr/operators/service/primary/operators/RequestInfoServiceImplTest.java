package com.mljr.operators.service.primary.operators;

import com.google.common.collect.Lists;
import com.mljr.operators.common.constant.OperatorsEnum;
import com.mljr.operators.common.constant.RequestInfoEnum;
import com.mljr.operators.entity.model.operators.RequestInfo;
import com.mljr.operators.service.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/27
 */
public class RequestInfoServiceImplTest extends BaseTest {

  @Autowired
  private IRequestInfoService requestInfoService;

  private List<RequestInfo> list = Lists.newArrayList();

  @Override
  public void testInit() {
    super.testInit();
    Assert.notNull(requestInfoService);
    RequestInfo entity = new RequestInfo();
    entity.setMobile("18521705531");
    entity.setIdcard("429*************34");
    entity.setOperatorsType(OperatorsEnum.CHINAUNICOM.getValue());
    entity.setAcquireDate("20161201");
    entity.setSign("HASH");
    entity.setStatus(RequestInfoEnum.INIT.getIndex());
    entity.setUrl("http://iservice.10010.com/e3/static/query/searchPerInfo/?_=%s");
    entity.setUrlType("1");
    list.add(entity);
    list.add(entity);
  }

  @Test
  public void testInsertByBatch() {
    requestInfoService.insertByBatch(list);
  }

  @Test
  public void testGetBySign() {
    RequestInfo entity = requestInfoService.getBySign("HASH");
    Assert.notNull(entity);
  }

  @Test
  public void testUpdateStatusBySign() {
    boolean flag = requestInfoService.updateStatusBySign("HASH", RequestInfoEnum.SUCCESS,
        RequestInfoEnum.INIT);
    Assert.state(flag);
  }

}
