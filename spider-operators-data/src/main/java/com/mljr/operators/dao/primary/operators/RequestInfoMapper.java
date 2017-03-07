package com.mljr.operators.dao.primary.operators;

import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.entity.model.operators.RequestInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Mapper
public interface RequestInfoMapper extends BaseMapper<RequestInfo, Long> {

  /**
   * 批量添加
   * 
   * @param list
   */
  void insertByBatch(List<RequestInfo> list);

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

  /**
   * 获取前一次请求的的日期
   * 
   * @param mobile 手机号
   * @param idcard 身份证
   * @return
   */
  Date getPerRequestDate(@Param("mobile") String mobile, @Param("idcard") String idcard);

  Set<Integer> checkState(@Param("mobile") String mobile, @Param("idcard") String idcard);

  /**
   * 批量修改状态
   * 
   * @param newStatus 状态
   * @param originStatus 修改状态
   * @param ids
   * @return
   */
  int batchUpdateStatusById(@Param("newStatus") int newStatus,
      @Param("originStatus") int originStatus, @Param("ids") List<Long>  ids);

  /**
   * 重试
   * 
   * @param operatorsType 运营商
   * @param status 状态
   * @return
   */
  List<RequestInfo> retry(@Param("operatorsType") String operatorsType,
      @Param("status") int status);

}
