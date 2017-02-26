package com.mljr.operators.service.chinaunicom.impl;

import com.alibaba.fastjson.JSON;
import com.mljr.operators.common.utils.DateUtil;
import com.mljr.operators.entity.dto.chinaunicom.*;
import com.mljr.operators.service.chinaunicom.IChinaUnicomService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author gaoxi
 * @Time 2017/2/13
 */
public abstract class AbstractChinaUnicomServiceImpl implements IChinaUnicomService {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractChinaUnicomServiceImpl.class);

    /**
     * 登陆URL
     */
    protected static final String LOGIN_URL = "http://iservice.10010.com/e4/index.html";

    /**
     * 获取用户基本信息+套餐信息
     */
    protected static final String USER_INFO_URL = "http://iservice.10010.com/e3/static/query/searchPerInfo/?_=%s";

    /**
     * 获取历史账单信息
     */
    protected static final String HISTORY_BILL_URL = "http://iservice.10010.com/e3/static/query/queryHistoryBill?_=%s&querytype=0001&querycode=0001&billdate=%s&flag=2";

    /**
     * 剩余话费、流量URL
     */
    protected static final String REMAINING_RESOURCE_URL = "http://iservice.10010.com/e3/static/query/userinfoquery?_=%s";

    /**
     * 通话详情
     */
    protected static final String CALL_DETAIL_URL = "http://iservice.10010.com/e3/static/query/callDetail?_=%s&pageNo=%s&pageSize=100&beginDate=%s&endDate=%s";

    /**
     * 短信URL
     */
    protected static final String SMS_URL = "http://iservice.10010.com/e3/static/query/sms?_=%s&pageSize=100&pageNo=%s&begindate=%s&enddate=%s";

    /**
     * 流量详单URL
     */
    protected static final String FLOW_DETAIL_URL = "http://iservice.10010.com/e3/static/query/callFlow?_=%s&pageSize=100&beginDate=%s&endDate=%s&pageNo=%s";

    /**
     * 上网记录URL
     */
    protected static final String FLOW_RECORD_URL = "http://iservice.10010.com/e3/static/query/callNetPlayRecord?_=%s&pageSize=100&beginDate=%s&endDate=%s&pageNo=%s";


    /**
     * user agent
     */
    protected static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:51.0) Gecko/20100101 Firefox/51.0";

    @Override
    public UserInfoDTO queryUserInfo(String cookies) {
        String url = String.format(USER_INFO_URL, new Date().getTime());
        String respString = request(url, cookies);
        if (null != respString) {
            return JSON.parseObject(respString, UserInfoDTO.class);
        }
        return null;
    }

    @Override
    public BillDTO queryHistoryBill(String cookies, int billYear, int billMonth) {
        LocalDate localDate = LocalDate.of(billYear, billMonth, 1);
        String month = localDate.getMonthValue() >= 10 ? localDate.getMonthValue() + "" : "0" + localDate.getMonthValue();
        String billDate = localDate.getYear() + "" + month;
        String url = String.format(HISTORY_BILL_URL, new Date().getTime(), billDate);
        String respStr = request(url, cookies);
        if (null != respStr) {
            return JSON.parseObject(respStr, BillDTO.class);
        }
        return null;
    }

    @Override
    public RemainingDTO queryAcctBalance(String cookies) {
        String url = String.format(REMAINING_RESOURCE_URL, new Date());
        String respStr = request(url, cookies);
        if (null != respStr) {
            return JSON.parseObject(respStr, RemainingDTO.class);
        }
        return null;
    }

    @Override
    public CallDTO queryCallDetail(String cookies, int callYear, int callMonth, int pageNo) {
        String startDate = LocalDate.of(callYear, callMonth, 1).toString();
        String endDate = LocalDate.of(callYear, callMonth + 1, 1).minusDays(1).toString();
        String url = String.format(CALL_DETAIL_URL, new Date().getTime(), pageNo, startDate, endDate);
        String respStr = request(url, cookies);
        if (null != respStr) {
            return JSON.parseObject(respStr, CallDTO.class);
        }
        return null;
    }

    @Override
    public SMSDTO querySMS(String cookies, int smsYear, int smsMonth, int pageNo) {
        String startDate = LocalDate.of(smsYear, smsMonth, 1).format(DateTimeFormatter.BASIC_ISO_DATE);
        String endDate = LocalDate.of(smsYear, smsMonth + 1, 1).minusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE);
        String url = String.format(SMS_URL, new Date().getTime(), pageNo, startDate, endDate);
        String respStr = request(url, cookies);
        if (null != respStr) {
            return JSON.parseObject(respStr, SMSDTO.class);
        }
        return null;
    }

    @Override
    public FlowDetailDTO queryCallFlow(String cookies, String date, int pageNo) {
        String queryDate = DateUtil.dateToString(DateUtil.defaultStringToDate(date), DateUtil.PATTERN_yyyy_MM_dd);
        pageNo = pageNo <= 0 ? 1 : pageNo;
        String url = String.format(FLOW_DETAIL_URL, new Date(), queryDate, queryDate, pageNo);
        String respStr = request(url, cookies);
        if (null != respStr) {
            return JSON.parseObject(respStr, FlowDetailDTO.class);
        }
        return null;
    }

    @Override
    public FlowRecordDTO queryFlowRecord(String cookies, int year, int month, int pageNo) {
        String startDate = LocalDate.of(year, month, 1).format(DateTimeFormatter.BASIC_ISO_DATE);
        String endDate = LocalDate.of(year, month + 1, 1).minusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE);
        pageNo = pageNo <= 0 ? 1 : pageNo;
        String url = String.format(FLOW_RECORD_URL, new Date(), startDate, endDate, pageNo);
        String respStr = request(url, cookies);
        if (null != respStr) {
            return JSON.parseObject(respStr, FlowRecordDTO.class);
        }
        return null;
    }

    protected String request(String url, String cookies) {
        try {
            Connection connection = Jsoup.connect(url)
                    .timeout(60 * 1000)
                    .method(Connection.Method.POST)
                    .header("User-Agent", USER_AGENT)
                    .ignoreContentType(true);
            if (!StringUtils.isBlank(cookies)) {
                connection.header("Cookie", cookies);
            }
            Connection.Response response = connection.execute();
            if (response.statusCode() == 200 && !StringUtils.isBlank(response.body())) {
                logger.info("get reslut json url:{} data:{}", url, response.body());
                return response.body();
            }
        } catch (IOException e) {
            logger.error("request failure.url:{}", url, e);
        }
        return null;
    }
}
