package com.mljr.operators.controller;

import com.google.common.collect.Lists;
import com.mljr.operators.entity.dto.chinaunicom.LoginDTO;
import com.mljr.operators.entity.vo.chinaunicom.BillVO;
import com.mljr.operators.entity.vo.chinaunicom.CallVO;
import com.mljr.operators.entity.vo.chinaunicom.SMSVO;
import com.mljr.operators.entity.vo.chinaunicom.UserInfoVO;
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

    @RequestMapping(value = "loadDataInfo", method = RequestMethod.GET)
    public String loadDataInfo(LoginDTO loginDTO) {
        try {
            if (loginDTO == null) {
                return null;
            }
            String cookies = chinaUnicomService.getCookies(loginDTO);
            if (null != cookies) {

                List<LocalDate> localDates = getSixmonths();

                UserInfoVO userInfoVO = chinaUnicomService.queryUserInfo(cookies);

                String acctBalance = chinaUnicomService.queryAcctBalance(cookies);

                localDates.forEach(localDate -> {
                    int pageNo = 1;
                    try {
                        CallVO callVO = null;
                        do {
                            pageNo = callVO != null ? callVO.getPageNo() : 1;
                            callVO = chinaUnicomService.queryCallDetail(cookies, localDate.getYear(), localDate.getMonthValue(), pageNo);
                        } while (callVO != null && callVO.getPageNo() <= callVO.getTotalPages());
                    } catch (Exception e) {
                        logger.error(String.format("chinaunicom queryCallDetail failure.date:{%s},pageNo:{%s}", localDate.toString(), pageNo), e);
                    }
                });

                localDates.forEach(localDate -> {
                    try {
                        BillVO billVO = chinaUnicomService.queryHistoryBill(cookies, localDate.getYear(), localDate.getMonthValue());
                    } catch (Exception e) {
                        logger.error("chinaunicom queryHistoryBill failure.date:{}", localDate.toString(), e);
                    }
                });

                localDates.forEach(localDate -> {
                    int pageNo = 1;
                    try {
                        SMSVO smsVO = null;
                        do {
                            pageNo = smsVO != null ? smsVO.getPageNo() : 1;
                            smsVO = chinaUnicomService.querySMS(cookies, localDate.getYear(), localDate.getMonthValue(), pageNo);
                        } while (smsVO != null && smsVO.getPageNo() <= smsVO.getTotalPages());
                    } catch (Exception e) {
                        logger.error(String.format("chinaunicom querySMS failure.date:{%s},pageNo:{%s}", localDate.toString(), pageNo));
                    }
                });
            }

        } catch (Exception e) {
            logger.error("chinaunicom load data info failure.params:{}", loginDTO.toString(), e);
        }
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
