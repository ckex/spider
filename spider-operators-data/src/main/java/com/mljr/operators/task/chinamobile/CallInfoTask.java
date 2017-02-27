package com.mljr.operators.task.chinamobile;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.mljr.operators.dao.primary.operators.CallInfoMapper;
import com.mljr.operators.entity.chinamobile.DatePair;
import com.mljr.operators.entity.model.operators.CallInfo;
import com.mljr.operators.service.ChinaMobileService;
import com.mljr.operators.service.primary.operators.ICallInfoService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by songchi on 17/2/23.
 */
@Component
public class CallInfoTask extends AbstractTask {

    @Autowired
    private ChinaMobileService chinaMobileService;

    @Autowired
    private ICallInfoService callInfoService;

    @Override
    void writeToDb(String data, DatePair pair) throws Exception {
        String callInfoStr = data.substring(data.indexOf("[["), data.lastIndexOf("]]") + 2);
        List<List<String>> list = new Gson().fromJson(callInfoStr, List.class);
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

    @Override
    String fetchCurrentData(DatePair pair) throws Exception {
        return chinaMobileService.getCurrentCallInfo(cookies, pair);
    }

    @Override
    String fetchHistoryData(DatePair pair) throws Exception {
        return chinaMobileService.getHistoryCallInfo(cookies, pair);
    }
}
