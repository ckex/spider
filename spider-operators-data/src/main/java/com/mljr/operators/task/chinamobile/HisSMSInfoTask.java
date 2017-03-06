package com.mljr.operators.task.chinamobile;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mljr.operators.common.constant.RequestInfoEnum;
import com.mljr.operators.common.utils.CookieUtils;
import com.mljr.operators.entity.chinamobile.DatePair;
import com.mljr.operators.entity.model.operators.RequestInfo;
import com.mljr.operators.entity.model.operators.SMSInfo;
import com.mljr.operators.service.ChinaMobileService;
import com.mljr.operators.service.primary.operators.IRequestInfoService;
import com.mljr.operators.service.primary.operators.ISMSInfoService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by songchi on 17/2/23.
 */
@Component
public class HisSMSInfoTask implements Runnable {

    protected static final Logger logger = LoggerFactory.getLogger(HisSMSInfoTask.class);

    @Autowired
    private ChinaMobileService chinaMobileService;

    @Autowired
    private ISMSInfoService smsInfoService;

    @Autowired
    private IRequestInfoService requestInfoService;

    public Long userInfoId;

    public String cookies;

    public RequestInfo requestInfo;

    public void setParams(Long userInfoId, String cookies, RequestInfo requestInfo) {
        this.userInfoId = userInfoId;
        this.cookies = cookies;
        this.requestInfo = requestInfo;
    }

    @Override
    public void run() {
        try {
            DatePair pair = new DatePair(DateFormatUtils.format(requestInfo.getStartDate(), "yyyy-MM-dd"),
                    DateFormatUtils.format(requestInfo.getEndDate(), "yyyy-MM-dd"));
            Map<String, String> cMap = CookieUtils.stringToMap(cookies);
            String data = chinaMobileService.getHistorySmsInfo(cMap, pair);
            writeToDb(data, pair);

            requestInfoService.updateStatusBySign(requestInfo.getSign(), RequestInfoEnum.SUCCESS,
                    RequestInfoEnum.INIT);

        } catch (Exception e) {
            logger.error("CurrSMSInfoTask error", e);
            requestInfoService.updateStatusBySign(requestInfo.getSign(), RequestInfoEnum.ERROR,
                    RequestInfoEnum.INIT);
        }
    }

    void writeToDb(String data, DatePair pair) throws Exception {
        String smsInfoStr = data.substring(data.indexOf("[["), data.lastIndexOf("]]") + 2);
        List<List<String>> list = new Gson().fromJson(smsInfoStr, new TypeToken<List<List<String>>>() {
        }.getType());
        List<SMSInfo> siList = Lists.newArrayList();
        for (List<String> subList : list) {

            String year = pair.getStartDate().substring(0, 4);

            String sendTime = subList.get(1);
            String location = subList.get(2);
            String sendNum = subList.get(3);
            String smsType = subList.get(4);
            String bussType = subList.get(5);
            String smsPackage = subList.get(6);
            String fee = subList.get(7);

            SMSInfo si = new SMSInfo();
            si.setUserInfoId(userInfoId);
            si.setSendTime(DateUtils.parseDate(year + "-" + sendTime, "yyyy-MM-dd HH:mm:ss"));
            si.setLocation(location);
            si.setSendNum(sendNum);
            si.setSmsType(smsType);
            si.setBusinessType(bussType);
            si.setSmsPackage(smsPackage);
            si.setFee(new BigDecimal(fee));
            si.setCreateTime(new Date());
            si.setUpdateTime(new Date());
            siList.add(si);
        }

        smsInfoService.insertByBatch(siList);
    }

}
