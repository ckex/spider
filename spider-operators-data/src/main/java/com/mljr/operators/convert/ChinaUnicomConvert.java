package com.mljr.operators.convert;

import com.google.common.collect.Lists;
import com.mljr.operators.entity.dto.chinaunicom.BillDTO;
import com.mljr.operators.entity.dto.chinaunicom.CallDTO;
import com.mljr.operators.entity.dto.chinaunicom.PersonInfoDTO;
import com.mljr.operators.entity.dto.chinaunicom.SMSDTO;
import com.mljr.operators.entity.vo.chinaunicom.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gaoxi
 * @Time 2017/2/17
 */
public class ChinaUnicomConvert {

    private ChinaUnicomConvert() {
    }

    public static UserInfoVO convert(PersonInfoDTO personInfo) {
        UserInfoVO entity = new UserInfoVO();
        if (personInfo.getResult() != null && personInfo.getResult().getMyDetail() != null) {
            entity.setUserName(personInfo.getResult().getMyDetail().getCustname());
            entity.setCityCode(personInfo.getResult().getMyDetail().getCitycode());
            entity.setIdcard(personInfo.getResult().getMyDetail().getCertnum());
            entity.setLevel(personInfo.getResult().getMyDetail().getCustlvl());
            entity.setProductName(personInfo.getResult().getMyDetail().getProductname());
        }
        List<PackageInfoVO> packageInfoList = Lists.newArrayList();
        if (personInfo.getPackageInfo() != null) {
            if (personInfo.getPackageInfo().getProductInfo() != null
                    && personInfo.getPackageInfo().getProductInfo().size() > 0) {
                personInfo.getPackageInfo().getProductInfo().forEach(productInfo -> {
                    if (productInfo.getPackageInfo() != null && productInfo.getPackageInfo().size() > 0) {
                        productInfo.getPackageInfo().forEach(packageInfo -> packageInfoList.add(convert(packageInfo)));
                    }
                });
            }
        }
        entity.setPackageInfos(packageInfoList);
        return entity;
    }

    public static PackageInfoVO convert(PersonInfoDTO.PackageInfo packageInfo) {
        PackageInfoVO entity = new PackageInfoVO();
        entity.setPackageName(packageInfo.getPackageName());
        List<PackageInfoVO.DiscntInfoChinaUnicomVO> discntInfoList = Lists.newArrayList();
        if (packageInfo.getDiscntInfo() != null && packageInfo.getDiscntInfo().size() > 0) {
            packageInfo.getDiscntInfo().forEach(discntInfo -> discntInfoList.add(convert(discntInfo)));
        }
        entity.setDiscntInfos(discntInfoList);
        return entity;
    }

    public static PackageInfoVO.DiscntInfoChinaUnicomVO convert(PersonInfoDTO.DiscntInfo discntInfo) {
        PackageInfoVO.DiscntInfoChinaUnicomVO entity = new PackageInfoVO.DiscntInfoChinaUnicomVO();
        entity.setDiscntName(discntInfo.getDiscntName());
        entity.setDiscntFee(discntInfo.getDiscntFee());
        return entity;
    }

    public static List<BillVO.BillDetailVO> convert(BillDTO billDTO) {
        List<BillVO.BillDetailVO> list = Lists.newArrayList();
        if (null != billDTO.getResult()
                && null != billDTO.getResult().getBillInfo()
                && billDTO.getResult().getBillInfo().size() > 0) {
            List<BillDTO.BillInfo> billInfoList = billDTO.getResult().getBillInfo().stream().filter(billInfo -> billInfo.getParentitemcode().equals("-1")).collect(Collectors.toList());
            if (null != billInfoList && billInfoList.size() > 0) {
                billInfoList.forEach(billInfo -> list.add(convert(billInfo)));
            }
        }
        return list;
    }

    public static BillVO.BillDetailVO convert(BillDTO.BillInfo billInfo) {
        BillVO.BillDetailVO entity = new BillVO.BillDetailVO();
        entity.setFeeName(billInfo.getIntegrateitem());
        entity.setFee(billInfo.getFee());
        return entity;
    }

    public static CallVO.CallDetailVO convert(CallDTO.CallDetailDTO callDetailDTO) {
        CallVO.CallDetailVO entity = new CallVO.CallDetailVO();
        entity.setCalllonghour(callDetailDTO.getCalllonghour());
        entity.setCallNumber(callDetailDTO.getOthernum());
        if (!StringUtils.isBlank(callDetailDTO.getCalldate())) {
            entity.setCallTime(callDetailDTO.getCalldate());
        }
        if (!StringUtils.isBlank(callDetailDTO.getCalltime())) {
            entity.setCallTime(entity.getCallTime() + " " + callDetailDTO.getCalltime());
        }
        entity.setCallTypeName(callDetailDTO.getCalltypeName());
        entity.setLandtype(callDetailDTO.getLandtype());
        return entity;
    }

    public static SMSVO.SMSDetailVO convert(SMSDTO.SMSDetailDTO smsDetailDTO) {
        SMSVO.SMSDetailVO entity = new SMSVO.SMSDetailVO();
        if (!StringUtils.isBlank(smsDetailDTO.getSmsdate())) {
            entity.setSendTime(smsDetailDTO.getSmsdate());
        }
        if (!StringUtils.isBlank(smsDetailDTO.getSmstime())) {
            entity.setSendTime(entity.getSendTime() + " " + smsDetailDTO.getSmstime());
        }
        entity.setSendNum(smsDetailDTO.getOthernum());
        if ("1".equals(smsDetailDTO.getSmstype())) {
            entity.setSmsType("接收");
        } else if ("2".equals(smsDetailDTO.getSmstype())) {
            entity.setSmsType("发送");
        } else {
            entity.setSmsType(smsDetailDTO.getSmstype());
        }
        if ("01".equals(smsDetailDTO.getBusinesstype())) {
            entity.setBusinessType("国内短信");
        } else {
            entity.setBusinessType(smsDetailDTO.getBusinesstype());
        }
        return entity;
    }

}
