package com.mljr.operators.service.chinaunicom.impl;

import com.mljr.operators.convert.ChinaUnicomConvert;
import com.mljr.operators.entity.dto.chinaunicom.BillDTO;
import com.mljr.operators.entity.dto.chinaunicom.CallDTO;
import com.mljr.operators.entity.model.operators.BillInfo;
import com.mljr.operators.entity.model.operators.CallInfo;
import com.mljr.operators.exception.ConvertException;
import com.mljr.operators.service.chinaunicom.IChinaUnicomStoreService;
import com.mljr.operators.service.primary.operators.IBillInfoService;
import com.mljr.operators.service.primary.operators.ICallInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
@Service
public class ChinaUnicomStoreServiceImpl implements IChinaUnicomStoreService {

    private static final Logger logger = LoggerFactory.getLogger(ChinaUnicomStoreServiceImpl.class);

    @Autowired
    private ICallInfoService callInfoService;

    @Autowired
    private IBillInfoService billInfoService;

    @Override
    public boolean saveCallInfo(Long userInfoDetailId, CallDTO callDTO) {
        boolean flag = false;
        try {
            List<CallInfo> list = ChinaUnicomConvert.convert(callDTO);
            if (!list.isEmpty()) {
                callInfoService.insertByBatch(userInfoDetailId, list);
            }
            flag = true;
        } catch (ConvertException e) {
            logger.error("get convert data failure.userInfoDetailId:{}", userInfoDetailId, e);
        } catch (RuntimeException e) {
            logger.error("save failure.userInfoDetailId:{}", userInfoDetailId, e);
        }
        return flag;
    }

    @Override
    public boolean saveBillInfo(Long userInfoDetailId, BillDTO billDTO) {
        boolean flag = false;
        try {
            List<BillInfo> list = ChinaUnicomConvert.convert(billDTO);
            if (!list.isEmpty()) {
//                billInfoService.insertByBatch(userInfoDetailId, list);
            }
            flag = true;
        } catch (ConvertException e) {
            logger.error("get convert data failure.userInfoDetailId:{}", userInfoDetailId, e);
        } catch (RuntimeException e) {
            logger.error("save failure.userInfoDetailId:{}", userInfoDetailId, e);
        }
        return flag;
    }
}
