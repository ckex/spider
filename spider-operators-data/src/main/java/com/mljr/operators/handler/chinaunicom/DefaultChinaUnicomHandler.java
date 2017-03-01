package com.mljr.operators.handler.chinaunicom;

import com.google.common.collect.Lists;
import com.mljr.operators.common.constant.OperatorsUrlEnum;
import com.mljr.operators.common.utils.DateUtil;
import com.mljr.operators.entity.chinamobile.DatePair;
import com.mljr.operators.entity.dto.operator.AcquisitionDTO;
import com.mljr.operators.entity.dto.operator.RequestInfoDTO;
import com.mljr.operators.handler.AbstractAcquisitionHandler;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

/**
 * @author gaoxi
 * @time 2017/3/1
 */
public class DefaultChinaUnicomHandler extends AbstractAcquisitionHandler {

  @Override
  public List<RequestInfoDTO> builderRequestUrl(AcquisitionDTO acquisition) {
    List<RequestInfoDTO> list = Lists.newArrayList();
    List<DatePair> sixDatePairs = getCountMonth(acquisition.getStartDate(), 5);
    List<DatePair> twoDatePairs = getCountMonth(acquisition.getStartDate(), 1);
    List<DatePair> dayDatePairs = Lists.newArrayList();
    // 添加User URL
    list.add(builderUserInfo(acquisition, getYester(acquisition.getStartDate())));
    sixDatePairs.forEach(datePair -> {
      list.add(builderCallUrl(acquisition, datePair));
      list.add(builderSmsUrl(acquisition, datePair));
      list.add(builderBillUrl(acquisition, datePair));

      LocalDate startDate =
          DateUtil.stringToLocalDate(datePair.getStartDate(), DateUtil.PATTERN_yyyy_MM_dd);
      LocalDate endDate =
          DateUtil.stringToLocalDate(datePair.getEndDate(), DateUtil.PATTERN_yyyy_MM_dd);
      dayDatePairs
          .addAll(DateUtil.getEachDayOfBetween(startDate, endDate, DateUtil.PATTERN_yyyy_MM_dd));
    });
    twoDatePairs.forEach(datePair -> list.add(builderFlowRecordUrl(acquisition, datePair)));
    dayDatePairs.forEach(datePair -> list.add(builderFlowUrl(acquisition, datePair)));
    return list;
  }

  // 每月只需要抓取一次
  private RequestInfoDTO builderUserInfo(AcquisitionDTO acquisition, DatePair datePair) {
    OperatorsUrlEnum enums = OperatorsUrlEnum.CHINA_UNICOM_USER_INFO;
    String url = enums.getUrl();
    return RequestInfoDTO.me().setMobile(acquisition.getMobile()).setIdcard(acquisition.getIdcard())
        .setOperatorsType(enums.getOperators().getValue()).setUrlType(enums.getIndex()).setUrl(url)
        .setStartDate(datePair.getStartDate()).setEndDate(datePair.getEndDate());
  }

  // 抓取最近半年
  private RequestInfoDTO builderCallUrl(AcquisitionDTO acquisition, DatePair datePair) {
    OperatorsUrlEnum enums = OperatorsUrlEnum.CHINA_UNICOM_CALL;
    String url = String.format(enums.getUrl(), 1, datePair.getStartDate(), datePair.getEndDate());
    return RequestInfoDTO.me().setMobile(acquisition.getMobile()).setIdcard(acquisition.getIdcard())
        .setOperatorsType(enums.getOperators().getValue()).setUrlType(enums.getIndex()).setUrl(url)
        .setStartDate(datePair.getStartDate()).setEndDate(datePair.getEndDate());
  }

