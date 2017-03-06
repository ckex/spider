package com.mljr.operators.task.chinamobile;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mljr.operators.common.constant.RequestInfoEnum;
import com.mljr.operators.common.utils.CookieUtils;
import com.mljr.operators.entity.chinamobile.DatePair;
import com.mljr.operators.entity.model.operators.CallInfo;
import com.mljr.operators.entity.model.operators.RequestInfo;
import com.mljr.operators.service.ChinaMobileService;
import com.mljr.operators.service.primary.operators.ICallInfoService;
import com.mljr.operators.service.primary.operators.IRequestInfoService;
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
public class CurrCallInfoTask implements Runnable {

    protected static final Logger logger = LoggerFactory.getLogger(HisCallInfoTask.class);

    @Autowired
    private ChinaMobileService chinaMobileService;

    @Autowired
    private ICallInfoService callInfoService;

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
            String data = chinaMobileService.getCurrentCallInfo(cMap, pair);
            writeToDb(data, pair);

            requestInfoService.updateStatusBySign(requestInfo.getSign(), RequestInfoEnum.SUCCESS,
                    RequestInfoEnum.INIT);

        } catch (Exception e) {
            logger.error("CurrCallInfoTask error", e);
            requestInfoService.updateStatusBySign(requestInfo.getSign(), RequestInfoEnum.ERROR,
                    RequestInfoEnum.INIT);
        }
    }

    void writeToDb(String data, DatePair pair) throws Exception {
        String callInfoStr = data.substring(data.indexOf("[["), data.lastIndexOf("]]") + 2);
        List<List<String>> list = new Gson().fromJson(callInfoStr, new TypeToken<List<List<String>>>() {
        }.getType());
        List<CallInfo> siList = Lists.newArrayList();
        for (List<String> subList : list) {

            String year = pair.getStartDate().substring(0, 4);

            String callDate = subList.get(1);
            String callLocalAddress = subList.get(2);
            String callType = subList.get(3);
            String callNumber = subList.get(4);
            String duration = subList.get(5);
            String landType = subList.get(6);
            String discountPackage = subList.get(7);
            String fee = subList.get(8);
            String firstCall = subList.get(9);

            CallInfo ci = new CallInfo();
            ci.setUserInfoId(userInfoId);
            ci.setCreateTime(new Date());
            ci.setUpdateTime(new Date());

            ci.setCallDate(DateUtils.parseDate(year + "-" + callDate, "yyyy-MM-dd HH:mm:ss"));
            ci.setCallLocalAddress(callLocalAddress);
            ci.setCallType(callType);
            ci.setCallLongHour(duration);
            ci.setLandType(landType);
            ci.setCallNumber(callNumber);
            ci.setDiscountPackage(discountPackage);
            ci.setCallFee(new BigDecimal(fee));
            ci.setFirstCall(firstCall);

            siList.add(ci);
        }
        callInfoService.insertByBatch(userInfoId, siList);
    }

}
