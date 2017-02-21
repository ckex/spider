package com.mljr.operators.entity.dto.chinaunicom;

import java.io.Serializable;

/**
 * @author gaoxi
 * @Time 2017/2/17
 */
public class RemainingDTO implements Serializable {

    private static final long serialVersionUID = 4693775451657504251L;

    private Remaining resource;

    public Remaining getResource() {
        return resource;
    }

    public void setResource(Remaining resource) {
        this.resource = resource;
    }

    public static class Remaining {
        private String balance;

        private String acctBalance;

        private String overFlow;

        private Boolean flowFlag;

        private String remainFlow;

        private Boolean balanceFlag;

        private String totalFlow;

        private Boolean successFlow;

        private Boolean successVoice;

        private Boolean successBalance;

        private Boolean successCredit;

        private Boolean voiceFlag;

        private String creditBalance;

        private String creditFlag;

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getAcctBalance() {
            return acctBalance;
        }

        public void setAcctBalance(String acctBalance) {
            this.acctBalance = acctBalance;
        }

        public String getOverFlow() {
            return overFlow;
        }

        public void setOverFlow(String overFlow) {
            this.overFlow = overFlow;
        }

        public Boolean getFlowFlag() {
            return flowFlag;
        }

        public void setFlowFlag(Boolean flowFlag) {
            this.flowFlag = flowFlag;
        }

        public String getRemainFlow() {
            return remainFlow;
        }

        public void setRemainFlow(String remainFlow) {
            this.remainFlow = remainFlow;
        }

        public Boolean getBalanceFlag() {
            return balanceFlag;
        }

        public void setBalanceFlag(Boolean balanceFlag) {
            this.balanceFlag = balanceFlag;
        }

        public String getTotalFlow() {
            return totalFlow;
        }

        public void setTotalFlow(String totalFlow) {
            this.totalFlow = totalFlow;
        }

        public Boolean getSuccessFlow() {
            return successFlow;
        }

        public void setSuccessFlow(Boolean successFlow) {
            this.successFlow = successFlow;
        }

        public Boolean getSuccessVoice() {
            return successVoice;
        }

        public void setSuccessVoice(Boolean successVoice) {
            this.successVoice = successVoice;
        }

        public Boolean getSuccessBalance() {
            return successBalance;
        }

        public void setSuccessBalance(Boolean successBalance) {
            this.successBalance = successBalance;
        }

        public Boolean getSuccessCredit() {
            return successCredit;
        }

        public void setSuccessCredit(Boolean successCredit) {
            this.successCredit = successCredit;
        }

        public Boolean getVoiceFlag() {
            return voiceFlag;
        }

        public void setVoiceFlag(Boolean voiceFlag) {
            this.voiceFlag = voiceFlag;
        }

        public String getCreditBalance() {
            return creditBalance;
        }

        public void setCreditBalance(String creditBalance) {
            this.creditBalance = creditBalance;
        }

        public String getCreditFlag() {
            return creditFlag;
        }

        public void setCreditFlag(String creditFlag) {
            this.creditFlag = creditFlag;
        }
    }
}
