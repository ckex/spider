package com.mljr.operators.entity.dto.chinaunicom;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author gaoxi
 * @Time 2017/2/17
 * 历史账单
 */
public class BillDTO implements Serializable {

    private static final long serialVersionUID = -7614194060583443591L;

    private String busiorder;

    private String ecsbusiorder;

    private Boolean existexception;

    private Boolean isnotsuccess;

    private Boolean issuccess;

    private String rspcode;

    private String rspdesc;

    private String rspsign;

    private String rspts;

    private String transid;

    private String trxid;

    private Result result;

    public String getBusiorder() {
        return busiorder;
    }

    public void setBusiorder(String busiorder) {
        this.busiorder = busiorder;
    }

    public String getEcsbusiorder() {
        return ecsbusiorder;
    }

    public void setEcsbusiorder(String ecsbusiorder) {
        this.ecsbusiorder = ecsbusiorder;
    }

    public Boolean getExistexception() {
        return existexception;
    }

    public void setExistexception(Boolean existexception) {
        this.existexception = existexception;
    }

    public Boolean getIsnotsuccess() {
        return isnotsuccess;
    }

    public void setIsnotsuccess(Boolean isnotsuccess) {
        this.isnotsuccess = isnotsuccess;
    }

    public Boolean getIssuccess() {
        return issuccess;
    }

    public void setIssuccess(Boolean issuccess) {
        this.issuccess = issuccess;
    }

    public String getRspcode() {
        return rspcode;
    }

    public void setRspcode(String rspcode) {
        this.rspcode = rspcode;
    }

    public String getRspdesc() {
        return rspdesc;
    }

    public void setRspdesc(String rspdesc) {
        this.rspdesc = rspdesc;
    }

    public String getRspsign() {
        return rspsign;
    }

    public void setRspsign(String rspsign) {
        this.rspsign = rspsign;
    }

    public String getRspts() {
        return rspts;
    }

