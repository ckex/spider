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

  private ChinaUnicomConvert() {}

  public static UserInfo convert(UserInfoDTO personInfo) {
    UserInfo entity = null;
    try {
      if (null != personInfo && personInfo.getResult() != null
          && personInfo.getResult().getMyDetail() != null) {
        entity = new UserInfo();
        entity.setUserName(personInfo.getResult().getMyDetail().getCustname());
        entity.setCityCode(personInfo.getResult().getMyDetail().getCitycode());
        entity.setType(String.valueOf(OperatorsEnum.CHINAUNICOM.getCode()));
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

  public static List<PackageInfoDTO> packageForConvert(UserInfoDTO personInfo) {
    List<PackageInfoDTO> list = Lists.newArrayList();
    try {
      String brandName = "";
      if (null != personInfo && null != personInfo.getResult()
          && null != personInfo.getResult().getMyDetail()) {
        brandName = personInfo.getResult().getMyDetail().getBrand();
      }
      if (null != personInfo && personInfo.getPackageInfo() != null) {
        if (null != personInfo.getPackageInfo().getProductInfo()
            && personInfo.getPackageInfo().getProductInfo().size() > 0) {
          String finalBrandName = brandName;
          personInfo.getPackageInfo().getProductInfo().forEach(productInfo -> {
            list.add(convert(productInfo, finalBrandName));
          });
        }
      }
    } catch (Exception e) {
      throw new ConvertException("convert failure.", e);
    }
    return list;
  }

  public static PackageInfoDTO convert(UserInfoDTO.PackageInfoBeanXX.ProductInfoBean productInfo,
      String brand) {
    PackageInfoDTO dtoEntity = new PackageInfoDTO();
    {
      PackageInfo entity = new PackageInfo();
      entity.setProductName(productInfo.getProductName());
      // 获取品牌
      entity.setBrandName(brand);
      dtoEntity.setPackageInfo(entity);
    }
    List<PackageInfoDetail> list = Lists.newArrayList();
    if (null == productInfo.getPackageInfo() || productInfo.getPackageInfo().size() == 0) {
      PackageInfoDetail entity = new PackageInfoDetail();
      entity.setDiscntFee(productInfo.getProductFee());
      entity.setDiscntName(productInfo.getProductName());
      list.add(entity);
    } else {
      productInfo.getPackageInfo()
          .forEach(packageInfo -> packageInfo.getDiscntInfo().forEach(discntInfo -> {
            list.add(convert(discntInfo));
          }));
    }
    dtoEntity.setDetailList(list);
    return dtoEntity;
  }

  public static PackageInfoDetail convert(
      UserInfoDTO.PackageInfoBeanXX.ProductInfoBean.PackageInfoBean.DiscntInfoBean discntInfo) {
    PackageInfoDetail entity = new PackageInfoDetail();
    entity.setDiscntName(discntInfo.getDiscntName());
    entity.setDiscntFee(discntInfo.getDiscntFee());
    return entity;
  }

  public static List<BillInfo> convert(BillDTO billDTO) {
    List<BillInfo> list = Lists.newArrayList();
    try {
      if (null != billDTO && null != billDTO.getResult()
          && null != billDTO.getResult().getBillinfo()
          && billDTO.getResult().getBillinfo().size() > 0) {
        List<BillDTO.ResultBean.BillinfoBean> billInfoList = billDTO.getResult().getBillinfo()
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

  public static BillInfo convert(BillDTO.ResultBean.BillinfoBean billInfo) {
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

  public static CallInfo convert(CallDTO.PageMapBean.ResultBean callDetailDTO) {
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
    if (null != smsdto && null != smsdto.getPageMap() && null != smsdto.getPageMap().getResult()
        && smsdto.getPageMap().getResult().size() > 0) {
      smsdto.getPageMap().getResult().forEach(smsDetailDTO -> list.add(convert(smsDetailDTO)));
    }
    return list;
  }

  public static SMSInfo convert(SMSDTO.PageMapBean.ResultBean smsDetailDTO) {
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
      entity.setSendTime(
          DateUtil.stringToDate("1970-01-01 00:00:00", DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss));
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
    }else if("03".equals(smsDetailDTO.getBusinesstype())){
      entity.setBusinessType("国内彩信");
    } else {
      entity.setBusinessType(smsDetailDTO.getBusinesstype());
    }
    return entity;
  }

  public static List<FlowInfo> convert(FlowDetailDTO flowDetailDTO) {
    List<FlowInfo> list = Lists.newArrayList();
    try {
      if (null != flowDetailDTO && null != flowDetailDTO.getResult()
          && null != flowDetailDTO.getResult().getCdrinfo()
          && flowDetailDTO.getResult().getCdrinfo().size() > 0) {
        flowDetailDTO.getResult().getCdrinfo().forEach(cdrinfoBean -> {
          if (null != cdrinfoBean.getCdrdetailinfo() && cdrinfoBean.getCdrdetailinfo().size() > 0) {
            cdrinfoBean.getCdrdetailinfo().forEach(cdrdetailinfoBean -> {
              list.add(convert(cdrdetailinfoBean));
            });
          }
        });
      }
    } catch (Exception e) {
      throw new ConvertException("convert failure.", e);
    }
    return list;
  }

  public static FlowInfo convert(
      FlowDetailDTO.ResultBean.CdrinfoBean.CdrdetailinfoBean pagelistBean) {
    FlowInfo entity = new FlowInfo();
    String date = pagelistBean.getBegindateformat() + " " + pagelistBean.getBegintimeformat();
    entity.setStartTime(DateUtil.stringToDate(date, DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss));
    entity.setHomeArea(pagelistBean.getHomearea());
    entity.setNetType(pagelistBean.getNettypeformat());
    if ("1".equals(pagelistBean.getForwardtype())) { // 非定向流量
      entity.setForwardType(Boolean.FALSE);
    } else if ("0".equals(pagelistBean.getForwardtype())) {// 定向流量
      entity.setForwardType(Boolean.TRUE);
    }
    entity.setTotalBytes(new BigDecimal(pagelistBean.getPertotalsm()));
    if (StringUtils.isNotBlank(pagelistBean.getReceivebytes())) {
      double receive = Double.parseDouble(pagelistBean.getReceivebytes()) / 1024;
      entity.setReceiveBytes(new BigDecimal(receive));
    } else {
      entity.setReceiveBytes(BigDecimal.ZERO);
    }
    entity.setSendBytes(entity.getTotalBytes().subtract(entity.getReceiveBytes()));
    entity.setFee(new BigDecimal(pagelistBean.getTotalfee()));
    entity.setSvcName(pagelistBean.getSvcname());
    entity.setDuration(pagelistBean.getLonghour());
    return entity;
  }

  public static List<FlowRecord> convert(FlowRecordDTO flowRecordDTO) {
    List<FlowRecord> list = Lists.newArrayList();
    try {
      if (null != flowRecordDTO && null != flowRecordDTO.getPageMap()
          && null != flowRecordDTO.getPageMap().getResult()
          && flowRecordDTO.getPageMap().getResult().size() > 0) {
        flowRecordDTO.getPageMap().getResult().forEach(resultBean -> list.add(convert(resultBean)));
      }
    } catch (Exception e) {
      throw new ConvertException("convert failure.", e);
    }
    return list;
  }

  public static FlowRecord convert(FlowRecordDTO.PageMapBean.ResultBean resultBean) {
    FlowRecord entity = new FlowRecord();
    entity.setBizName(resultBean.getBizname());
    entity.setStartTime(
        DateUtil.stringToDate(resultBean.getBegintime(), DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss));
    entity.setDomainName(resultBean.getDomainname());
    entity.setFeatInfo(resultBean.getFeatinfo());
    entity.setFlowName(resultBean.getFlowname());
    return entity;
  }

}
