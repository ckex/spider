package com.mljr.operators.task.chinamobile;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.mljr.operators.dao.primary.operators.SMSInfoMapper;
import com.mljr.operators.entity.chinamobile.DatePair;
import com.mljr.operators.entity.model.operators.SMSInfo;
import com.mljr.operators.service.ChinaMobileService;
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
public class SMSInfoTask extends AbstractTask {

    @Autowired
    private ChinaMobileService chinaMobileService;

    @Autowired
    private SMSInfoMapper smsInfoMapper;

    public void doWork(DatePair pair) throws Exception {
        String smsInfoStr = chinaMobileService.getSmsInfo(cookies, pair);
        String data = smsInfoStr.substring(smsInfoStr.indexOf("[["), smsInfoStr.lastIndexOf("]]") + 2);
        List<List<String>> list = new Gson().fromJson(data, List.class);
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

        smsInfoMapper.insertByBatch(siList);

    }

}
