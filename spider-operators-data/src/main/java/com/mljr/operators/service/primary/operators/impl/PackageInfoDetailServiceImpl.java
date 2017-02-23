package com.mljr.operators.service.primary.operators.impl;

import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.dao.primary.operators.PackageInfoDetailMapper;
import com.mljr.operators.entity.model.operators.PackageInfoDetail;
import com.mljr.operators.service.primary.BaseServiceImpl;
import com.mljr.operators.service.primary.operators.IPackageInfoDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
@Service
public class PackageInfoDetailServiceImpl extends BaseServiceImpl<PackageInfoDetail, Long> implements IPackageInfoDetailService {

    @Autowired
    private PackageInfoDetailMapper packageInfoDetailMapper;

    @Override
    protected BaseMapper<PackageInfoDetail, Long> getMapper() {
        return packageInfoDetailMapper;
    }
}
