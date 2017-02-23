package com.mljr.operators.controller;

import com.google.common.collect.Lists;
import com.mljr.operators.entity.dto.chinaunicom.LoginDTO;
import com.mljr.operators.entity.vo.chinaunicom.ChinaUnicomVO;
import com.mljr.operators.service.chinaunicom.IChinaUnicomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * @author gaoxi
 * @Time 2017/2/19
 */
@RestController
@RequestMapping("/chinaunicom/")
public class ChinaUnicomController {

    private static final Logger logger = LoggerFactory.getLogger(ChinaUnicomController.class);

    @Autowired
    @Qualifier("chinaUnicomService")
    private IChinaUnicomService chinaUnicomService;

    @RequestMapping(value = "loadAllInfo", method = RequestMethod.GET)
    public ChinaUnicomVO loadAllInfo(LoginDTO loginDTO) {

        return null;
    }

    private static List<LocalDate> getSixmonths() {
        List<LocalDate> list = Lists.newArrayList();
        LocalDate today = LocalDate.now();
        list.add(today);
        for (int i = 1; i < 6; i++) {
            list.add(today.minusMonths(i));
        }
        return list;
    }


}
