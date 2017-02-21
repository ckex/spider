package com.mljr.operators.controller;

import com.google.common.collect.Lists;
import com.mljr.operators.entity.dto.chinaunicom.LoginDTO;
import com.mljr.operators.entity.vo.chinaunicom.*;
import com.mljr.operators.service.chinaunicom.IChinaUnicomService;
import org.apache.commons.lang3.StringUtils;
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
        ChinaUnicomVO result = null;
        try {
            if (loginDTO == null
                    || StringUtils.isBlank(loginDTO.getMobile())
                    || StringUtils.isBlank(loginDTO.getPassword())) {
                return null;
            }
            String cookies = chinaUnicomService.getCookies(loginDTO);

            if (null != cookies) {

                result = new ChinaUnicomVO();

                List<CallVO> callVOList = Lists.newArrayList();

                List<BillVO> billVOList = Lists.newArrayList();

                List<SMSVO> smsvoList = Lists.newArrayList();

                List<LocalDate> localDates = getSixmonths();

                UserInfoVO userInfoVO = chinaUnicomService.queryUserInfo(cookies);

                String acctBalance = chinaUnicomService.queryAcctBalance(cookies);

                localDates.forEach(localDate -> {
                    int pageNo = 1;
                    try {
                        CallVO callVO = null;
                        do {
                            pageNo = callVO != null ? callVO.getPageNo() + 1 : 1;
                            callVO = chinaUnicomService.queryCallDetail(cookies, localDate.getYear(), localDate.getMonthValue(), pageNo);
                            callVOList.add(callVO);
                        } while (callVO != null && callVO.getPageNo() + 1 <= callVO.getTotalPages());
                    } catch (Exception e) {
                        logger.error(String.format("chinaunicom queryCallDetail failure.date:{%s},pageNo:{%s}", localDate.toString(), pageNo), e);
                    }
                });

                localDates.forEach(localDate -> {
                    try {
                        BillVO billVO = chinaUnicomService.queryHistoryBill(cookies, localDate.getYear(), localDate.getMonthValue());
                        billVOList.add(billVO);
                    } catch (Exception e) {
                        logger.error("chinaunicom queryHistoryBill failure.date:{}", localDate.toString(), e);
                    }
                });

                localDates.forEach(localDate -> {
                    int pageNo = 1;
                    try {
                        SMSVO smsVO = null;
                        do {
                            pageNo = smsVO != null ? smsVO.getPageNo() + 1 : 1;
                            smsVO = chinaUnicomService.querySMS(cookies, localDate.getYear(), localDate.getMonthValue(), pageNo);
                            smsvoList.add(smsVO);
                        } while (smsVO != null && smsVO.getPageNo() + 1 <= smsVO.getTotalPages());
                    } catch (Exception e) {
                        logger.error(String.format("chinaunicom querySMS failure.date:{%s},pageNo:{%s}", localDate.toString(), pageNo));
                    }
                });

                result.setUserInfo(userInfoVO);
                result.setAcctBalance(acctBalance);
                result.setCallItems(callVOList);
                result.setSmsItems(smsvoList);
                result.setBillItems(billVOList);
            }

        } catch (Exception e) {
            logger.error("chinaunicom load data info failure.params:{}", loginDTO.toString(), e);
        }
        return result;
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
