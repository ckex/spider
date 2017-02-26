package com.mljr.operators.service.chinaunicom;

import com.mljr.operators.entity.dto.chinaunicom.*;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
public interface IChinaUnicomStoreService {

    /**
     * 保存用户信息
     *
     * @param loginDTO
     * @param userInfoDTO 数据传输层
     * @return
     */
    boolean saveUserInfo(LoginDTO loginDTO, UserInfoDTO userInfoDTO);

    /**
     * 保存套餐信息
     *
     * @param userInfoId t_user_info.id
     * @param personInfo 套餐信息
     * @return
     */
    boolean savePackageInfo(Long userInfoId, UserInfoDTO personInfo);

    /**
     * 保存流量信息
     *
     * @param userInfoId t_user_info.id
     * @param callDTO    通话详情数据传输层
     * @return true:SUCC false:ERROR
     */
    boolean saveCallInfo(Long userInfoId, CallDTO callDTO);

    /**
     * 保存账单信息
     *
     * @param userInfoId t_user_info.id
     * @param billDTO    账单数据
     * @return
     */
    boolean saveBillInfo(Long userInfoId, BillDTO billDTO);

    /**
     * 保存短信信息
     *
     * @param userInfoId t_user_info.id
     * @param smsdto     短信数据
     * @return
     */
    boolean saveSmsInfo(Long userInfoId, SMSDTO smsdto);

    /**
     * @param userInfoId    t_user_info.id
     * @param flowDetailDTO 流量详情数据
     * @return
     */
    boolean saveFlowInfo(Long userInfoId, FlowDetailDTO flowDetailDTO);

    /**
     * @param userInfoId    t_user_info.id
     * @param flowRecordDTO 流量记录数据
     * @return
     */
    boolean saveFlowRecordInfo(Long userInfoId, FlowRecordDTO flowRecordDTO);


}
