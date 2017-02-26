package com.mljr.operators.entity.dto.chinaunicom;

import java.io.Serializable;
import java.util.List;

/**
 * @author gaoxi
 * @Time 2017/2/17
 */
public class CallDTO implements Serializable {

    private static final long serialVersionUID = -643777061758304658L;

    private int totalRecord;
    private PageMapBean pageMap;

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public PageMapBean getPageMap() {
        return pageMap;
    }

    public void setPageMap(PageMapBean pageMap) {
        this.pageMap = pageMap;
    }

    public static class PageMapBean {

        private int totalPages;
        private int pageSize;
        private int totalCount;
        private int pageNo;
        private List<ResultBean> result;

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

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

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }

        public static class ResultBean {

            private String othernum;
            private String twoplusfee;
            private String otherfee;
            private String roamfee;
            private String cellid;
            private String thtype;
            private String landtype;
            private String businesstype;
            private String homeareaName;
            private String romatypeName;
            private String totalfee;
            private String otherarea;
            private String thtypeName;
            private String calledhome;
            private String calltype;
            private String landfee;
            private String longtype;
            private String calltypeName;
            private String homenum;
            private String calllonghour;
            private String homearea;
            private String nativefee;
            private String romatype;
            private String calltime;
            private String calldate;
            private String deratefee;

            public String getOthernum() {
                return othernum;
            }

            public void setOthernum(String othernum) {
                this.othernum = othernum;
            }

            public String getTwoplusfee() {
                return twoplusfee;
            }

            public void setTwoplusfee(String twoplusfee) {
                this.twoplusfee = twoplusfee;
            }

            public String getOtherfee() {
                return otherfee;
            }

            public void setOtherfee(String otherfee) {
                this.otherfee = otherfee;
            }

            public String getRoamfee() {
                return roamfee;
            }

            public void setRoamfee(String roamfee) {
                this.roamfee = roamfee;
            }

            public String getCellid() {
                return cellid;
            }

            public void setCellid(String cellid) {
                this.cellid = cellid;
            }

            public String getThtype() {
                return thtype;
            }

            public void setThtype(String thtype) {
                this.thtype = thtype;
            }

            public String getLandtype() {
                return landtype;
            }

            public void setLandtype(String landtype) {
                this.landtype = landtype;
            }

            public String getBusinesstype() {
                return businesstype;
            }

            public void setBusinesstype(String businesstype) {
                this.businesstype = businesstype;
            }

            public String getHomeareaName() {
                return homeareaName;
            }

            public void setHomeareaName(String homeareaName) {
                this.homeareaName = homeareaName;
            }

            public String getRomatypeName() {
                return romatypeName;
            }

            public void setRomatypeName(String romatypeName) {
                this.romatypeName = romatypeName;
            }

            public String getTotalfee() {
                return totalfee;
            }

            public void setTotalfee(String totalfee) {
                this.totalfee = totalfee;
            }

            public String getOtherarea() {
                return otherarea;
            }

            public void setOtherarea(String otherarea) {
                this.otherarea = otherarea;
            }

            public String getThtypeName() {
                return thtypeName;
            }

            public void setThtypeName(String thtypeName) {
                this.thtypeName = thtypeName;
            }

            public String getCalledhome() {
                return calledhome;
            }

            public void setCalledhome(String calledhome) {
                this.calledhome = calledhome;
            }

            public String getCalltype() {
                return calltype;
            }

            public void setCalltype(String calltype) {
                this.calltype = calltype;
            }

            public String getLandfee() {
                return landfee;
            }

            public void setLandfee(String landfee) {
                this.landfee = landfee;
            }

            public String getLongtype() {
                return longtype;
            }

            public void setLongtype(String longtype) {
                this.longtype = longtype;
            }

            public String getCalltypeName() {
                return calltypeName;
            }

            public void setCalltypeName(String calltypeName) {
                this.calltypeName = calltypeName;
            }

            public String getHomenum() {
                return homenum;
            }

            public void setHomenum(String homenum) {
                this.homenum = homenum;
            }

            public String getCalllonghour() {
                return calllonghour;
            }

            public void setCalllonghour(String calllonghour) {
                this.calllonghour = calllonghour;
            }

            public String getHomearea() {
                return homearea;
            }

            public void setHomearea(String homearea) {
                this.homearea = homearea;
            }

            public String getNativefee() {
                return nativefee;
            }

            public void setNativefee(String nativefee) {
                this.nativefee = nativefee;
            }

            public String getRomatype() {
                return romatype;
            }

            public void setRomatype(String romatype) {
                this.romatype = romatype;
            }

            public String getCalltime() {
                return calltime;
            }

            public void setCalltime(String calltime) {
                this.calltime = calltime;
            }

            public String getCalldate() {
                return calldate;
            }

            public void setCalldate(String calldate) {
                this.calldate = calldate;
            }

            public String getDeratefee() {
                return deratefee;
            }

            public void setDeratefee(String deratefee) {
                this.deratefee = deratefee;
            }
        }
    }
}
