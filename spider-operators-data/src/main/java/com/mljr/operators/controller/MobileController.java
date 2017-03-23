package com.mljr.operators.controller;

import com.alibaba.fastjson.JSON;
import com.mljr.operators.common.constant.ApiCodeEnum;
import com.mljr.operators.entity.BaseResponse2;
import com.mljr.operators.entity.model.operators.PhoneInfo;
import com.mljr.operators.service.primary.operators.IPhoneInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gaoxi
 * @Time: 2017/3/23 下午8:38
 */
@RestController
@RequestMapping("mobile")
public class MobileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MobileController.class);

    @Autowired
    private IPhoneInfoService phoneInfoService;

    @RequestMapping(value = "/query/{mobile}", method = RequestMethod.GET)
    public String queryAffiliationPlace(@PathVariable String mobile) {
        BaseResponse2<PhoneInfo> entity  =  null;
        try {
            PhoneInfo phoneInfo = phoneInfoService.queryByPhone(mobile);
            entity=new BaseResponse2<>(ApiCodeEnum.SUCC,phoneInfo);
        } catch (Exception e) {
            LOGGER.error("[queryAffiliationPlace] error.mobile:{}", mobile, e);
            entity=new BaseResponse2<>(ApiCodeEnum.SYSTEM_EXCEPTIION,null);
        }
        return JSON.toJSONString(entity);
    }

}
