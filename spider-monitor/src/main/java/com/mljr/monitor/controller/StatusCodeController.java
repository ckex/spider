package com.mljr.monitor.controller;

import com.google.common.collect.ComparisonChain;
import com.mljr.entity.MonitorData;
import com.mljr.monitor.service.StatusCodeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;

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
        if(CollectionUtils.isNotEmpty(dataList)){
            dataList.sort(Comparator.comparing(MonitorData::getDomain).thenComparing(MonitorData::getServerIp));
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
}
