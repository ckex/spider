package com.mljr.operators.service.primary.operators;

import com.mljr.operators.entity.model.operators.BillInfo;
import com.mljr.operators.service.primary.IBaseService;

import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
public interface IBillInfoService extends IBaseService<BillInfo, Long> {

    /**
     * 批量添加
     *
     * @param userInfoId
     * @param list
     */
    void insertByBatch(Long userInfoId, List<BillInfo> list);

}
