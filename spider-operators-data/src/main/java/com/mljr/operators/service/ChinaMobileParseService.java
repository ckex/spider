package com.mljr.operators.service;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mljr.operators.entity.chinamobile.DatePair;
import com.mljr.operators.entity.model.operators.BillInfo;
import com.mljr.operators.entity.model.operators.CallInfo;
import com.mljr.operators.entity.model.operators.FlowInfo;
import com.mljr.operators.entity.model.operators.SMSInfo;
import com.mljr.operators.service.primary.operators.IPhoneInfoService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import us.codecraft.webmagic.selector.Html;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ChinaMobileParseService {

    public static Gson gson = new Gson();

    public static List<CallInfo> parseCallInfo(IPhoneInfoService phoneInfoService,
                                               String data, DatePair pair, Long userInfoId) throws Exception {
        String callInfoStr = data.substring(data.indexOf("[["), data.lastIndexOf("]]") + 2);
        List<List<String>> list =
                new Gson().fromJson(callInfoStr, new TypeToken<List<List<String>>>() {
                }.getType());
        List<CallInfo> ciList = Lists.newArrayList();
        for (List<String> subList : list) {

            String year = pair.getStartDate().substring(0, 4);

            String callDate = subList.get(1);
            String callLocalAddress = subList.get(2);
            String callType = subList.get(3);
            String callNumber = subList.get(4);
            String duration = subList.get(5);
            String landType = subList.get(6);
            String discountPackage = subList.get(7);
            String fee = subList.get(8);
            String firstCall = subList.get(9);

            CallInfo ci = new CallInfo();
            ci.setUserInfoId(userInfoId);
            ci.setCreateTime(new Date());
            ci.setUpdateTime(new Date());

            ci.setCallDate(DateUtils.parseDate(year + "-" + callDate, "yyyy-MM-dd HH:mm:ss"));
            ci.setCallLocalAddress(callLocalAddress);
            ci.setCallType(callType);
            ci.setCallLongHour(duration);
            // 转秒
            ci.setCallDuration(CommonService.toSecond(duration));
            ci.setLandType(landType);
            ci.setCallNumber(callNumber);
            ci.setDiscountPackage(discountPackage);
            ci.setCallFee(new BigDecimal(fee));
            ci.setFirstCall(firstCall);
            ci.setCallRemoteAddress(phoneInfoService.selectByPhone(callNumber));
            ciList.add(ci);
        }

        return ciList;
    }

    public static List<FlowInfo> parseFlowInfo(String data, DatePair pair, Long userInfoId) throws Exception {
        String flowInfoStr = data.substring(data.indexOf("[["), data.lastIndexOf("]]") + 2);
        List<List<String>> list =
                new Gson().fromJson(flowInfoStr, new TypeToken<List<List<String>>>() {
                }.getType());
        List<FlowInfo> fiList = Lists.newArrayList();
        for (List<String> subList : list) {

            String startTime = subList.get(1);
            String homeArea = subList.get(2);
            String onlinePattern = subList.get(3); // 上网方式
            String duration = subList.get(4); // 时长
            String totalBytes = subList.get(5); // 流量
            String svcName = subList.get(6);
            String fee = subList.get(7);
            String netType = subList.get(8);

            String year = pair.getStartDate().substring(0, 4);

            FlowInfo fi = new FlowInfo();
            fi.setUserInfoId(userInfoId);
            fi.setForwardType(true);

            fi.setStartTime(DateUtils.parseDate(year + "-" + startTime, "yyyy-MM-dd HH:mm:ss"));
            fi.setHomeArea(homeArea);
            fi.setOnlinePattern(onlinePattern);
            fi.setDuration(CommonService.toSecond(duration) + "");
            fi.setTotalBytes(parseBytes(totalBytes));
            fi.setSvcName(svcName);
            fi.setFee(new BigDecimal(fee));
            fi.setNetType(netType);
            fi.setCreateTime(new Date());
            fi.setUpdateTime(new Date());

            fiList.add(fi);
        }

        return fiList;
    }


    public static List<SMSInfo> parseSmsInfo(String data, DatePair pair, Long userInfoId) throws Exception {
        String smsInfoStr = data.substring(data.indexOf("[["), data.lastIndexOf("]]") + 2);
        List<List<String>> list =
                new Gson().fromJson(smsInfoStr, new TypeToken<List<List<String>>>() {
                }.getType());
        List<SMSInfo> siList = Lists.newArrayList();
        for (List<String> subList : list) {

            String year = pair.getStartDate().substring(0, 4);

            String sendTime = subList.get(1);
            String location = subList.get(2);
            String sendNum = subList.get(3);
            String smsType = subList.get(4);
            String bussType = subList.get(5);
            String smsPackage = subList.get(6);
            String fee = subList.get(7);

            SMSInfo si = new SMSInfo();
            si.setUserInfoId(userInfoId);
            si.setSendTime(DateUtils.parseDate(year + "-" + sendTime, "yyyy-MM-dd HH:mm:ss"));
            si.setLocation(location);
            si.setSendNum(sendNum);
            si.setSmsType(smsType);
            si.setBusinessType(bussType);
            si.setSmsPackage(smsPackage);
            si.setFee(new BigDecimal(fee));
            si.setCreateTime(new Date());
            si.setUpdateTime(new Date());
            siList.add(si);
        }
        return siList;
    }

    public static List<BillInfo> parseCurrBillInfo(String htmlStr, String queryTime, Long userInfoId) throws Exception {
        Html html = new Html(htmlStr);
        List<String> feeNameAll = Lists.newArrayList();
        List<String> feeValueAll = Lists.newArrayList();

        for (int i = 2; i <= 6; i++) {
            String namePattern =
                    "//*[@id=\"feeInfo\"]//table//tbody//tr[1]//td[1]//div//table//tbody[%d]//tr//td[1]//span//p//text()";
            String valuePattern =
                    "//*[@id=\"feeInfo\"]//table//tbody//tr[1]//td[1]//div//table//tbody[%d]//tr//td[2]//text()";
            String feeName = html.xpath(String.format(namePattern, i)).get();
            String feeValue = html.xpath(String.format(valuePattern, i)).get();
            feeNameAll.add(StringUtils.trim(feeName));
            feeValueAll.add(StringUtils.replace(feeValue, "￥", "").trim());
        }

        for (int i = 2; i <= 5; i++) {
            String namePattern =
                    "//*[@id=\"feeInfo\"]//table//tbody//tr[1]//td[2]//div//table//tbody[%d]//tr//td[1]//span//p//text()";
            String valuePattern =
                    "//*[@id=\"feeInfo\"]//table//tbody//tr[1]//td[2]//div//table//tbody[%d]//tr//td[2]//text()";
            String feeName = html.xpath(String.format(namePattern, i)).get();
            String feeValue = html.xpath(String.format(valuePattern, i)).get();
            feeNameAll.add(StringUtils.trim(feeName));
            feeValueAll.add(StringUtils.replace(feeValue, "￥", "").trim());
        }

        return getBillInfos(queryTime, feeNameAll, feeValueAll);
    }


    public static List<BillInfo> parseHisBillInfo(String historyData, String queryTime, Long userInfoId) throws Exception {
        Map<String, String> map =
                gson.fromJson(historyData, new TypeToken<Map<String, String>>() {
                }.getType());
        String htmlStr = map.get("message");
        Html html = new Html(htmlStr);
        List<String> feeNameAll = Lists.newArrayList();
        List<String> feeValueAll = Lists.newArrayList();

        for (int i = 2; i <= 6; i++) {
            String namePattern =
                    "//*[@id=\"feeInfo\"]//table//tbody//tr[1]//td[1]//table//tbody//tr[%d]//td[1]//span//p//text()";
            String valuePattern =
                    "//*[@id=\"feeInfo\"]//table//tbody//tr[1]//td[1]//table//tbody//tr[%d]//td[2]//text()";
            String feeName = html.xpath(String.format(namePattern, i)).get();
            String feeValue = html.xpath(String.format(valuePattern, i)).get();
            feeNameAll.add(StringUtils.trim(feeName));
            feeValueAll.add(StringUtils.replace(feeValue, "￥", "").trim());
        }

        for (int i = 2; i <= 5; i++) {
            String namePattern =
                    "//*[@id=\"feeInfo\"]//table//tbody//tr[1]//td[2]//table//tbody//tr[%d]//td[1]//span//p//text()";
            String valuePattern =
                    "//*[@id=\"feeInfo\"]//table//tbody//tr[1]//td[2]//table//tbody//tr[%d]//td[2]//text()";
            String feeName = html.xpath(String.format(namePattern, i)).get();
            String feeValue = html.xpath(String.format(valuePattern, i)).get();
            feeNameAll.add(StringUtils.trim(feeName));
            feeValueAll.add(StringUtils.replace(feeValue, "￥", "").trim());
        }

        return getBillInfos(queryTime, feeNameAll, feeValueAll);
    }

    public static List<BillInfo> getBillInfos(String queryTime, List<String> feeNameAll,
                                              List<String> feeValueAll) {
        List<BillInfo> infos = Lists.newArrayList();
        for (int i = 0; i < feeNameAll.size(); i++) {
            BillInfo info = new BillInfo();
            info.setBillDate(Integer.parseInt(queryTime.replace("年", "").replace("月", "")));
            info.setFeeName(feeNameAll.get(i));
            info.setFee(feeValueAll.get(i));
            infos.add(info);
        }
        return infos;
    }


    private static BigDecimal parseBytes(String totalBytes) {
        if (totalBytes.contains("GB")) {
            totalBytes = totalBytes.replace("GB", "");
            return new BigDecimal(Integer.parseInt(totalBytes) * 1024 * 1024);
        } else if (totalBytes.contains("MB")) {
            totalBytes = totalBytes.replace("MB", "");
            return new BigDecimal(Integer.parseInt(totalBytes) * 1024);
        } else if (totalBytes.contains("KB")) {
            totalBytes = totalBytes.replace("KB", "");
            return new BigDecimal(Integer.parseInt(totalBytes));
        } else {
            throw new RuntimeException("流量单位处理错误  " + totalBytes);
        }
    }
}