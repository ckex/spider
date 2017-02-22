package com.mljr.operators.service;

import com.google.common.collect.Lists;
import com.mljr.operators.entity.model.operators.CallInfo;
import com.mljr.operators.service.primary.operators.ICallInfoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/22
 */
public class CallInfoServiceImplTest extends BaseTest {

    @Autowired
    private ICallInfoService callInfoService;

    @Test
    public void testBatchInsert() {
        List<CallInfo> list = Lists.newArrayList();
        CallInfo c1=new CallInfo();
        c1.setCallDate(new Date());
        c1.setCallFee(BigDecimal.ONE);
        c1.setCallFee(BigDecimal.ONE);
        c1.setCallLocalAddress("test1");
        c1.setCallLongHour("1");
        c1.setCallNumber("18521705531");
        c1.setCallRemoteAddress("test1");
        c1.setCallType("1");
        c1.setLandType("1");
        list.add(c1);
        c1=null;
        c1=new CallInfo();
        c1.setCallDate(new Date());
        c1.setCallFee(BigDecimal.ZERO);
        c1.setCallFee(BigDecimal.ZERO);
        c1.setCallLocalAddress("test2");
        c1.setCallLongHour("2");
        c1.setCallNumber("18521705532");
        c1.setCallRemoteAddress("test2");
        c1.setCallType("2");
        c1.setLandType("2");
        list.add(c1);
        callInfoService.insertByBatch(1L,list);
    }

}
