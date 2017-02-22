package com.mljr.operators.service;

import com.mljr.operators.SpringBootOperatorsMain;
import com.mljr.operators.entity.model.operators.UserInfo;
import com.mljr.operators.service.primary.operators.IUserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * @author gaoxi
 * @time 2017/2/22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootOperatorsMain.class)
public class UserInfoServiceImplTest {

    @Autowired
    private IUserInfoService userInfoService;

    @Test
    public void testQuery() {

        Assert.notNull(userInfoService);

        UserInfo userInfo = userInfoService.getByMobile("18521705531");

        System.out.println();
    }
}