  // 抓取最近半年
  private RequestInfoDTO builderSmsUrl(AcquisitionDTO acquisition, DatePair datePair) {
    OperatorsUrlEnum enums = OperatorsUrlEnum.CHINA_UNICOM_SMS;
    String url = String.format(enums.getUrl(), 1, datePair.getStartDate().replaceAll("-", ""),
        datePair.getEndDate().replaceAll("-", ""));
    return RequestInfoDTO.me().setMobile(acquisition.getMobile()).setIdcard(acquisition.getIdcard())
        .setOperatorsType(enums.getOperators().getValue()).setUrlType(enums.getIndex()).setUrl(url)
        .setStartDate(datePair.getStartDate()).setEndDate(datePair.getEndDate());
  }

  // 抓取最近半年
  private RequestInfoDTO builderBillUrl(AcquisitionDTO acquisition, DatePair datePair) {
    OperatorsUrlEnum enums = OperatorsUrlEnum.CHINA_UNICOM_BILL;
    LocalDate localDate = LocalDate.parse(datePair.getStartDate());
    String yyyyMMDate = localDate.getYear() + "" + localDate.getMonthValue();
    String url = String.format(enums.getUrl(), yyyyMMDate);
    return RequestInfoDTO.me().setMobile(acquisition.getMobile()).setIdcard(acquisition.getIdcard())
        .setOperatorsType(enums.getOperators().getValue()).setUrlType(enums.getIndex()).setUrl(url)
        .setStartDate(datePair.getStartDate()).setEndDate(datePair.getEndDate());
  }

  // 抓取最近半年
  private RequestInfoDTO builderFlowUrl(AcquisitionDTO acquisition, DatePair datePair) {
    OperatorsUrlEnum enums = OperatorsUrlEnum.CHINA_UNICOM_FLOW;
    String url = String.format(enums.getUrl(), 1, datePair.getStartDate(), datePair.getEndDate());
    return RequestInfoDTO.me().setMobile(acquisition.getMobile()).setIdcard(acquisition.getIdcard())
        .setOperatorsType(enums.getOperators().getValue()).setUrlType(enums.getIndex()).setUrl(url)
        .setStartDate(datePair.getStartDate()).setEndDate(datePair.getEndDate());
  }

  // 抓取最近2个月
  private RequestInfoDTO builderFlowRecordUrl(AcquisitionDTO acquisition, DatePair datePair) {
    OperatorsUrlEnum enums = OperatorsUrlEnum.CHINA_UNICOM_FLOW_RECORD;
    String url = String.format(enums.getUrl(), 1, datePair.getStartDate().replaceAll("-", ""),
        datePair.getEndDate().replaceAll("-", ""));
    return RequestInfoDTO.me().setMobile(acquisition.getMobile()).setIdcard(acquisition.getIdcard())
        .setOperatorsType(enums.getOperators().getValue()).setUrlType(enums.getIndex()).setUrl(url)
        .setStartDate(datePair.getStartDate()).setEndDate(datePair.getEndDate());
  }

  private DatePair getMonth(LocalDate localDate) {
    if (localDate == localDate.with(TemporalAdjusters.firstDayOfMonth())) {
      return null;
    }
    LocalDate yesterDay = localDate.minusDays(1);
    LocalDate firstDay = yesterDay.with(TemporalAdjusters.firstDayOfMonth());
    return new DatePair(DateUtil.dateToString(firstDay, DateUtil.PATTERN_yyyy_MM_dd),
        DateUtil.dateToString(yesterDay, DateUtil.PATTERN_yyyy_MM_dd));
  }

  private List<DatePair> getCountMonth(LocalDate date, int monthCount) {
    List<DatePair> datePairs = Lists.newArrayList();
    DatePair maxDatePair = getMonth(date);
    if (null != maxDatePair) {
      datePairs.add(maxDatePair);
    }
    datePairs.addAll(DateUtil.getPreEachOfMonth(date, monthCount, DateUtil.PATTERN_yyyy_MM_dd));
    return datePairs;
  }

  private DatePair getYester(LocalDate localDate) {
    LocalDate yesterDay = localDate.minusDays(1);
    return new DatePair(DateUtil.dateToString(yesterDay, DateUtil.PATTERN_yyyy_MM_dd),
        DateUtil.dateToString(yesterDay, DateUtil.PATTERN_yyyy_MM_dd));
  }
}
