package com.mljr.operators.service.chinaunicom.impl;

import com.mljr.operators.convert.ChinaUnicomConvert;
import com.mljr.operators.entity.dto.chinaunicom.*;
import com.mljr.operators.entity.model.operators.BillInfo;
import com.mljr.operators.entity.model.operators.CallInfo;
import com.mljr.operators.entity.model.operators.SMSInfo;
import com.mljr.operators.entity.model.operators.UserInfo;
import com.mljr.operators.exception.ConvertException;
import com.mljr.operators.service.chinaunicom.IChinaUnicomStoreService;
import com.mljr.operators.service.primary.operators.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/23
 */
@Service
public class ChinaUnicomStoreServiceImpl implements IChinaUnicomStoreService {

    private static final Logger logger = LoggerFactory.getLogger(ChinaUnicomStoreServiceImpl.class);

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private ICallInfoService callInfoService;

    @Autowired
    private IBillInfoService billInfoService;

    @Autowired
    private ISMSInfoService smsInfoService;

    @Autowired
    private IPackageInfoService packageInfoService;

    @Autowired
    private IPackageInfoDetailService packageInfoDetailService;

    @Override
    public boolean saveUserInfo(LoginDTO loginDTO, PersonInfoDTO personInfoDTO) {
        boolean flag = false;
        try {
            UserInfo userInfo = ChinaUnicomConvert.convert(personInfoDTO);
            if (null != userInfo) {
                userInfo.setMobile(loginDTO.getMobile());
                userInfo.setPwd(loginDTO.getPassword());
                userInfo.setProvinceCode(loginDTO.getProvinceCode());
                userInfo.setIdcard(loginDTO.getIdcard());
                userInfoService.insertIgnore(userInfo);
            }
            flag = true;
        } catch (ConvertException e) {
            logger.error("get convert data failure.params:{}", loginDTO.toString(), e);
        } catch (RuntimeException e) {
            logger.error("save failure.params:{}", loginDTO.toString(), e);
        }
        return flag;
    }

    @Override
    public boolean savePackageInfo(Long userInfoId, PersonInfoDTO personInfo) {
        boolean flag = false;
        try {
            List<PackageInfoDTO> dtoList = ChinaUnicomConvert.packageForConvert(personInfo);
            if (!dtoList.isEmpty()) {
                dtoList.forEach(dto -> {
                    dto.getPackageInfo().setUserInfoId(userInfoId);
                    dto.getPackageInfo().setCreateTime(new Date());
                    dto.getPackageInfo().setUpdateTime(new Date());
                    packageInfoService.save(dto.getPackageInfo());
                    packageInfoDetailService.insertByBatch(dto.getPackageInfo().getId(), dto.getDetailList());
                });
            }
            flag = true;
        } catch (ConvertException e) {
            logger.error("get convert data failure.userInfoDetailId:{}", userInfoId, e);
        } catch (RuntimeException e) {
            logger.error("save failure.userInfoDetailId:{}", userInfoId, e);
        }
        return flag;
    }

    @Override
    public boolean saveCallInfo(Long userInfoId, CallDTO callDTO) {
        boolean flag = false;
        try {
            List<CallInfo> list = ChinaUnicomConvert.convert(callDTO);
            if (!list.isEmpty()) {
                callInfoService.insertByBatch(userInfoId, list);
            }
            flag = true;
        } catch (ConvertException e) {
            logger.error("get convert data failure.userInfoDetailId:{}", userInfoId, e);
        } catch (RuntimeException e) {
            logger.error("save failure.userInfoDetailId:{}", userInfoId, e);
        }
        return flag;
    }

    @Override
    public boolean saveBillInfo(Long userInfoId, BillDTO billDTO) {
        boolean flag = false;
        try {
            List<BillInfo> list = ChinaUnicomConvert.convert(billDTO);
            if (!list.isEmpty()) {
                billInfoService.insertByBatch(userInfoId, list);
            }
            flag = true;
        } catch (ConvertException e) {
            logger.error("get convert data failure.userInfoDetailId:{}", userInfoId, e);
        } catch (RuntimeException e) {
            logger.error("save failure.userInfoDetailId:{}", userInfoId, e);
        }
        return flag;
    }

    @Override
    public boolean saveSmsInfo(Long userInfoId, SMSDTO smsdto) {
        boolean flag = false;
        try {
            List<SMSInfo> list = ChinaUnicomConvert.convert(smsdto);
            if (!list.isEmpty()) {
                list.forEach(smsInfo -> smsInfo.setUserInfoId(userInfoId));
                smsInfoService.insertByBatch(list);
            }
            flag = true;
        } catch (ConvertException e) {
            logger.error("get convert data failure.userInfoDetailId:{}", userInfoId, e);
        } catch (RuntimeException e) {
            logger.error("save failure.userInfoDetailId:{}", userInfoId, e);
        }
        return flag;
    }

    @Override
    public boolean saveFlowInfo(Long userInfoId, FlowDetailDTO flowDetailDTO) {
        return true;
    }

    @Override
    public boolean saveFlowRecordInfo(Long userInfoId, FlowRecordDTO flowRecordDTO) {
        return true;
    }
}
