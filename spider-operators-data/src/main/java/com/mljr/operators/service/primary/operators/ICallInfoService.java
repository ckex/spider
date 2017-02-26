package com.mljr.operators.service.primary.operators;

import com.mljr.operators.entity.model.operators.CallInfo;
import com.mljr.operators.service.primary.IBaseService;

import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/22
 */
public interface ICallInfoService extends IBaseService<CallInfo, Long> {

    /**
     * 批量插入
     *
     * @param userInfoId userInfoDetail.id
     * @param list       数据
     */
    void insertByBatch(Long userInfoId, List<CallInfo> list);
}
