package com.mljr.operators.service.primary.operators;

import com.mljr.operators.entity.model.operators.FlowRecord;
import com.mljr.operators.service.primary.IBaseService;

import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/26
 */
public interface IFlowRecordService extends IBaseService<FlowRecord,Long> {

    /**
     * 批量添加
     * @param userInfoId
     * @param list
     */
    void insertByBatch(Long userInfoId,List<FlowRecord> list);
}