    public void setRspts(String rspts) {
        this.rspts = rspts;
    }

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }

    public String getTrxid() {
        return trxid;
    }

    public void setTrxid(String trxid) {
        this.trxid = trxid;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {

        private String actionfeeused;

        private String adjustfee;

        private String allfee;

        private String areacode;

        private String backfee;

        private String balance;

        private String busiorder;

        private String cycleid;

        private String deratefee;

        private String issuccess;

        private String presentfeeused;

        private String recvfeeused;

        private String respcode;

        private String respdesc;

        private String serialnumber;

        private String userid;

        private String writeofffee;

        @JsonProperty("billinfo")
        private List<BillInfo> billInfo;

        @JsonProperty("billinfoitem")
        private List<List<BillInfo>> billInfoItem;

        @JsonProperty("scoreinfo")
        private List<ScoreInfo> scoreInfo;

        public String getActionfeeused() {
            return actionfeeused;
        }

        public void setActionfeeused(String actionfeeused) {
            this.actionfeeused = actionfeeused;
        }

        public String getAdjustfee() {
            return adjustfee;
        }

        public void setAdjustfee(String adjustfee) {
            this.adjustfee = adjustfee;
        }

        public String getAllfee() {
            return allfee;
        }

        public void setAllfee(String allfee) {
            this.allfee = allfee;
        }

        public String getAreacode() {
            return areacode;
        }

        public void setAreacode(String areacode) {
            this.areacode = areacode;
        }

        public String getBackfee() {
            return backfee;
        }

        public void setBackfee(String backfee) {
            this.backfee = backfee;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getBusiorder() {
            return busiorder;
        }

        public void setBusiorder(String busiorder) {
            this.busiorder = busiorder;
        }

        public String getCycleid() {
            return cycleid;
        }

        public void setCycleid(String cycleid) {
            this.cycleid = cycleid;
        }

        public String getDeratefee() {
            return deratefee;
        }

        public void setDeratefee(String deratefee) {
            this.deratefee = deratefee;
        }

        public String getIssuccess() {
            return issuccess;
        }

        public void setIssuccess(String issuccess) {
            this.issuccess = issuccess;
        }

        public String getPresentfeeused() {
            return presentfeeused;
        }

        public void setPresentfeeused(String presentfeeused) {
            this.presentfeeused = presentfeeused;
        }

        public String getRecvfeeused() {
            return recvfeeused;
        }

        public void setRecvfeeused(String recvfeeused) {
            this.recvfeeused = recvfeeused;
        }

        public String getRespcode() {
            return respcode;
        }

        public void setRespcode(String respcode) {
            this.respcode = respcode;
        }

        public String getRespdesc() {
            return respdesc;
        }

        public void setRespdesc(String respdesc) {
            this.respdesc = respdesc;
        }

        public String getSerialnumber() {
            return serialnumber;
        }

        public void setSerialnumber(String serialnumber) {
            this.serialnumber = serialnumber;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getWriteofffee() {
            return writeofffee;
        }

        public void setWriteofffee(String writeofffee) {
            this.writeofffee = writeofffee;
        }

        public List<BillInfo> getBillInfo() {
            return billInfo;
        }

        public void setBillInfo(List<BillInfo> billInfo) {
            this.billInfo = billInfo;
        }

        public List<List<BillInfo>> getBillInfoItem() {
            return billInfoItem;
        }

        public void setBillInfoItem(List<List<BillInfo>> billInfoItem) {
            this.billInfoItem = billInfoItem;
        }

        public List<ScoreInfo> getScoreInfo() {
            return scoreInfo;
        }

        public void setScoreInfo(List<ScoreInfo> scoreInfo) {
            this.scoreInfo = scoreInfo;
        }
    }

    public static class BillInfo {

        private String adjustafter;

        private String adjustbefore;

        private String balance;

        private String discnt;

        private String fee;

        private String integrateitem;

        private String integrateitemcode;

        private String parentitemcode;

        private String usedvalue;

        private String level;

        public String getAdjustafter() {
            return adjustafter;
        }

        public void setAdjustafter(String adjustafter) {
            this.adjustafter = adjustafter;
        }

        public String getAdjustbefore() {
            return adjustbefore;
        }

        public void setAdjustbefore(String adjustbefore) {
            this.adjustbefore = adjustbefore;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getDiscnt() {
            return discnt;
        }

        public void setDiscnt(String discnt) {
            this.discnt = discnt;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getIntegrateitem() {
            return integrateitem;
        }

        public void setIntegrateitem(String integrateitem) {
            this.integrateitem = integrateitem;
        }

        public String getIntegrateitemcode() {
            return integrateitemcode;
        }

        public void setIntegrateitemcode(String integrateitemcode) {
            this.integrateitemcode = integrateitemcode;
        }

        public String getParentitemcode() {
            return parentitemcode;
        }

        public void setParentitemcode(String parentitemcode) {
            this.parentitemcode = parentitemcode;
        }

        public String getUsedvalue() {
            return usedvalue;
        }

        public void setUsedvalue(String usedvalue) {
            this.usedvalue = usedvalue;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }

    public static class ScoreInfo {

        private String rsrvscore1;

        private String rsrvscore2;

        private String rsrvscore3;

        private String rsrvscoreadjust;

        private String scoreusevalue;

        public String getRsrvscore1() {
            return rsrvscore1;
        }

        public void setRsrvscore1(String rsrvscore1) {
            this.rsrvscore1 = rsrvscore1;
        }

        public String getRsrvscore2() {
            return rsrvscore2;
        }

        public void setRsrvscore2(String rsrvscore2) {
            this.rsrvscore2 = rsrvscore2;
        }

        public String getRsrvscore3() {
            return rsrvscore3;
        }

        public void setRsrvscore3(String rsrvscore3) {
            this.rsrvscore3 = rsrvscore3;
        }

        public String getRsrvscoreadjust() {
            return rsrvscoreadjust;
        }

        public void setRsrvscoreadjust(String rsrvscoreadjust) {
            this.rsrvscoreadjust = rsrvscoreadjust;
        }

        public String getScoreusevalue() {
            return scoreusevalue;
        }

        public void setScoreusevalue(String scoreusevalue) {
            this.scoreusevalue = scoreusevalue;
        }
    }

}
