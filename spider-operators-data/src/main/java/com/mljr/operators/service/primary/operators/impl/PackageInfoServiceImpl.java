package com.mljr.operators.service.primary.operators.impl;

import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.dao.primary.operators.PackageInfoMapper;
import com.mljr.operators.entity.model.operators.PackageInfo;
import com.mljr.operators.service.primary.BaseServiceImpl;
import com.mljr.operators.service.primary.operators.IPackageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
@Service
public class PackageInfoServiceImpl extends BaseServiceImpl<PackageInfo,Long> implements IPackageInfoService {

    @Autowired
    private PackageInfoMapper packageInfoMapper;

    @Override
    protected BaseMapper<PackageInfo, Long> getMapper() {
        return packageInfoMapper;
    }
}
