package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.entity.model.operators.PackageInfo;

public interface PackageInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PackageInfo record);

    int insertSelective(PackageInfo record);

    PackageInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PackageInfo record);

    int updateByPrimaryKey(PackageInfo record);
}