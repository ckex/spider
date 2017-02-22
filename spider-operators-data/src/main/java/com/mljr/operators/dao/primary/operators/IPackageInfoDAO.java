package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.entity.model.operators.PackageInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IPackageInfoDAO {

    int insertSelective(PackageInfo record);

    PackageInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PackageInfo record);

}