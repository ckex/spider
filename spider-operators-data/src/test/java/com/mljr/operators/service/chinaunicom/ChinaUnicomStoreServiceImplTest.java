package com.mljr.operators.service.chinaunicom;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.mljr.operators.entity.dto.chinaunicom.CallDTO;
import com.mljr.operators.service.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.io.File;
import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
public class ChinaUnicomStoreServiceImplTest extends BaseTest {

    @Autowired
    private IChinaUnicomStoreService chinaUnicomStoreService;

    private static final String PATH_PREFIX = "chinaunicom";

    private static final String CALL_INFO_FILE = "callInfo.json";

    @Override
    public void testInit() {
        super.testInit();
        Assert.notNull(chinaUnicomStoreService);
    }

    @Test
    public void testStoreCallInfo() {
        String pathUrl = Joiner.on(File.separator).join(PATH_PREFIX, CALL_INFO_FILE);
        String path = getClass().getClassLoader().getResource(pathUrl).getPath();
        List<String> list = readFileLines(path);
        if (null != list) {
            CallDTO callDTO = JSON.parseObject(list.get(0), CallDTO.class);
            boolean flag = chinaUnicomStoreService.saveCallInfo(1L, callDTO);
            Assert.state(flag);
        }
    }

    @Test
    public void testSaveBillInfo() {

    }

    @Test
    public void testSaveFlowInfo() {

    }

}
