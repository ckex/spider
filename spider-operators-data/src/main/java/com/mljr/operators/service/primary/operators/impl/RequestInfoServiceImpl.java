package com.mljr.operators.service.primary.operators.impl;

import com.mljr.operators.common.constant.RequestInfoEnum;
import com.mljr.operators.dao.BaseMapper;
import com.mljr.operators.dao.primary.operators.RequestInfoMapper;
import com.mljr.operators.entity.model.operators.RequestInfo;
import com.mljr.operators.service.primary.BaseServiceImpl;
import com.mljr.operators.service.primary.operators.IRequestInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author gaoxi
 * @time 2017/2/27
 */
@Service
public class RequestInfoServiceImpl extends BaseServiceImpl<RequestInfo, Long>
    implements IRequestInfoService {

  @Autowired
  private RequestInfoMapper requestInfoMapper;

  @Override
  protected BaseMapper<RequestInfo, Long> getMapper() {
    return requestInfoMapper;
  }

  @Override
  public List<RequestInfo> insertByBatch(List<RequestInfo> list) {
    requestInfoMapper.insertByBatch(list);
    return list;
  }

  @Override
  public RequestInfo getBySign(String sign) {
    return requestInfoMapper.getBySign(sign);
  }

  @Override
  public boolean updateStatusBySign(String sign, RequestInfoEnum newStatus,
      RequestInfoEnum originStatus) {
    return requestInfoMapper.updateStatusBySign(sign, newStatus.getIndex(),
        originStatus.getIndex()) == 1;
  }

  @Override
  public Date getPerRequestDate(String mobile, String idcard) {
    return requestInfoMapper.getPerRequestDate(mobile, idcard);
  }

  @Override
  public Set<Integer> checkState(String mobile, String idcard) {
    return requestInfoMapper.checkState(mobile,idcard);
  }
}
