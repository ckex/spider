package com.mljr.operators.service.chinaunicom;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.mljr.operators.entity.dto.chinaunicom.*;
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

    private static final String USER_INFO_FILE = "userInfo.json";

    private static final String CALL_INFO_FILE = "callInfo.json";

    private static final String SMS_INFO_FILE = "smsInfo.json";

    private static final String BILL_INFO_FILE = "billInfo.json";

    private static final String FLOW_DETAIL_FILE = "flowDetailInfo.json";

    private static final String FLOW_RECORD_FILE = "flowRecordInfo.json";

    private static final String REMAINING_INFO_FILE="remainingInfo.json";

    @Override
    public void testInit() {
        super.testInit();
        Assert.notNull(chinaUnicomStoreService);
    }

    @Test
    public void testSaveUserInfo() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setIdcard("429*************37");
        loginDTO.setProvinceCode("031");
        loginDTO.setMobile("18521705533");
        loginDTO.setPassword("123456");

        UserInfoDTO personInfo = parse(USER_INFO_FILE, UserInfoDTO.class);
        if (null != personInfo) {
            boolean flag = chinaUnicomStoreService.saveUserInfo(loginDTO, personInfo);
            Assert.state(flag);
        }
    }

    @Test
    public void testSavePackageInfo() {
        UserInfoDTO personInfo = parse(USER_INFO_FILE, UserInfoDTO.class);
        if (null != personInfo) {
            boolean flag = chinaUnicomStoreService.savePackageInfo(1L, personInfo);
            Assert.state(flag);
        }
    }

    @Test
    public void testSaveCallInfo() {
        CallDTO callDTO = parse(CALL_INFO_FILE, CallDTO.class);
        if (null != callDTO) {
            boolean flag = chinaUnicomStoreService.saveCallInfo(1L, callDTO);
            Assert.state(flag);
        }
    }

    @Test
    public void testSaveBillInfo() {
        BillDTO billDTO = parse(BILL_INFO_FILE, BillDTO.class);
        if (null != billDTO) {
            boolean flag = chinaUnicomStoreService.saveBillInfo(1L, billDTO);
            Assert.state(flag);
        }
    }

    @Test
    public void testSaveSmsInfo() {
        SMSDTO smsdto = parse(SMS_INFO_FILE, SMSDTO.class);
        if (null != smsdto) {
            boolean flag = chinaUnicomStoreService.saveSmsInfo(2L, smsdto);
            Assert.state(flag);
        }
    }


    @Test
    public void testSaveFlowDetailInfo() {
        FlowDetailDTO flowDetailDTO = parse(FLOW_DETAIL_FILE, FlowDetailDTO.class);
        if (null != flowDetailDTO) {
            boolean flag = chinaUnicomStoreService.saveFlowInfo(3L, flowDetailDTO);
            Assert.state(flag);
        }
    }

    @Test
    public void testSaveFlowRecordInfo() {
        FlowRecordDTO flowRecordDTO = parse(FLOW_RECORD_FILE, FlowRecordDTO.class);
        if (null != flowRecordDTO) {
            boolean flag = chinaUnicomStoreService.saveFlowRecordInfo(3L, flowRecordDTO);
            Assert.state(flag);
        }
    }

    @Test
    public void testSaveRemainingInfo(){
        RemainingDTO remainingDTO=parse(REMAINING_INFO_FILE,RemainingDTO.class);
        Assert.notNull(remainingDTO);
    }

    private <T> T parse(String fileName, Class<T> c) {
        String pathUrl = Joiner.on(File.separator).join(PATH_PREFIX, fileName);
        String path = getClass().getClassLoader().getResource(pathUrl).getPath();
        List<String> list = readFileLines(path);
        if (null != list) {
            T t = JSON.parseObject(list.get(0), c);
            return t;
        }
        return null;
    }

}
