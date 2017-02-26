package com.mljr.operators.service.chinaunicom;

import com.mljr.operators.entity.dto.chinaunicom.*;

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
    PersonInfoDTO queryUserInfo(String cookies);

    /**
     * 查询最近6个月的历史账单信息
     *
     * @param cookies   cookies
     * @param billYear  year eg:2017
     * @param billMonth month eg:01
     * @return
     */
    BillDTO queryHistoryBill(String cookies, int billYear, int billMonth);

    /**
     * 获取话费月额
     *
     * @param cookies cookies
     * @return
     */
    RemainingDTO queryAcctBalance(String cookies);

    /**
     * 查询通话详情
     *
     * @param cookies   cookies
     * @param callYear  year eg:2017
     * @param callMonth month eg:1
     * @param pageNo    当前页
     * @return
     */
    CallDTO queryCallDetail(String cookies, int callYear, int callMonth, int pageNo);

    /**
     * 查询短信详情
     *
     * @param cookies  cookies
     * @param smsYear  year eg:2017
     * @param smsMonth month eg:1
     * @param pageNo   当前页
     * @return
     */
    SMSDTO querySMS(String cookies, int smsYear, int smsMonth, int pageNo);

    /**
     * 获取上网流量详情
     *
     * @param cookies cookies
     * @param date    日期
     * @param pageNo  分页数
     */
    FlowDetailDTO queryCallFlow(String cookies, String date, int pageNo);

    /**
     * 获取指定月份的手机上网记录
     *
     * @param cookies cookies
     * @param year    年
     * @param month   月
     * @param pageNo  分页数
     * @return
     */
    FlowRecordDTO queryFlowRecord(String cookies, int year, int month, int pageNo);
}
