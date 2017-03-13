package com.mljr.operators.service.statistics;

import com.mljr.operators.entity.vo.statistics.SmsStatisticsVO;
import com.mljr.operators.service.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by gaoxi
 *
 * @Time: 2017/3/13 下午12:01
 */
public class SmsStatisticsServiceImplTest extends BaseTest {

  @Autowired
  private ISmsStatisticsService smsStatisticsService;

    @Override
    public void testInit() {
        super.testInit();
        Assert.assertNotNull(smsStatisticsService);
    }

    @Test
  public void testGetTimeBySmsType() {

//      List<SmsStatisticsVO> list = smsStatisticsService.getTimeBySmsType(10);

        System.out.println();

  }
}
