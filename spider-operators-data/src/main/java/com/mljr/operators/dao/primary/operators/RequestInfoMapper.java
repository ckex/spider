package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.entity.model.operators.RequestInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RequestInfoMapper extends BaseMapper<RequestInfo, Long> {

  /**
   * 批量添加
   * 
   * @param list
   */
  void insertByBatch(@Param("list") List<RequestInfo> list);

  /**
   * 根据Sign查询
   * 
   * @param sign 签名
   * @return
   */
  RequestInfo getBySign(@Param("sign") String sign);

  /**
   * 修改状态
   * 
   * @param sign sign
   * @param status 修改状态
   * @param originStatus 原始状态
   */
  int updateStatusBySign(@Param("sign") String sign, @Param("status") int status,
      @Param("originStatus") int originStatus);

}
