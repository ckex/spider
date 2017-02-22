package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.entity.model.operators.PackageInfoDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IPackageInfoDetailDAO {

    int insertSelective(PackageInfoDetail record);

    PackageInfoDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PackageInfoDetail record);

}