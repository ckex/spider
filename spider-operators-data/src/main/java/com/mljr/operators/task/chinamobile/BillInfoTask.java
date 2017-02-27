package com.mljr.operators.task.chinamobile;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.mljr.operators.entity.chinamobile.DatePair;
import com.mljr.operators.entity.model.operators.BillInfo;
import com.mljr.operators.service.ChinaMobileService;
import com.mljr.operators.service.primary.operators.IBillInfoService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.selector.Html;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by songchi on 17/2/23.
 */
@Component
public class BillInfoTask implements Runnable {
    protected static final Logger logger = LoggerFactory.getLogger(CallInfoTask.class);

    @Autowired
    private ChinaMobileService chinaMobileService;

    @Autowired
    private IBillInfoService billInfoService;

    public Long userInfoId;

    public Map<String, String> cookies;

    public void setParams(Long userInfoId, Map<String, String> cookies) {
        this.userInfoId = userInfoId;
        this.cookies = cookies;
    }

    Gson gson = new Gson();

    @Override

    public void run() {
        try {
            //写当月数据
            String currentData = chinaMobileService.getCurrentBillInfo(cookies);
            writeCurrent(currentData, DateFormatUtils.format(new Date(), "yyyy年MM月"));

            // 写历史数据
            for (String queryTime : DatePair.getHistoryDate(7)) {
                String historyData = chinaMobileService.getHistoryBillInfo(cookies, queryTime);
                Map<String, String> map = gson.fromJson(historyData, Map.class);
                String htmlStr = map.get("message");
                writeHistory(htmlStr, queryTime);
            }

        } catch (Exception e) {
            logger.error("BillInfoTask error", e);
        }
    }

    public void writeCurrent(String htmlStr, String queryTime) {
        try {
            Html html = new Html(htmlStr);
            List<String> feeNameAll = Lists.newArrayList();
            List<String> feeValueAll = Lists.newArrayList();

            for (int i = 2; i <= 6; i++) {
                String namePattern = "//*[@id=\"feeInfo\"]//table//tbody//tr[1]//td[1]//div//table//tbody[%d]//tr//td[1]//span//p//text()";
                String valuePattern = "//*[@id=\"feeInfo\"]//table//tbody//tr[1]//td[1]//div//table//tbody[%d]//tr//td[2]//text()";
                String feeName = html.xpath(String.format(namePattern, i)).get();
                String feeValue = html.xpath(String.format(valuePattern, i)).get();
                feeNameAll.add(StringUtils.trim(feeName));
                feeValueAll.add(StringUtils.replace(feeValue, "￥", "").trim());
            }

            for (int i = 2; i <= 5; i++) {
                String namePattern = "//*[@id=\"feeInfo\"]//table//tbody//tr[1]//td[2]//div//table//tbody[%d]//tr//td[1]//span//p//text()";
                String valuePattern = "//*[@id=\"feeInfo\"]//table//tbody//tr[1]//td[2]//div//table//tbody[%d]//tr//td[2]//text()";
                String feeName = html.xpath(String.format(namePattern, i)).get();
                String feeValue = html.xpath(String.format(valuePattern, i)).get();
                feeNameAll.add(StringUtils.trim(feeName));
                feeValueAll.add(StringUtils.replace(feeValue, "￥", "").trim());
            }

            List<BillInfo> infos = getBillInfos(queryTime, feeNameAll, feeValueAll);

            billInfoService.insertByBatch(userInfoId, infos);

        } catch (Exception e) {
            logger.error("BillInfoTask write current data error", e);
            e.printStackTrace();
        }
    }

    public void writeHistory(String htmlStr, String queryTime) {
        try {

            Html html = new Html(htmlStr);
            List<String> feeNameAll = Lists.newArrayList();
            List<String> feeValueAll = Lists.newArrayList();

            for (int i = 2; i <= 6; i++) {
                String namePattern = "//*[@id=\"feeInfo\"]//table//tbody//tr[1]//td[1]//table//tbody//tr[%d]//td[1]//span//p//text()";
                String valuePattern = "//*[@id=\"feeInfo\"]//table//tbody//tr[1]//td[1]//table//tbody//tr[%d]//td[2]//text()";
                String feeName = html.xpath(String.format(namePattern, i)).get();
                String feeValue = html.xpath(String.format(valuePattern, i)).get();
                feeNameAll.add(StringUtils.trim(feeName));
                feeValueAll.add(StringUtils.replace(feeValue, "￥", "").trim());
            }

            for (int i = 2; i <= 5; i++) {
                String namePattern = "//*[@id=\"feeInfo\"]//table//tbody//tr[1]//td[2]//table//tbody//tr[%d]//td[1]//span//p//text()";
                String valuePattern = "//*[@id=\"feeInfo\"]//table//tbody//tr[1]//td[2]//table//tbody//tr[%d]//td[2]//text()";
                String feeName = html.xpath(String.format(namePattern, i)).get();
                String feeValue = html.xpath(String.format(valuePattern, i)).get();
                feeNameAll.add(StringUtils.trim(feeName));
                feeValueAll.add(StringUtils.replace(feeValue, "￥", "").trim());
            }

            List<BillInfo> infos = getBillInfos(queryTime, feeNameAll, feeValueAll);

            billInfoService.insertByBatch(userInfoId, infos);

        } catch (Exception e) {
            logger.error("BillInfoTask write history data error", e);
            e.printStackTrace();
        }
    }

    public List<BillInfo> getBillInfos(String queryTime, List<String> feeNameAll, List<String> feeValueAll) {
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


}
