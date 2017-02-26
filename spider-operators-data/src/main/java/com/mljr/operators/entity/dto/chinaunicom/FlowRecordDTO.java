package com.mljr.operators.entity.dto.chinaunicom;

import java.io.Serializable;
import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/25
 */
public class FlowRecordDTO implements Serializable {

    private static final long serialVersionUID = -925294065484412982L;

    private String querynowdate;
    private String queryDateScope;
    private boolean isSuccess;
    private PageMapBean pageMap;
    private int totalRecord;
    private String msg;
    private boolean timeresult;

    public String getQuerynowdate() {
        return querynowdate;
    }

    public void setQuerynowdate(String querynowdate) {
        this.querynowdate = querynowdate;
    }

    public String getQueryDateScope() {
        return queryDateScope;
    }

    public void setQueryDateScope(String queryDateScope) {
        this.queryDateScope = queryDateScope;
    }

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public PageMapBean getPageMap() {
        return pageMap;
    }

    public void setPageMap(PageMapBean pageMap) {
        this.pageMap = pageMap;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isTimeresult() {
        return timeresult;
    }

    public void setTimeresult(boolean timeresult) {
        this.timeresult = timeresult;
    }

    public static class PageMapBean {

        private int totalCount;
        private int pageNo;
        private int pageSize;
        private int totalPages;
        private List<ResultBean> result;

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

        public static class ResultBean {

            private String begintime;
            private String biztype;
            private String flowtype;
            private String bizname;
            private String flowname;
            private String totaltraffic;
            private String durationtime;
            private String rattype;
            private String apn;
            private String uptraffic;
            private String downtraffic;
            private String clientip;
            private String accessip;
            private String useragent;
            private String featinfo;
            private String domainname;

            public String getBegintime() {
                return begintime;
            }

            public void setBegintime(String begintime) {
                this.begintime = begintime;
            }

            public String getBiztype() {
                return biztype;
            }

            public void setBiztype(String biztype) {
                this.biztype = biztype;
            }

            public String getFlowtype() {
                return flowtype;
            }

            public void setFlowtype(String flowtype) {
                this.flowtype = flowtype;
            }

            public String getBizname() {
                return bizname;
            }

            public void setBizname(String bizname) {
                this.bizname = bizname;
            }

            public String getFlowname() {
                return flowname;
            }

            public void setFlowname(String flowname) {
                this.flowname = flowname;
            }

            public String getTotaltraffic() {
                return totaltraffic;
            }

            public void setTotaltraffic(String totaltraffic) {
                this.totaltraffic = totaltraffic;
            }

            public String getDurationtime() {
                return durationtime;
            }

            public void setDurationtime(String durationtime) {
                this.durationtime = durationtime;
            }

            public String getRattype() {
                return rattype;
            }

            public void setRattype(String rattype) {
                this.rattype = rattype;
            }

            public String getApn() {
                return apn;
            }

            public void setApn(String apn) {
                this.apn = apn;
            }

            public String getUptraffic() {
                return uptraffic;
            }

            public void setUptraffic(String uptraffic) {
                this.uptraffic = uptraffic;
            }

            public String getDowntraffic() {
                return downtraffic;
            }

            public void setDowntraffic(String downtraffic) {
                this.downtraffic = downtraffic;
            }

            public String getClientip() {
                return clientip;
            }

            public void setClientip(String clientip) {
                this.clientip = clientip;
            }

            public String getAccessip() {
                return accessip;
            }

            public void setAccessip(String accessip) {
                this.accessip = accessip;
            }

            public String getUseragent() {
                return useragent;
            }

            public void setUseragent(String useragent) {
                this.useragent = useragent;
            }

            public String getFeatinfo() {
                return featinfo;
            }

            public void setFeatinfo(String featinfo) {
                this.featinfo = featinfo;
            }

            public String getDomainname() {
                return domainname;
            }

            public void setDomainname(String domainname) {
                this.domainname = domainname;
            }
        }
    }
}
