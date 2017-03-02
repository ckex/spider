package com.mljr.operators.service.reqeust;

import com.google.common.collect.Lists;
import com.mljr.operators.common.constant.OperatorsUrlEnum;
import com.mljr.operators.common.constant.ProvinceEnum;
import com.mljr.operators.common.utils.DateUtil;
import com.mljr.operators.entity.chinamobile.DatePair;
import com.mljr.operators.entity.dto.operator.RequestInfoDTO;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gaoxi
 * @time 2017/3/2
 */
public abstract class AbstractRequestUrlSelectorService implements IRequestUrlSelectorService {

  @Override
  public List<ProvinceEnum> availableProvince() {
    return Lists.newArrayList(ProvinceEnum.values());
  }

  /**
   * 获取要请求的URL
   * 
   * @return
   */
  protected abstract OperatorsUrlEnum getOperatorsUrl();

  protected RequestInfoDTO convert(String mobile, String idcard, DatePair datePair, String url) {
    return new RequestInfoDTO(mobile, idcard).setOperatorsEnum(getOperator())
        .setStartDate(datePair.getStartDate()).setEndDate(datePair.getEndDate()).setUrl(url)
        .setPattern(getPattern()).setOperatorsUrl(getOperatorsUrl());
  }

  protected String getPattern() {
    return DateUtil.PATTERN_yyyy_MM_dd;
  }

  /**
   * 往前推六个月
   * 
   * @param localDate 日期
   * @return
   */
  protected List<DatePair> getRecentMonth(LocalDate localDate, int monthCount) {
    List<DatePair> list = Lists.newArrayList();
    DatePair datePair = getYesterDayOfMonth(localDate);
    List<DatePair> fiveList =
        DateUtil.getPreEachOfMonth(localDate, monthCount, DateUtil.PATTERN_yyyy_MM_dd);
    List<DatePair> filterList =
        fiveList.stream()
            .filter(datePair1 -> !(datePair1.getStartDate().equals(datePair.getStartDate())
                && datePair1.getEndDate().equals(datePair.getEndDate())))
            .collect(Collectors.toList());
    list.add(datePair);
    list.addAll(filterList);
    return list;
  }

  /**
   * 获取开始时间和结束时间
   * 
   * @param localDate 日期
   * @return startDate=localDate-1所在月份的第一天 endDate=yesterDay-1
   */
  protected DatePair getYesterDayOfMonth(LocalDate localDate) {
    LocalDate yesterDay = localDate.minusDays(1);
    LocalDate firstDay = yesterDay.with(TemporalAdjusters.firstDayOfMonth());
    return new DatePair(DateUtil.dateToString(firstDay, DateUtil.PATTERN_yyyy_MM_dd),
        DateUtil.dateToString(yesterDay, DateUtil.PATTERN_yyyy_MM_dd));
  }

  /**
   * 获取日期之间的每一天
   * 
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return startDate &lt; date &lt; endDate
   */
  protected List<DatePair> getEachDay(LocalDate startDate, LocalDate endDate) {
    return DateUtil.getEachDayOfBetween(startDate, endDate, DateUtil.PATTERN_yyyy_MM_dd);
  }
}
