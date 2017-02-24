package com.mljr.operators.service.chinaunicom;

import com.mljr.operators.entity.dto.chinaunicom.BillDTO;
import com.mljr.operators.entity.dto.chinaunicom.CallDTO;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
public interface IChinaUnicomStoreService {

    /**
     * 保存流量信息
     *
     * @param userInfoDetailId 用户信息ID t_user_info_detail.id
     * @param callDTO          通话详情数据传输层
     * @return true:SUCC false:ERROR
     */
    boolean saveCallInfo(Long userInfoDetailId, CallDTO callDTO);

    /**
     * 保存账单信息
     *
     * @param userInfoDetailId 用户信息ID t_user_info_detail.id
     * @param billDTO          账单数据
     * @return
     */
    boolean saveBillInfo(Long userInfoDetailId, BillDTO billDTO);


}
