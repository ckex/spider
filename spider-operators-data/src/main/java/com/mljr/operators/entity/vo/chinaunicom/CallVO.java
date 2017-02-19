package com.mljr.operators.entity.vo.chinaunicom;

import java.io.Serializable;
import java.util.List;

/**
 * @author gaoxi
 * @Time 2017/2/17
 */
public class CallVO implements Serializable {

    private static final long serialVersionUID = 2303143487952577878L;

    private int pageNo=0;//当前页

    private int totalPages=0;//总页数

    private String queryDate;//查询日期

    private List<CallDetailVO> callDetail;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<CallDetailVO> getCallDetail() {
        return callDetail;
    }

    public void setCallDetail(List<CallDetailVO> callDetail) {
        this.callDetail = callDetail;
    }

    public String getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }

    public static class CallDetailVO {

        private String callNumber;//呼叫号码

        private String callTypeName;//呼叫类型

        private String landtype;//通话类型

        private String callTime;//通话时间

        private String calllonghour;//通话时长

        public String getCallNumber() {
            return callNumber;
        }

        public void setCallNumber(String callNumber) {
            this.callNumber = callNumber;
        }

        public String getCallTypeName() {
            return callTypeName;
        }

        public void setCallTypeName(String callTypeName) {
            this.callTypeName = callTypeName;
        }

        public String getLandtype() {
            return landtype;
        }

        public void setLandtype(String landtype) {
            this.landtype = landtype;
        }

        public String getCallTime() {
            return callTime;
        }

        public void setCallTime(String callTime) {
            this.callTime = callTime;
        }

        public String getCalllonghour() {
            return calllonghour;
        }

        public void setCalllonghour(String calllonghour) {
            this.calllonghour = calllonghour;
        }
    }
}
