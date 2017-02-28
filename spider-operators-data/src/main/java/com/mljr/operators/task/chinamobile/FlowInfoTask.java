package com.mljr.operators.task.chinamobile;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mljr.operators.entity.chinamobile.DatePair;
import com.mljr.operators.entity.model.operators.FlowInfo;
import com.mljr.operators.service.ChinaMobileService;
import com.mljr.operators.service.primary.operators.IFlowInfoService;
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
public class FlowInfoTask extends AbstractTask {

    @Autowired
    private ChinaMobileService chinaMobileService;

    @Autowired
    private IFlowInfoService flowInfoService;

    @Override
    void writeToDb(String data, DatePair pair) throws Exception {
        String flowInfoStr = data.substring(data.indexOf("[["), data.lastIndexOf("]]") + 2);
        List<List<String>> list = new Gson().fromJson(flowInfoStr, new TypeToken<List<List<String>>>(){}.getType());
        List<FlowInfo> fiList = Lists.newArrayList();
        for (List<String> subList : list) {

            String startTime = subList.get(1);
            String homeArea = subList.get(2);
            String onlinePattern = subList.get(3);  // 上网方式
            String duration = subList.get(4);   // 时长
            String totalBytes = subList.get(5);  // 流量
            String svcName = subList.get(6);
            String fee = subList.get(7);
            String netType = subList.get(8);

            String year = pair.getStartDate().substring(0, 4);

            FlowInfo fi = new FlowInfo();
            fi.setUserInfoId(userInfoId);
            fi.setForwardType(true);

            fi.setStartTime(DateUtils.parseDate(year + "-" + startTime, "yyyy-MM-dd HH:mm:ss"));
            fi.setHomeArea(homeArea);
            fi.setOnlinePattern(onlinePattern);
            fi.setDuration(duration);
            fi.setTotalBytes(parseBytes(totalBytes));
            fi.setSvcName(svcName);
            fi.setFee(new BigDecimal(fee));
            fi.setNetType(netType);
            fi.setCreateTime(new Date());
            fi.setUpdateTime(new Date());

            fiList.add(fi);
        }
        flowInfoService.insertByBatch(fiList);
    }

    @Override
    String fetchCurrentData(DatePair pair) throws Exception {
        return chinaMobileService.getCurrentFlowInfo(cookies, pair);
    }

    @Override
    String fetchHistoryData(DatePair pair) throws Exception {
        return chinaMobileService.getHistoryFlowInfo(cookies, pair);
    }

    private BigDecimal parseBytes(String totalBytes) {
        if (totalBytes.contains("GB")) {
            totalBytes = totalBytes.replace("GB", "");
            return new BigDecimal(Integer.parseInt(totalBytes) * 1024 * 1024);
        } else if (totalBytes.contains("MB")) {
            totalBytes = totalBytes.replace("MB", "");
            return new BigDecimal(Integer.parseInt(totalBytes) * 1024);
        } else if (totalBytes.contains("KB")) {
            totalBytes = totalBytes.replace("KB", "");
            return new BigDecimal(Integer.parseInt(totalBytes));
        } else {
            throw new RuntimeException("流量单位处理错误  " + totalBytes);
        }
    }
}
