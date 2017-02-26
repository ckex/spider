package com.mljr.operators.service.primary.operators;

import com.mljr.operators.entity.model.operators.UserInfo;
import com.mljr.operators.service.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * @author gaoxi
 * @time 2017/2/22
 */
public class UserInfoServiceImplTest extends BaseTest {

    @Autowired
    private IUserInfoService userInfoService;

    @Test
    public void testQuery() {

        Assert.notNull(userInfoService);

        UserInfo userInfo = userInfoService.selectUniqUser("18521705531","429*************34");

        System.out.println();
    }
}
