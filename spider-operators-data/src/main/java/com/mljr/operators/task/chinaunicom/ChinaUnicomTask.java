package com.mljr.operators.task.chinaunicom;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.mljr.operators.common.constant.MQConstant;
import com.mljr.operators.common.constant.OperatorsEnum;
import com.mljr.operators.common.constant.OperatorsUrlEnum;
import com.mljr.operators.common.constant.RequestInfoEnum;
import com.mljr.operators.common.utils.ChinaUnicomUtil;
import com.mljr.operators.common.utils.DateUtil;
import com.mljr.operators.common.utils.RabbitMQUtil;
import com.mljr.operators.convert.RequestInfoConvert;
import com.mljr.operators.entity.dto.chinaunicom.*;
import com.mljr.operators.entity.dto.operator.RequestInfoDTO;
import com.mljr.operators.entity.model.operators.RequestInfo;
import com.mljr.operators.entity.model.operators.UserInfo;
import com.mljr.operators.service.ApiService;
import com.mljr.operators.service.chinaunicom.IChinaUnicomStoreService;
import com.mljr.operators.service.primary.operators.IRequestInfoService;
import com.mljr.operators.service.primary.operators.IUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author gaoxi
 * @time 2017/3/2
 */
public class ChinaUnicomTask implements Runnable {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChinaUnicomTask.class);

  private IChinaUnicomStoreService chinaUnicomStoreService;

  private IUserInfoService userInfoService;

  private IRequestInfoService requestInfoService;

  private ApiService apiServicer;

  private RequestInfo requestInfo;

  public ChinaUnicomTask(ApplicationContext context, RequestInfo requestInfo) {
    this.requestInfo = requestInfo;
    this.chinaUnicomStoreService = context.getBean(IChinaUnicomStoreService.class);
    this.userInfoService = context.getBean(IUserInfoService.class);
    this.requestInfoService = context.getBean(IRequestInfoService.class);
    this.apiServicer = context.getBean(ApiService.class);
  }

  @Override
  public void run() {
    Stopwatch stopwatch = Stopwatch.createStarted();
    String cookies = apiServicer.findCookiesByCellphone(requestInfo.getMobile());
    List<RequestInfoDTO> list = Lists.newArrayList();
    if (StringUtils.isNotBlank(cookies)) {
      boolean flag = false;
      UserInfo userInfo =
          userInfoService.selectUniqUser(requestInfo.getMobile(), requestInfo.getIdcard());
      OperatorsUrlEnum enums = OperatorsUrlEnum.indexOf(requestInfo.getUrlType().intValue());
      switch (enums) {
        case CHINA_UNICOM_BILL:
          HttpRespDTO<BillDTO> billHttpResp =
              ChinaUnicomUtil.validateBill(cookies, requestInfo.getUrl());
          if (billHttpResp.isSucc()) {
            flag = chinaUnicomStoreService.saveBillInfo(userInfo.getId(), billHttpResp.getBody());
          }
          break;
        case CHINA_UNICOM_SMS:
          HttpRespDTO<SMSDTO> smsHttpResp =
              ChinaUnicomUtil.validateSMS(cookies, requestInfo.getUrl());
          if (smsHttpResp.isSucc()) {
            flag = chinaUnicomStoreService.saveSmsInfo(userInfo.getId(), smsHttpResp.getBody());
            int totalPage = getPageUrl(smsHttpResp.getBody());
            if (totalPage > 0) {
              list = getPageList(enums, totalPage);
            }
          }
          break;
        case CHINA_UNICOM_CALL:
          HttpRespDTO<CallDTO> callHttpResp =
              ChinaUnicomUtil.validateCall(cookies, requestInfo.getUrl());
          if (callHttpResp.isSucc()) {
            flag = chinaUnicomStoreService.saveCallInfo(userInfo.getId(), callHttpResp.getBody());
            int callTotalPage = getPageUrl(callHttpResp.getBody());
            if (callTotalPage > 0) {
              list = getPageList(enums, callTotalPage);
            }
          }
          break;
        case CHINA_UNICOM_FLOW:
          HttpRespDTO<FlowDetailDTO> flowHttpResp =
              ChinaUnicomUtil.validateFlow(cookies, requestInfo.getUrl());
          if (flowHttpResp.isSucc()) {
            flag = chinaUnicomStoreService.saveFlowInfo(userInfo.getId(), flowHttpResp.getBody());
          }
          break;
        case CHINA_UNICOM_USER_INFO:
          HttpRespDTO<UserInfoDTO> userInfoHttpResp =
              ChinaUnicomUtil.validatePackageInfo(cookies, requestInfo.getUrl());
          if (userInfoHttpResp.isSucc()) {
            flag = chinaUnicomStoreService.savePackageInfo(userInfo.getId(),
                userInfoHttpResp.getBody());
          }
          break;
        case CHINA_UNICOM_FLOW_RECORD:
          HttpRespDTO<FlowRecordDTO> flowRecordHttpResp =
              ChinaUnicomUtil.validateFlowRecord(cookies, requestInfo.getUrl());
          if (flowRecordHttpResp.isSucc()) {
            flag = chinaUnicomStoreService.saveFlowRecordInfo(userInfo.getId(),
                flowRecordHttpResp.getBody());
            int flowRecordTotalPage = getPageUrl(flowRecordHttpResp.getBody());
            if (flowRecordTotalPage > 0) {
              list = getPageList(enums, flowRecordTotalPage);
            }
          }
          break;
        default:
          LOGGER.warn("chinaunicom url type no find.params:{}", JSON.toJSON(requestInfo));
          break;
      }
      if (!flag) {
        LOGGER.error("chinaunicom url type store failure.params:{}", JSON.toJSON(requestInfo));
      }
      if (!list.isEmpty()) {
        try {
          List<RequestInfo> pageList = RequestInfoConvert.convert(list, RequestInfoEnum.INIT);
          requestInfoService.insertByBatch(pageList);
          List<RequestInfo> filterList =
              pageList.stream().filter(filterRequestInfo -> filterRequestInfo.getId() != null)
                  .collect(Collectors.toList());
          if (null != filterList && filterList.size() > 0) {
            filterList.forEach(filterRequestInfo -> {
              String routingKey =
                  RabbitMQUtil.getRoutingKey(OperatorsEnum.indexOf(requestInfo.getOperatorsType()));
              if (null != routingKey) {
                RabbitMQUtil.sendMessage(MQConstant.OPERATOR_MQ_EXCHANGE, routingKey, filterList);
              }
            });
          }

        } catch (Exception e) {
          LOGGER.error("page insertByBatch failure.params:{}", JSON.toJSON(requestInfo));
        }
      }
      RequestInfoEnum requestInfoEnum = flag ? RequestInfoEnum.SUCCESS : RequestInfoEnum.ERROR;
      requestInfoService.updateStatusBySign(requestInfo.getSign(), requestInfoEnum,
          RequestInfoEnum.INIT);
      if (null != enums) {
        String info = String.format("{%s} chinaunicom {%s} run use time={%s}",
            Thread.currentThread().getName(), String.valueOf(enums.getIndex()),
            stopwatch.elapsed(TimeUnit.MILLISECONDS));
        LOGGER.info(info);
      }
    }
  }

  private int getPageUrl(SMSDTO smsdto) {
    if (null != smsdto && null != smsdto.getPageMap()) {
      if (smsdto.getPageMap().getPageNo() == 1 && smsdto.getPageMap().getTotalPages() > 1) {
        return smsdto.getPageMap().getTotalPages();
      }
    }
    return 0;
  }

  private int getPageUrl(CallDTO callDTO) {
    if (null != callDTO && null != callDTO.getPageMap()) {
      if (callDTO.getPageMap().getPageNo() == 1 && callDTO.getPageMap().getTotalPages() > 1) {
        return callDTO.getPageMap().getTotalPages();
      }
    }
    return 0;
  }

  private int getPageUrl(FlowRecordDTO flowRecordDTO) {
    if (null != flowRecordDTO && null != flowRecordDTO.getPageMap()) {
      if (flowRecordDTO.getPageMap().getPageNo() == 1
          && flowRecordDTO.getPageMap().getTotalPages() > 1) {
        return flowRecordDTO.getPageMap().getTotalPages();
      }
    }
    return 0;
  }

  private List<RequestInfoDTO> getPageList(OperatorsUrlEnum operatorsUrlEnum, int totalPage) {
    List<RequestInfoDTO> list = Lists.newArrayList();
    String startDate =
        DateUtil.dateToString(requestInfo.getStartDate(), DateUtil.PATTERN_yyyy_MM_dd);
    String endDate = DateUtil.dateToString(requestInfo.getEndDate(), DateUtil.PATTERN_yyyy_MM_dd);
    for (int i = 2; i <= totalPage; i++) {
      String url = null;
      switch (operatorsUrlEnum) {
        case CHINA_UNICOM_SMS:
          url = String.format(operatorsUrlEnum.getUrl(), i, startDate.replaceAll("-", ""),
              endDate.replaceAll("-", ""));
          break;
        case CHINA_UNICOM_CALL:
          url = String.format(operatorsUrlEnum.getUrl(), i, startDate, endDate);
          break;
        case CHINA_UNICOM_FLOW:
          url = String.format(operatorsUrlEnum.getUrl(), i, startDate, endDate);
          break;
        case CHINA_UNICOM_FLOW_RECORD:
          url = String.format(operatorsUrlEnum.getUrl(), i, startDate.replaceAll("-", ""),
              endDate.replaceAll("-", ""));
          break;
      }
      RequestInfoDTO entity = new RequestInfoDTO(requestInfo.getMobile(), requestInfo.getIdcard())
          .setOperatorsEnum(OperatorsEnum.indexOf(requestInfo.getOperatorsType()))
          .setStartDate(startDate).setEndDate(endDate).setUrl(url)
          .setOperatorsUrl(operatorsUrlEnum);
      list.add(entity);
    }
    return list;
  }
}
