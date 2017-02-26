package com.mljr.operators.entity.dto.chinaunicom;

import java.io.Serializable;
import java.util.List;

/**
 * @author gaoxi
 * @Time 2017/2/17
 */
public class SMSDTO implements Serializable {

    private static final long serialVersionUID = -1225547660913180719L;

    private String querynowdate;
    private int mmsCount;
    private String totalfee;
    private String endDate;
    private PageMapBean pageMap;
    private boolean isSuccess;
    private String beginDate;

    public String getQuerynowdate() {
        return querynowdate;
    }

    public void setQuerynowdate(String querynowdate) {
        this.querynowdate = querynowdate;
    }

    public int getMmsCount() {
        return mmsCount;
    }

    public void setMmsCount(int mmsCount) {
        this.mmsCount = mmsCount;
    }

    public String getTotalfee() {
        return totalfee;
    }

    public void setTotalfee(String totalfee) {
        this.totalfee = totalfee;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public PageMapBean getPageMap() {
        return pageMap;
    }

    public void setPageMap(PageMapBean pageMap) {
        this.pageMap = pageMap;
    }

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public static class PageMapBean {

        private int totalCount;
        private int pageNo;
        private int pageSize;
        private int totalPages;
        private List<ResultBean> result;
        private List<PagesBean> pages;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }

        public List<PagesBean> getPages() {
            return pages;
        }

        public void setPages(List<PagesBean> pages) {
            this.pages = pages;
        }

        public static class ResultBean {

            private String amount;
            private String fee;
            private String smsdate;
            private String smstime;
            private String businesstype;
            private String othernum;
            private String smstype;
            private String otherarea;
            private String homearea;
            private String deratefee;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }

            public String getSmsdate() {
                return smsdate;
            }

            public void setSmsdate(String smsdate) {
                this.smsdate = smsdate;
            }

            public String getSmstime() {
                return smstime;
            }

            public void setSmstime(String smstime) {
                this.smstime = smstime;
            }

            public String getBusinesstype() {
                return businesstype;
            }

            public void setBusinesstype(String businesstype) {
                this.businesstype = businesstype;
            }

            public String getOthernum() {
                return othernum;
            }

            public void setOthernum(String othernum) {
                this.othernum = othernum;
            }

            public String getSmstype() {
                return smstype;
            }

            public void setSmstype(String smstype) {
                this.smstype = smstype;
            }

            public String getOtherarea() {
                return otherarea;
            }

            public void setOtherarea(String otherarea) {
                this.otherarea = otherarea;
            }

            public String getHomearea() {
                return homearea;
            }

            public void setHomearea(String homearea) {
                this.homearea = homearea;
            }

            public String getDeratefee() {
                return deratefee;
            }

            public void setDeratefee(String deratefee) {
                this.deratefee = deratefee;
            }
        }

        public static class PagesBean {
            /**
             * pageNo : 1
             * curr : true
             */

            private int pageNo;
            private boolean curr;

            public int getPageNo() {
                return pageNo;
            }

            public void setPageNo(int pageNo) {
                this.pageNo = pageNo;
            }

            public boolean isCurr() {
                return curr;
            }

            public void setCurr(boolean curr) {
                this.curr = curr;
            }
        }
    }
}
