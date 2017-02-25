package com.mljr.operators.service.primary.operators;

import com.mljr.operators.entity.model.operators.FlowInfo;
import com.mljr.operators.service.primary.IBaseService;

import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
public interface IFlowInfoService extends IBaseService<FlowInfo, Long> {

    /**
     * 批量添加
     *
     * @param list 数据
     */
    void insertByBatch(List<FlowInfo> list);
}
