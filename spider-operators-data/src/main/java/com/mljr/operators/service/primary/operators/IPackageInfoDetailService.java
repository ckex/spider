package com.mljr.operators.service.primary.operators;

import com.mljr.operators.entity.model.operators.PackageInfoDetail;
import com.mljr.operators.service.primary.IBaseService;

import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
public interface IPackageInfoDetailService extends IBaseService<PackageInfoDetail, Long> {

    /**
     * 批量添加
     *
     * @param packageInfoId
     * @param list
     */
    void insertByBatch(Long packageInfoId, List<PackageInfoDetail> list);
}
