package com.mljr.operators.entity.vo.chinaunicom;

import java.io.Serializable;
import java.util.List;

/**
 * @author gaoxi
 * @Time 2017/2/17
 */
public class SMSVO implements Serializable {

    private static final long serialVersionUID = 2043775441212915355L;

    private int pageNo=0;//当前页

    private int totalPages=0;//总页数

    private List<SMSDetailVO> smsDetails;

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

    public List<SMSDetailVO> getSmsDetails() {
        return smsDetails;
    }

    public void setSmsDetails(List<SMSDetailVO> smsDetails) {
        this.smsDetails = smsDetails;
    }

    public static class SMSDetailVO {

        private String sendTime;//发送时间

        private String sendNum;//发送号码

        private String smsType;//传送方式

        private String businessType;//业务类型

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public String getSendNum() {
            return sendNum;
        }

        public void setSendNum(String sendNum) {
            this.sendNum = sendNum;
        }

        public String getSmsType() {
            return smsType;
        }

        public void setSmsType(String smsType) {
            this.smsType = smsType;
        }

        public String getBusinessType() {
            return businessType;
        }

        public void setBusinessType(String businessType) {
            this.businessType = businessType;
        }
    }
}
