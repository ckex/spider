package com.mljr.operators.task.chinamobile;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.mljr.operators.dao.primary.operators.SMSInfoMapper;
import com.mljr.operators.entity.model.operators.SMSInfo;
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
public class SMSInfoTask implements Runnable {

    protected static final Logger logger = LoggerFactory.getLogger(SMSInfoTask.class);
    @Autowired
    private ChinaMobileService chinaMobileService;

    @Autowired
    private SMSInfoMapper smsInfoMapper;

    private Map<String, String> cookies;

//    public FlowInfoTask(Map<String, String> cookies) {
//        this.cookies = cookies;
//    }

    private String beginTime = "2016-12-01";


    private String endTime = "2016-12-31";


    @Override
    public void run() {
        try {
            String smsInfoStr = FileUtils.readFileToString(new File("/Users/songchi/Desktop/op/smsInfo.txt"));
            String data = smsInfoStr.substring(smsInfoStr.indexOf("[["), smsInfoStr.lastIndexOf("]]") + 2);
//            System.out.println(data);
            List<List<String>> list = new Gson().fromJson(data, List.class);
            List<SMSInfo> siList = Lists.newArrayList();
            for (List<String> subList : list) {

                String year = beginTime.substring(0, 4);

                String sendTime = subList.get(1);
                String location = subList.get(2);
                String sendNum = subList.get(3);
                String smsType = subList.get(4);
                String bussType = subList.get(5);
                String smsPackage = subList.get(6);
                String fee = subList.get(7);

                SMSInfo si = new SMSInfo();
                si.setUserInfoId(2181L);
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

            for (SMSInfo smsInfo : siList) {
                smsInfoMapper.insertSelective(smsInfo);
            }

//            flowInfoMapper.insertByBatch(siList);


        } catch (Exception e) {
            logger.error("SMSInfoTask error", e);

        }
    }

}
