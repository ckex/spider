package com.mljr.operators.common.utils;

import com.google.common.collect.Lists;
import com.mljr.operators.common.constant.ChinaMobileConstant;
import com.mljr.operators.entity.chinamobile.DatePair;
import com.mljr.operators.entity.dto.operator.RequestInfoDTO;

import java.util.List;

/**
 * Created by songchi on 17/3/3.
 */
public class ShanghaiUtils {

    public static List<RequestInfoDTO> getShanghaiUrls() {
        List<RequestInfoDTO> list = Lists.newArrayList();

        list.add(new RequestInfoDTO().setUrl(ChinaMobileConstant.Shanghai.PACKAGE_INFO_PATTERN));

        list.add(new RequestInfoDTO().setUrl(ChinaMobileConstant.Shanghai.CURRENT_BILL_INFO_PATTERN));


        DatePair pair = DatePair.getCurrentDatePair();

        String currFlow = String.format(ChinaMobileConstant.Shanghai.CURRENT_FLOW_INFO_PATTERN,
                pair.getStartDate(), pair.getEndDate());

        String currSms = String.format(ChinaMobileConstant.Shanghai.CURRENT_SMS_INFO_PATTERN,
                pair.getStartDate(), pair.getEndDate());

        String currCall = String.format(ChinaMobileConstant.Shanghai.CURRENT_CALL_INFO_PATTERN,
                pair.getStartDate(), pair.getEndDate());

        list.add(new RequestInfoDTO().setStartDate(pair.getStartDate())
                .setEndDate(pair.getEndDate()).setUrl(currFlow));

        list.add(new RequestInfoDTO().setStartDate(pair.getStartDate())
                .setEndDate(pair.getEndDate()).setUrl(currSms));

        list.add(new RequestInfoDTO().setStartDate(pair.getStartDate())
                .setEndDate(pair.getEndDate()).setUrl(currCall));

        for (String queryTime : DatePair.getHistoryDate(7)) {
            String url = String.format(ChinaMobileConstant.Shanghai.HISTORY_BILL_INFO_PATTERN, queryTime);
            list.add(new RequestInfoDTO().setStartDate(queryTime)
                    .setEndDate(queryTime).setUrl(url));
        }

        for (DatePair hisPair : DatePair.getHistoryDatePair(7)) {
            String hisFlow = String.format(ChinaMobileConstant.Shanghai.HISTORY_FLOW_INFO_PATTERN,
                    pair.getStartDate(), pair.getEndDate());

            String hisSms = String.format(ChinaMobileConstant.Shanghai.HISTORY_SMS_INFO_PATTERN,
                    pair.getStartDate(), pair.getEndDate());

            String hisCall = String.format(ChinaMobileConstant.Shanghai.HISTORY_CALL_INFO_PATTERN,
                    pair.getStartDate(), pair.getEndDate());
            list.add(new RequestInfoDTO().setStartDate(hisPair.getStartDate())
                    .setEndDate(hisPair.getEndDate()).setUrl(hisFlow));

            list.add(new RequestInfoDTO().setStartDate(hisPair.getStartDate())
                    .setEndDate(hisPair.getEndDate()).setUrl(hisSms));

            list.add(new RequestInfoDTO().setStartDate(hisPair.getStartDate())
                    .setEndDate(hisPair.getEndDate()).setUrl(hisCall));
        }
        return list;
    }
}
