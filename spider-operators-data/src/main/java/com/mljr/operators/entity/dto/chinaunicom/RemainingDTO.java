package com.mljr.operators.entity.dto.chinaunicom;

import java.io.Serializable;

/**
 * @author gaoxi
 * @Time 2017/2/17
 */
public class RemainingDTO implements Serializable {

    private static final long serialVersionUID = 4693775451657504251L;

    private ResourceBean resource;

    public ResourceBean getResource() {
        return resource;
    }

    public void setResource(ResourceBean resource) {
        this.resource = resource;
    }

    public static class ResourceBean {

        private double balance;
        private double acctBalance;
        private double overFlow;
        private boolean flowFlag;
        private double remainFlow;
        private boolean balanceFlag;
        private double totalFlow;
        private boolean successFlow;
        private boolean successVoice;
        private boolean successBalance;
        private boolean successCredit;
        private boolean voiceFlag;
        private double creditBalance;
        private boolean creditFlag;

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public double getAcctBalance() {
            return acctBalance;
        }

        public void setAcctBalance(double acctBalance) {
            this.acctBalance = acctBalance;
        }

        public double getOverFlow() {
            return overFlow;
        }

        public void setOverFlow(double overFlow) {
            this.overFlow = overFlow;
        }

        public boolean isFlowFlag() {
            return flowFlag;
        }

        public void setFlowFlag(boolean flowFlag) {
            this.flowFlag = flowFlag;
        }

        public double getRemainFlow() {
            return remainFlow;
        }

        public void setRemainFlow(double remainFlow) {
            this.remainFlow = remainFlow;
        }

        public boolean isBalanceFlag() {
            return balanceFlag;
        }

        public void setBalanceFlag(boolean balanceFlag) {
            this.balanceFlag = balanceFlag;
        }

        public double getTotalFlow() {
            return totalFlow;
        }

        public void setTotalFlow(double totalFlow) {
            this.totalFlow = totalFlow;
        }

        public boolean isSuccessFlow() {
            return successFlow;
        }

        public void setSuccessFlow(boolean successFlow) {
            this.successFlow = successFlow;
        }

        public boolean isSuccessVoice() {
            return successVoice;
        }

        public void setSuccessVoice(boolean successVoice) {
            this.successVoice = successVoice;
        }

        public boolean isSuccessBalance() {
            return successBalance;
        }

        public void setSuccessBalance(boolean successBalance) {
            this.successBalance = successBalance;
        }

        public boolean isSuccessCredit() {
            return successCredit;
        }

        public void setSuccessCredit(boolean successCredit) {
            this.successCredit = successCredit;
        }

        public boolean isVoiceFlag() {
            return voiceFlag;
        }

        public void setVoiceFlag(boolean voiceFlag) {
            this.voiceFlag = voiceFlag;
        }

        public double getCreditBalance() {
            return creditBalance;
        }

        public void setCreditBalance(double creditBalance) {
            this.creditBalance = creditBalance;
        }

        public boolean isCreditFlag() {
            return creditFlag;
        }

        public void setCreditFlag(boolean creditFlag) {
            this.creditFlag = creditFlag;
        }
    }
}
