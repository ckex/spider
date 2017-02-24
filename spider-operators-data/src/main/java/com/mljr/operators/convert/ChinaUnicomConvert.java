package com.mljr.operators.convert;

import com.google.common.collect.Lists;
import com.mljr.operators.common.constant.OperatorsEnum;
import com.mljr.operators.common.utils.DateUtil;
import com.mljr.operators.entity.dto.chinaunicom.*;
import com.mljr.operators.entity.model.operators.*;
import com.mljr.operators.exception.ConvertException;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gaoxi
 * @Time 2017/2/17
 */
public class ChinaUnicomConvert {

    private ChinaUnicomConvert() {
    }

    public static UserInfo convert(PersonInfoDTO personInfo) {
        UserInfo entity = null;
        try {
            if (null != personInfo && personInfo.getResult() != null
                    && personInfo.getResult().getMyDetail() != null) {
                entity = new UserInfo();
                entity.setUserName(personInfo.getResult().getMyDetail().getCustname());
                entity.setCityCode(personInfo.getResult().getMyDetail().getCitycode());
                entity.setType(String.valueOf(OperatorsEnum.CHINAUNICOM.getValue()));
                if ("男".equals(personInfo.getResult().getMyDetail().getCustsex())) {
                    entity.setSex(Boolean.FALSE);
                } else {
                    entity.setSex(Boolean.TRUE);
                }
                entity.setAddress(personInfo.getResult().getMyDetail().getCertaddr());
            }
        } catch (Exception e) {
            throw new ConvertException("convert failure.", e);
        }
        return entity;
    }

    public static List<PackageInfoDTO> packageForConvert(PersonInfoDTO personInfo) {
        List<PackageInfoDTO> list = Lists.newArrayList();
        try {
            if (null != personInfo && personInfo.getPackageInfo() != null) {
                if (null != personInfo.getPackageInfo().getProductInfo()
                        && personInfo.getPackageInfo().getProductInfo().size() > 0) {
                    personInfo.getPackageInfo().getProductInfo().forEach(productInfo -> list.add(convert(productInfo)));
                }
            }
        } catch (Exception e) {
            throw new ConvertException("convert failure.", e);
        }
        return list;
    }

    public static PackageInfoDTO convert(PersonInfoDTO.ProductInfo productInfo) {
        PackageInfoDTO dtoEntity = new PackageInfoDTO();
        {
            PackageInfo entity = new PackageInfo();
            entity.setProductName(productInfo.getProductName());
            dtoEntity.setPackageInfo(entity);
        }
        List<PackageInfoDetail> list = Lists.newArrayList();
        productInfo.getPackageInfo().forEach(packageInfo -> packageInfo.getDiscntInfo().forEach(discntInfo -> {
            list.add(convert(discntInfo));
        }));
        dtoEntity.setDetailList(list);
        return dtoEntity;
    }

    public static PackageInfoDetail convert(PersonInfoDTO.DiscntInfo discntInfo) {
        PackageInfoDetail entity = new PackageInfoDetail();
        entity.setDiscntName(discntInfo.getDiscntName());
        entity.setDiscntFee(discntInfo.getDiscntFee());
        return entity;
    }

    public static List<BillInfo> convert(BillDTO billDTO) {
        List<BillInfo> list = Lists.newArrayList();
        try {
            if (null != billDTO && null != billDTO.getResult()
                    && null != billDTO.getResult().getBillInfo()
                    && billDTO.getResult().getBillInfo().size() > 0) {
                List<BillDTO.BillInfo> billInfoList = billDTO.getResult().getBillInfo()
                        .stream().filter(billInfo -> !billInfo.getParentitemcode().equals("-1"))
                        .collect(Collectors.toList());
                if (null != billInfoList && billInfoList.size() > 0) {
                    billInfoList.forEach(billInfo -> {
                        BillInfo entity = convert(billInfo);
                        entity.setBillDate(Integer.parseInt(billDTO.getResult().getCycleid()));
                        list.add(entity);
                    });
                }
            }
        } catch (Exception e) {
            throw new ConvertException("convert failure.", e);
        }
        return list;
    }

    public static BillInfo convert(BillDTO.BillInfo billInfo) {
        BillInfo entity = new BillInfo();
        entity.setFeeName(billInfo.getIntegrateitem());
        entity.setFee(billInfo.getFee());
        return entity;
    }

    public static List<CallInfo> convert(CallDTO callDTO) {
        List<CallInfo> list = Lists.newArrayList();
        try {
            if (null != callDTO && null != callDTO.getPageMap()
                    && null != callDTO.getPageMap().getResult()
                    && callDTO.getPageMap().getResult().size() > 0) {

                callDTO.getPageMap().getResult().forEach(callDetailDTO -> list.add(convert(callDetailDTO)));
            }
        } catch (Exception e) {
            throw new ConvertException("convert failure.", e);
        }
        return list;
    }

    public static CallInfo convert(CallDTO.CallDetailDTO callDetailDTO) {
        CallInfo entity = new CallInfo();
        String date = null;
        if (!StringUtils.isBlank(callDetailDTO.getCalldate())) {
            date = callDetailDTO.getCalldate();
        }
        if (!StringUtils.isBlank(callDetailDTO.getCalltime())) {
            date = date + " " + callDetailDTO.getCalltime();
        }
        if (null != date) {
            entity.setCallDate(DateUtil.stringToDate(date, DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss));
        } else {
            DateUtil.stringToDate("1970-01-01 00:00:00", DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
        }
        entity.setCallNumber(callDetailDTO.getOthernum());
        entity.setCallType(callDetailDTO.getCalltypeName());
        entity.setLandType(callDetailDTO.getLandtype());
        entity.setCallLongHour(callDetailDTO.getCalllonghour());
        entity.setCallFee(new BigDecimal(callDetailDTO.getTotalfee()));
        entity.setCallLocalAddress(callDetailDTO.getHomearea());
        entity.setCallRemoteAddress(callDetailDTO.getCalledhome());
        return entity;
    }

    public static List<SMSInfo> convert(SMSDTO smsdto) {
        List<SMSInfo> list = Lists.newArrayList();
        if (null != smsdto && null != smsdto.getPageMap()
                && null != smsdto.getPageMap().getResult()
                && smsdto.getPageMap().getResult().size() > 0) {
            smsdto.getPageMap().getResult().forEach(smsDetailDTO -> list.add(convert(smsDetailDTO)));
        }
        return list;
    }

    public static SMSInfo convert(SMSDTO.SMSDetailDTO smsDetailDTO) {
        SMSInfo entity = new SMSInfo();
        String date = null;
        if (!StringUtils.isBlank(smsDetailDTO.getSmsdate())) {
            date = smsDetailDTO.getSmsdate();
        }
        if (!StringUtils.isBlank(smsDetailDTO.getSmstime())) {
            date = date + " " + smsDetailDTO.getSmstime();
        }
        if (null != date) {
            entity.setSendTime(DateUtil.stringToDate(date, DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss));
        } else {
            entity.setSendTime(DateUtil.stringToDate("1970-01-01 00:00:00", DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss));
        }
        entity.setSendNum(smsDetailDTO.getOthernum());
        entity.setFee(new BigDecimal(smsDetailDTO.getAmount()));
        entity.setLocation(smsDetailDTO.getHomearea());
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
