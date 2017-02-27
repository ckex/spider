package com.mljr.operators.service.primary.operators;

import com.mljr.operators.common.constant.RequestInfoEnum;
import com.mljr.operators.entity.model.operators.RequestInfo;
import com.mljr.operators.service.primary.IBaseService;

import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/27
 */
public interface IRequestInfoService extends IBaseService<RequestInfo, Long> {

  /**
   * 批量添加
   * 
   * @param list
   */
  void insertByBatch(List<RequestInfo> list);

  /**
   * 根据sign获取数据
   * 
   * @param sign sign
   * @return
   */
  RequestInfo getBySign(String sign);

  /**
   * 修改状态
   * 
   * @param sign sign
   * @param newStatus 修改状态
   * @param originStatus 原始状态
   */
  boolean updateStatusBySign(String sign, RequestInfoEnum newStatus, RequestInfoEnum originStatus);
}
