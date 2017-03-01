package com.mljr.operators.service.primary.operators;

import com.mljr.operators.common.constant.OperatorsEnum;
import com.mljr.operators.common.constant.RequestInfoEnum;
import com.mljr.operators.entity.model.operators.RequestInfo;
import com.mljr.operators.service.primary.IBaseService;

import java.util.Date;
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
  List<RequestInfo> insertByBatch(List<RequestInfo> list);

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

  /**
   * 获取前一次请求的的日期
   * 
   * @param mobile 手机号
   * @param idcard 身份证
   * @param operatorsEnum 运营商
   * @return
   */
  Date getPerRequestDate(String mobile, String idcard, OperatorsEnum operatorsEnum);
}
