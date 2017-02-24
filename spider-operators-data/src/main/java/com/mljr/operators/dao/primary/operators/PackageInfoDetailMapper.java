package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.entity.model.operators.PackageInfoDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PackageInfoDetailMapper extends BaseMapper<PackageInfoDetail, Long> {

    /**
     * 批量添加
     *
     * @param packageInfoId
     * @param list
     */
    void insertByBatch(@Param("packageInfoId") Long packageInfoId, @Param("list") List<PackageInfoDetail> list);

}