package com.mljr.operators.task.chinamobile;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.mljr.operators.dao.primary.operators.CallInfoMapper;
import com.mljr.operators.entity.model.operators.CallInfo;
import com.mljr.operators.service.ChinaMobileService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by songchi on 17/2/23.
 */
@Component
public class CallInfoTask implements Runnable {

    protected static final Logger logger = LoggerFactory.getLogger(CallInfoTask.class);
    @Autowired
    private ChinaMobileService chinaMobileService;

    @Autowired
    private CallInfoMapper callInfoMapper;

    private Map<String, String> cookies;

//    public FlowInfoTask(Map<String, String> cookies) {
//        this.cookies = cookies;
//    }

    private String beginTime = "2016-12-01";


    private String endTime = "2016-12-31";


    @Override
    public void run() {
        try {
            String callInfoStr = FileUtils.readFileToString(new File("/Users/songchi/Desktop/op/callInfo.txt"));
            String data = callInfoStr.substring(callInfoStr.indexOf("[["), callInfoStr.lastIndexOf("]]") + 2);
            List<List<String>> list = new Gson().fromJson(data, List.class);
            List<CallInfo> siList = Lists.newArrayList();
            for (List<String> subList : list) {

                String year = beginTime.substring(0, 4);

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
                ci.setUserInfoId(2183L);
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

            for (CallInfo callInfo : siList) {
                callInfoMapper.insertSelective(callInfo);
            }

//            flowInfoMapper.insertByBatch(siList);


        } catch (Exception e) {
            logger.error("FlowInfoTask error", e);

        }
    }

}
