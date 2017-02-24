package com.mljr.operators.service.primary.operators;

import com.mljr.operators.entity.model.operators.SMSInfo;
import com.mljr.operators.service.primary.IBaseService;

import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
public interface ISMSInfoService extends IBaseService<SMSInfo,Long> {
    void insertByBatch(List<SMSInfo> list);
}
