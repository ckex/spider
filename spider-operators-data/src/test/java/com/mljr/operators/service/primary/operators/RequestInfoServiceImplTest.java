package com.mljr.operators.service.primary.operators;

import com.google.common.collect.Lists;
import com.mljr.operators.common.constant.MQConstant;
import com.mljr.operators.common.constant.OperatorsEnum;
import com.mljr.operators.common.constant.ProvinceEnum;
import com.mljr.operators.common.constant.RequestInfoEnum;
import com.mljr.operators.common.utils.DateUtil;
import com.mljr.operators.common.utils.RabbitMQUtil;
import com.mljr.operators.entity.model.operators.RequestInfo;
import com.mljr.operators.service.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Date;
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
    entity.setMobile("18521705532");
    entity.setIdcard("429*************34");
    entity.setOperatorsType(OperatorsEnum.CHINAUNICOM.getCode());
    entity.setStartDate(DateUtil.defaultStringToDate("2016-12-01"));
    entity.setEndDate(DateUtil.defaultStringToDate("2016-12-31"));
    entity.setSign("HASH");
    entity.setStatus(RequestInfoEnum.INIT.getIndex());
    entity.setProvinceCode(ProvinceEnum.SH.getName());
    entity.setUrl("http://iservice.10010.com/e3/static/query/searchPerInfo/?_=%s");
    entity.setUrlType(1);

    RequestInfo entity2 = new RequestInfo();
    entity2.setMobile("18521705533");
    entity2.setIdcard("429*************34");
    entity2.setOperatorsType(OperatorsEnum.CHINAUNICOM.getCode());
    entity2.setStartDate(DateUtil.defaultStringToDate("2016-12-01"));
    entity2.setEndDate(DateUtil.defaultStringToDate("2016-12-31"));
    entity2.setSign("HASH1");
    entity2.setProvinceCode(ProvinceEnum.SH.getName());
    entity2.setStatus(RequestInfoEnum.INIT.getIndex());
    entity2.setUrl("http://iservice.10010.com/e3/static/query/searchPerInfo/?_=%s");
    entity2.setUrlType(1);

    list.add(entity);
    list.add(entity2);
  }

  @Test
  public void testInsertByBatch() {
    requestInfoService.insertByBatch(list);
    System.out.println();
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

  @Test
  public void testPerRequestDate() {
    Date date = requestInfoService.getPerRequestDate("18521705532", "429*************34");
    LocalDate localDate = DateUtil.dateToLocalDate(date);
    System.out.println();
  }

  @Test
  public void testSendMessage() {
    RequestInfo requestInfo = requestInfoService.getById(155L);
    if (requestInfo != null) {
      List<RequestInfo> list = Lists.newArrayList(requestInfo);
      RabbitMQUtil.sendMessage(MQConstant.OPERATOR_MQ_EXCHANGE,
          MQConstant.OPERATOR_MQ_CHINAUNICOM_ROUTING_KEY, list);
    }
    System.out.println();
  }

  @Test
  public void testCheckState() throws Exception {
    requestInfoService.checkState("15601662655","310226198702100190").forEach(System.out::println);

  }
}
