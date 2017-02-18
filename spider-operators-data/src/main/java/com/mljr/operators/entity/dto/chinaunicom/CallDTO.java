package com.mljr.operators.entity.dto.chinaunicom;

import java.io.Serializable;
import java.util.List;

/**
 * @author gaoxi
 * @Time 2017/2/17
 */
public class CallDTO implements Serializable {

    private static final long serialVersionUID = -643777061758304658L;

    private Integer totalRecord;

    private CallPage pageMap;

    public Integer getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
    }

    public void setPageMap(CallPage pageMap) {
        this.pageMap = pageMap;
    }

    public CallPage getPageMap() {
        return pageMap;
    }

    public static class CallPage {

        private List<CallDetailDTO> result;

        private Integer totalCount;

        private Integer pageNo;

        private Integer pageSize;

        private Integer totalPages;

        public List<CallDetailDTO> getResult() {
            return result;
        }

        public void setResult(List<CallDetailDTO> result) {
            this.result = result;
        }

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
    }

    public static class CallDetailDTO {

        private String businesstype;

        private String longtype;

        private String thtype;

        private String calledhome;

        private String cellid;

        private String thtypeName;

        private String twoplusfee;

        private String calllonghour;

        private String calldate;

        private String calltime;

        private String totalfee;

        private String calltype;

        private String othernum;

        private String otherarea;

        private String romatype;

        private String homearea;

        private String homenum;

        private String calltypeName;

        private String landtype;

        private String romatypeName;

        private String homeareaName;

        private String nativefee;

        private String landfee;

        private String roamfee;

        private String deratefee;

        private String otherfee;

        public String getBusinesstype() {
            return businesstype;
        }

        public void setBusinesstype(String businesstype) {
            this.businesstype = businesstype;
        }

        public String getLongtype() {
            return longtype;
        }

        public void setLongtype(String longtype) {
            this.longtype = longtype;
        }

        public String getThtype() {
            return thtype;
        }

        public void setThtype(String thtype) {
            this.thtype = thtype;
        }

        public String getCalledhome() {
            return calledhome;
        }

        public void setCalledhome(String calledhome) {
            this.calledhome = calledhome;
        }

        public String getCellid() {
            return cellid;
        }

        public void setCellid(String cellid) {
            this.cellid = cellid;
        }

        public String getThtypeName() {
            return thtypeName;
        }

        public void setThtypeName(String thtypeName) {
            this.thtypeName = thtypeName;
        }

        public String getTwoplusfee() {
            return twoplusfee;
        }

        public void setTwoplusfee(String twoplusfee) {
            this.twoplusfee = twoplusfee;
        }

        public String getCalllonghour() {
            return calllonghour;
        }

        public void setCalllonghour(String calllonghour) {
            this.calllonghour = calllonghour;
        }

        public String getCalldate() {
            return calldate;
        }

        public void setCalldate(String calldate) {
            this.calldate = calldate;
        }

        public String getCalltime() {
            return calltime;
        }

        public void setCalltime(String calltime) {
            this.calltime = calltime;
        }

        public String getTotalfee() {
            return totalfee;
        }

        public void setTotalfee(String totalfee) {
            this.totalfee = totalfee;
        }

        public String getCalltype() {
            return calltype;
        }

        public void setCalltype(String calltype) {
            this.calltype = calltype;
        }

        public String getOthernum() {
            return othernum;
        }

        public void setOthernum(String othernum) {
            this.othernum = othernum;
        }

        public String getOtherarea() {
            return otherarea;
        }

        public void setOtherarea(String otherarea) {
            this.otherarea = otherarea;
        }

        public String getRomatype() {
            return romatype;
        }

        public void setRomatype(String romatype) {
            this.romatype = romatype;
        }

        public String getHomearea() {
            return homearea;
        }

        public void setHomearea(String homearea) {
            this.homearea = homearea;
        }

        public String getHomenum() {
            return homenum;
        }

        public void setHomenum(String homenum) {
            this.homenum = homenum;
        }

        public String getCalltypeName() {
            return calltypeName;
        }

        public void setCalltypeName(String calltypeName) {
            this.calltypeName = calltypeName;
        }

        public String getLandtype() {
            return landtype;
        }

        public void setLandtype(String landtype) {
            this.landtype = landtype;
        }

        public String getRomatypeName() {
            return romatypeName;
        }

        public void setRomatypeName(String romatypeName) {
            this.romatypeName = romatypeName;
        }

        public String getHomeareaName() {
            return homeareaName;
        }

        public void setHomeareaName(String homeareaName) {
            this.homeareaName = homeareaName;
        }

        public String getNativefee() {
            return nativefee;
        }

        public void setNativefee(String nativefee) {
            this.nativefee = nativefee;
        }

        public String getLandfee() {
            return landfee;
        }

        public void setLandfee(String landfee) {
            this.landfee = landfee;
        }

        public String getRoamfee() {
            return roamfee;
        }

        public void setRoamfee(String roamfee) {
            this.roamfee = roamfee;
        }

        public String getDeratefee() {
            return deratefee;
        }

        public void setDeratefee(String deratefee) {
            this.deratefee = deratefee;
        }

        public String getOtherfee() {
            return otherfee;
        }

        public void setOtherfee(String otherfee) {
            this.otherfee = otherfee;
        }
    }
}
