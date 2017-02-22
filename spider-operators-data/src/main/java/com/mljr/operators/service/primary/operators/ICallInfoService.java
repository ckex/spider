package com.mljr.operators.service.primary.operators;

import com.mljr.operators.entity.model.operators.CallInfo;

import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/22
 */
public interface ICallInfoService {

    /**
     * 批量插入
     *
     * @param userInfoId userInfoDetail.id
     * @param list       数据
     */
    void insertByBatch(Long userInfoId, List<CallInfo> list);
}
