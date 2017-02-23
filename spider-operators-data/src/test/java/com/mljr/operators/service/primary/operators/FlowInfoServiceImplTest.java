package com.mljr.operators.service.primary.operators;

import com.mljr.operators.entity.model.operators.FlowInfo;
import com.mljr.operators.service.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
public class FlowInfoServiceImplTest extends BaseTest {

    @Autowired
    private IFlowInfoService flowInfoService;

    @Override
    public void testInit() {
        super.testInit();
        org.springframework.util.Assert.notNull(flowInfoService);
    }

    @Test
    public void testQuery() {

        FlowInfo flowInfo=flowInfoService.getById(1L);


        System.out.println();


    }
}
