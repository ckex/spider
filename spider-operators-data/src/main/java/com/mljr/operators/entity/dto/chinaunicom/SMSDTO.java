package com.mljr.operators.entity.dto.chinaunicom;

import java.io.Serializable;
import java.util.List;

/**
 * @author gaoxi
 * @Time 2017/2/17
 */
public class SMSDTO implements Serializable {

    private static final long serialVersionUID = -1225547660913180719L;

    private SMSPage pageMap;

    private Integer mmsCount;

    private String totalfee;

    private String endDate;

    private String beginDate;

    private Boolean isSuccess;

    public SMSPage getPageMap() {
        return pageMap;
    }

    public void setPageMap(SMSPage pageMap) {
        this.pageMap = pageMap;
    }

    public Integer getMmsCount() {
        return mmsCount;
    }

    public void setMmsCount(Integer mmsCount) {
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

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public static class SMSPage {

        private Integer totalCount;

        private Integer pageNo;

        private Integer pageSize;

        private Integer totalPages;

        private List<SMSDetailDTO> result;

        public Integer getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }

        public Integer getPageNo() {
            return pageNo;
        }

        public void setPageNo(Integer pageNo) {
            this.pageNo = pageNo;
        }

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }

        public List<SMSDetailDTO> getResult() {
            return result;
        }

        public void setResult(List<SMSDetailDTO> result) {
            this.result = result;
        }
    }

    public static class SMSDetailDTO {

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
}
