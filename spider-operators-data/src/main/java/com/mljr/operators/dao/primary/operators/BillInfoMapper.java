package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.entity.model.operators.BillInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BillInfoMapper extends BaseMapper<BillInfo, Long> {

    /**
     * 批量添加
     *
     * @param userInfoId
     * @param list
     */
    void insertByBatch(@Param("userInfoId") Long userInfoId, @Param("list") List<BillInfo> list);

}