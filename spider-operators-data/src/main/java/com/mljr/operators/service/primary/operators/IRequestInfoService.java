package com.mljr.operators.service.primary.operators;

import com.mljr.operators.common.constant.OperatorsEnum;
import com.mljr.operators.common.constant.RequestInfoEnum;
import com.mljr.operators.entity.model.operators.RequestInfo;
import com.mljr.operators.service.primary.IBaseService;

import java.util.Date;
import java.util.List;
import java.util.Set;

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
   * @return
   */
  Date getPerRequestDate(String mobile, String idcard);

  Set<Integer> checkState(String mobile, String idcard);

  /**
   * 批量修改状态
   * @param newStatus 状态
   * @param originStatus 修改状态
   * @param ids
   * @return
   */
  boolean batchUpdateStatusById(RequestInfoEnum newStatus,RequestInfoEnum originStatus,List<Long> ids);

  /**
   * 重试
   * @param operatorsEnum 运营商
   * @param status 状态
   * @return
   */
  List<RequestInfo> retry(OperatorsEnum operatorsEnum,RequestInfoEnum status);
}
