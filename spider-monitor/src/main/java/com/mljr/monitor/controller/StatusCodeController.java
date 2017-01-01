package com.mljr.monitor.controller;

import com.mljr.entity.MonitorData;
import com.mljr.monitor.service.StatusCodeService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;

/**
 * Created by songchi on 16/12/23.
 */
@Controller
public class StatusCodeController {

    @Autowired
    private StatusCodeService statusCodeService;

    @RequestMapping("/statusCode")
    public ModelAndView statusCode() {

        List<String> jsonList = statusCodeService.getLatestRecord();
        List<MonitorData> dataList = statusCodeService.transferToObject(jsonList);
        if (CollectionUtils.isNotEmpty(dataList)) {
            dataList.sort(Comparator.comparing(MonitorData::getDomain).thenComparing(MonitorData::getServerIp, ipComparator));
        }
        ModelAndView mav = new ModelAndView("statusCode");
        mav.addObject("dataList", dataList);
        mav.addObject("totolCount", statusCodeService.totolCount(dataList));
        return mav;
    }

    @RequestMapping("/detail/{serverIp}/{domain}/other")
    public ModelAndView detail(@PathVariable("serverIp") String serverIp, @PathVariable("domain") String domain) {

        List<String> jsonList = statusCodeService.getRecordByDomain(serverIp, domain);
        List<MonitorData> dataList = statusCodeService.transferToObject(jsonList);

        ModelAndView mav = new ModelAndView("statusCode");
        mav.addObject("dataList", dataList);
        mav.addObject("totolCount", statusCodeService.totolCount(dataList));
        return mav;
    }

    Comparator<String> ipComparator = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            try{
                if (o1.startsWith("ucloud") && o2.startsWith("ucloud")) {
                    int num1 = Integer.parseInt(o1.replace("ucloud", ""));
                    int num2 = Integer.parseInt(o2.replace("ucloud", ""));
                    return num1 - num2;
                }
            }catch (Exception e){
                e.printStackTrace();
                return o1.compareTo(o2);
            }
            return o1.compareTo(o2);
        }
    };
}
