package com.mljr.operators.service.chinaunicom;

import com.mljr.operators.entity.dto.chinaunicom.PersonInfoDTO;
import com.mljr.operators.service.BaseTest;
import com.mljr.operators.service.primary.operators.IUserInfoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
public class ChinaUnicomStoreServiceImplTest extends BaseTest {

    @Autowired
    private IChinaUnicomService chinaUnicomService;

    @Autowired
    private IUserInfoService userInfoService;

    private String cookies = "";

    @Override
    public void testInit() {
        super.testInit();
        cookies = "";
        Assert.notNull(chinaUnicomService);
        Assert.notNull(userInfoService);
    }

    @Test
    public void testStoreUserInfo() {
        PersonInfoDTO personInfo = chinaUnicomService.queryUserInfo(cookies);
        if (null != personInfo) {


        }
    }

}
