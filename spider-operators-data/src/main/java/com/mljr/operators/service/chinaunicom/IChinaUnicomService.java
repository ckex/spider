package com.mljr.operators.service.chinaunicom;

import com.mljr.operators.entity.dto.chinaunicom.LoginDTO;
import com.mljr.operators.entity.vo.chinaunicom.BillVO;
import com.mljr.operators.entity.vo.chinaunicom.CallVO;
import com.mljr.operators.entity.vo.chinaunicom.SMSVO;
import com.mljr.operators.entity.vo.chinaunicom.UserInfoVO;

import java.util.List;

/**
 * @author gaoxi
 * @Time 2017/2/13
 */
public interface IChinaUnicomService {

    /**
     * 获取cookie
     *
     * @param loginDTO login数据
     * @return 返回login后的cookies
     */
    String getCookies(LoginDTO loginDTO);

    /**
     * 获取运营商基本信息
     *
     * @param cookies cookies信息
     * @return
     */
    UserInfoVO queryUserInfo(String cookies) throws Exception;

    /**
     * 查询最近6个月的历史账单信息
     *
     * @param cookies   cookies
     * @param billYear  year eg:2017
     * @param billMonth month eg:01
     * @return
     * @throws Exception
     */
    BillVO queryHistoryBill(String cookies, int billYear, int billMonth) throws Exception;

    /**
     * 获取话费月额
     *
     * @param cookies cookies
     * @return
     */
    String queryAcctBalance(String cookies) throws Exception;

    /**
     * 查询通话详情
     *
     * @param cookies   cookies
     * @param callYear  year eg:2017
     * @param callMonth month eg:1
     * @param pageNo    当前页
     * @return
     * @throws Exception
     */
    CallVO queryCallDetail(String cookies, int callYear, int callMonth, int pageNo) throws Exception;

    /**
     * 查询短信详情
     *
     * @param cookies  cookies
     * @param smsYear  year eg:2017
     * @param smsMonth month eg:1
     * @param pageNo   当前页
     * @return
     * @throws Exception
     */
    SMSVO querySMS(String cookies, int smsYear, int smsMonth, int pageNo) throws Exception;
}
