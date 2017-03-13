package com.mljr.operators.service.statistics;

import com.mljr.operators.entity.vo.statistics.CallNumberStatisticsVO;
import com.mljr.operators.service.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by gaoxi
 *
 * @Time: 2017/3/13 下午4:44
 */
public class CallStatisticsServiceImplTest extends BaseTest {

    @Autowired
    private ICallStatisticsService callStatisticsService;

    @Test
    public void testGetStatisticsByNumber(){
        List<CallNumberStatisticsVO> list = callStatisticsService.getStatisticsByNumber(10);

        System.out.println();
    }
}
