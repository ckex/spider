package com.mljr.operators.task.chinamobile;

import com.mljr.operators.entity.chinamobile.DatePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by songchi on 17/2/24.
 */
public abstract class AbstractTask implements Runnable {
    protected static final Logger logger = LoggerFactory.getLogger(CallInfoTask.class);
    public Long userInfoId;

    public Map<String, String> cookies;

    public void setParams(Long userInfoId, Map<String, String> cookies) {
        this.userInfoId = userInfoId;
        this.cookies = cookies;
    }

    abstract void writeToDb(String data, DatePair pair) throws Exception;

    abstract String fetchCurrentData(DatePair pair) throws Exception;

    abstract String fetchHistoryData(DatePair pair) throws Exception;

    @Override
    public void run() {

        try {
            // 写当月数据
            DatePair currentPair = DatePair.getCurrentDatePair();
            String currentData = fetchCurrentData(currentPair);
            writeToDb(currentData, currentPair);

            // 写历史数据
            for (DatePair hisPair : DatePair.getHistoryDatePair(7)) {
                String hisData = fetchHistoryData(hisPair);
                writeToDb(hisData, hisPair);
            }

        } catch (Exception e) {
            logger.error(this.toString(), e);
        }

    }
}
