package com.mljr.operators.common.constant;

/**
 * Created by songchi on 17/2/26.
 */
public class ChinaMobileConstant {
    public static class Shanghai {

        public final static String SMS_CODE_PATTERN = "https://sh.ac.10086.cn/loginjt?act=1&telno=%s";

        public final static String LOGIN_PATTERN = "https://sh.ac.10086.cn/loginjt?act=2&telno=%s&password=%s&authLevel=5&dtm=%s&ctype=1&decode=1&source=wsyyt";


        public final static String USER_INFO_PATTERN = "http://www.sh.10086.cn/sh/wsyyt/action?act=my.getUserName";

        public final static String PACKAGE_INFO_PATTERN = "http://www.sh.10086.cn/sh/wsyyt/action?act=my.getMusType";


        public final static String CURRENT_BILL_INFO_PATTERN = "http://www.sh.10086.cn/sh/wsyyt/busi/historySearch.do?method=getFiveBillAll&showType=0&firstPage=y&uniqueKey=15&uniqueName=%E8%B4%A6%E5%8D%95%E6%9F%A5%E8%AF%A2";

        public final static String CURRENT_FLOW_INFO_PATTERN = "http://www.sh.10086.cn/sh/wsyyt/busi/historySearch.do?method=getOneBillDetailAjax&billType=NEW_GPRS_NEW&startDate=%s&endDate=%s&jingque=&searchStr=-1&index=0&r=1488089308448&isCardNo=0&gprsType=";

        public final static String CURRENT_SMS_INFO_PATTERN = "http://www.sh.10086.cn/sh/wsyyt/busi/historySearch.do?method=getOneBillDetailAjax&billType=NEW_SMS&startDate=%s&endDate=%s&jingque=&searchStr=-1&index=0&r=1488089308448&isCardNo=0&gprsType=";

        public final static String CURRENT_CALL_INFO_PATTERN = "http://www.sh.10086.cn/sh/wsyyt/busi/historySearch.do?method=getOneBillDetailAjax&billType=NEW_GSM&startDate=%s&endDate=%s&jingque=&searchStr=-1&index=0&r=1488089308448&isCardNo=0&gprsType=";


        public final static String HISTORY_BILL_INFO_PATTERN = "http://www.sh.10086.cn/sh/wsyyt/busi/historySearch.do?method=FiveBillAllNewAjax&dateTime=%s&tab=tab2_15&isPriceTaxSeparate=null&showType=0&r=1487313589994";

        public final static String HISTORY_FLOW_INFO_PATTERN = "http://www.sh.10086.cn/sh/wsyyt/busi/historySearch.do?method=getFiveBillDetailAjax&billType=NEW_GPRS_NEW&startDate=%s&endDate=%s&filterfield=输入对方号码：&filterValue=&searchStr=-1&index=0&r=1487232359541&isCardNo=0&gprsType=";

        public final static String HISTORY_SMS_INFO_PATTERN = "http://www.sh.10086.cn/sh/wsyyt/busi/historySearch.do?method=getFiveBillDetailAjax&billType=NEW_SMS&startDate=%s&endDate=%s&filterfield=输入对方号码：&filterValue=&searchStr=-1&index=0&r=1487232359541&isCardNo=0&gprsType=";

        public final static String HISTORY_CALL_INFO_PATTERN = "http://www.sh.10086.cn/sh/wsyyt/busi/historySearch.do?method=getFiveBillDetailAjax&billType=NEW_GSM&startDate=%s&endDate=%s&filterfield=输入对方号码：&filterValue=&searchStr=-1&index=0&r=1487232359541&isCardNo=0&gprsType=";

    }
}
