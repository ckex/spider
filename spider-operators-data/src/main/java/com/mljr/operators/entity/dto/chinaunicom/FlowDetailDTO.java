package com.mljr.operators.entity.dto.chinaunicom;

import java.io.Serializable;
import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/25
 */
public class FlowDetailDTO implements Serializable {

    private static final long serialVersionUID = -6814890454934903664L;

    private String busiorder;
    private String ecsbusiorder;
    private boolean existexception;
    private boolean isnotsuccess;
    private boolean issuccess;
    private String rspcode;
    private String rspdesc;
    private String rspsign;
    private String rspts;
    private ResultBean result;
    private int totalpage;
    private String transid;
    private String trxid;
    private List<PagelistBean> pagelist;

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

    public boolean isExistexception() {
        return existexception;
    }

    public void setExistexception(boolean existexception) {
        this.existexception = existexception;
    }

    public boolean isIsnotsuccess() {
        return isnotsuccess;
    }

    public void setIsnotsuccess(boolean isnotsuccess) {
        this.isnotsuccess = isnotsuccess;
    }

    public boolean isIssuccess() {
        return issuccess;
    }

    public void setIssuccess(boolean issuccess) {
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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
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

    public List<PagelistBean> getPagelist() {
        return pagelist;
    }

    public void setPagelist(List<PagelistBean> pagelist) {
        this.pagelist = pagelist;
    }

    public static class ResultBean {

        private String alltotalfee;
        private String busiorder;
        private boolean issuccess;
        private String querynowdate;
        private String respcode;
        private String respdesc;
        private String totalrecord;
        private String totalsm;
        private List<CdrinfoBean> cdrinfo;

        public String getAlltotalfee() {
            return alltotalfee;
        }

        public void setAlltotalfee(String alltotalfee) {
            this.alltotalfee = alltotalfee;
        }

        public String getBusiorder() {
            return busiorder;
        }

        public void setBusiorder(String busiorder) {
            this.busiorder = busiorder;
        }

        public boolean isIssuccess() {
            return issuccess;
        }

        public void setIssuccess(boolean issuccess) {
            this.issuccess = issuccess;
        }

        public String getQuerynowdate() {
            return querynowdate;
        }

        public void setQuerynowdate(String querynowdate) {
            this.querynowdate = querynowdate;
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

        public String getTotalrecord() {
            return totalrecord;
        }

        public void setTotalrecord(String totalrecord) {
            this.totalrecord = totalrecord;
        }

        public String getTotalsm() {
            return totalsm;
        }

        public void setTotalsm(String totalsm) {
            this.totalsm = totalsm;
        }

        public List<CdrinfoBean> getCdrinfo() {
            return cdrinfo;
        }

        public void setCdrinfo(List<CdrinfoBean> cdrinfo) {
            this.cdrinfo = cdrinfo;
        }

        public static class CdrinfoBean {
            private String currentpage;
            private String currentpagenum;
            private List<CdrdetailinfoBean> cdrdetailinfo;

            public String getCurrentpage() {
                return currentpage;
            }

            public void setCurrentpage(String currentpage) {
                this.currentpage = currentpage;
            }

            public String getCurrentpagenum() {
                return currentpagenum;
            }

            public void setCurrentpagenum(String currentpagenum) {
                this.currentpagenum = currentpagenum;
            }

            public List<CdrdetailinfoBean> getCdrdetailinfo() {
                return cdrdetailinfo;
            }

            public void setCdrdetailinfo(List<CdrdetailinfoBean> cdrdetailinfo) {
                this.cdrdetailinfo = cdrdetailinfo;
            }

            public static class CdrdetailinfoBean {

                private String begindate;
                private String begindateformat;
                private String begintime;
                private String begintimeformat;
                private String deratefee;
                private String fee;
                private String forwardtype;
                private String forwardtypeformat;
                private String homearea;
                private String longhour;
                private String nettype;
                private String nettypeformat;
                private String pertotalsm;
                private String receivebytes;
                private String roamstat;
                private String sendbytes;
                private String svcname;
                private String totalfee;

                public String getBegindate() {
                    return begindate;
                }

                public void setBegindate(String begindate) {
                    this.begindate = begindate;
                }

                public String getBegindateformat() {
                    return begindateformat;
                }

                public void setBegindateformat(String begindateformat) {
                    this.begindateformat = begindateformat;
                }

                public String getBegintime() {
                    return begintime;
                }

                public void setBegintime(String begintime) {
                    this.begintime = begintime;
                }

                public String getBegintimeformat() {
                    return begintimeformat;
                }

                public void setBegintimeformat(String begintimeformat) {
                    this.begintimeformat = begintimeformat;
                }

                public String getDeratefee() {
                    return deratefee;
                }

                public void setDeratefee(String deratefee) {
                    this.deratefee = deratefee;
                }

                public String getFee() {
                    return fee;
                }

                public void setFee(String fee) {
                    this.fee = fee;
                }

                public String getForwardtype() {
                    return forwardtype;
                }

                public void setForwardtype(String forwardtype) {
                    this.forwardtype = forwardtype;
                }

                public String getForwardtypeformat() {
                    return forwardtypeformat;
                }

                public void setForwardtypeformat(String forwardtypeformat) {
                    this.forwardtypeformat = forwardtypeformat;
                }

                public String getHomearea() {
                    return homearea;
                }

                public void setHomearea(String homearea) {
                    this.homearea = homearea;
                }

                public String getLonghour() {
                    return longhour;
                }

                public void setLonghour(String longhour) {
                    this.longhour = longhour;
                }

                public String getNettype() {
                    return nettype;
                }

                public void setNettype(String nettype) {
                    this.nettype = nettype;
                }

                public String getNettypeformat() {
                    return nettypeformat;
                }

                public void setNettypeformat(String nettypeformat) {
                    this.nettypeformat = nettypeformat;
                }

                public String getPertotalsm() {
                    return pertotalsm;
                }

                public void setPertotalsm(String pertotalsm) {
                    this.pertotalsm = pertotalsm;
                }

                public String getReceivebytes() {
                    return receivebytes;
                }

                public void setReceivebytes(String receivebytes) {
                    this.receivebytes = receivebytes;
                }

                public String getRoamstat() {
                    return roamstat;
                }

                public void setRoamstat(String roamstat) {
                    this.roamstat = roamstat;
                }

                public String getSendbytes() {
                    return sendbytes;
                }

                public void setSendbytes(String sendbytes) {
                    this.sendbytes = sendbytes;
                }

                public String getSvcname() {
                    return svcname;
                }

                public void setSvcname(String svcname) {
                    this.svcname = svcname;
                }

                public String getTotalfee() {
                    return totalfee;
                }

                public void setTotalfee(String totalfee) {
                    this.totalfee = totalfee;
                }
            }
        }
    }

    public static class PagelistBean {

        private String begindate;
        private String begindateformat;
        private String begintime;
        private String begintimeformat;
        private String deratefee;
        private String fee;
        private String forwardtype;
        private String forwardtypeformat;
        private String homearea;
        private String longhour;
        private String nettype;
        private String nettypeformat;
        private String pertotalsm;
        private String receivebytes;
        private String roamstat;
        private String sendbytes;
        private String svcname;
        private String totalfee;

        public String getBegindate() {
            return begindate;
        }

        public void setBegindate(String begindate) {
            this.begindate = begindate;
        }

        public String getBegindateformat() {
            return begindateformat;
        }

        public void setBegindateformat(String begindateformat) {
            this.begindateformat = begindateformat;
        }

        public String getBegintime() {
            return begintime;
        }

        public void setBegintime(String begintime) {
            this.begintime = begintime;
        }

        public String getBegintimeformat() {
            return begintimeformat;
        }

        public void setBegintimeformat(String begintimeformat) {
            this.begintimeformat = begintimeformat;
        }

        public String getDeratefee() {
            return deratefee;
        }

        public void setDeratefee(String deratefee) {
            this.deratefee = deratefee;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getForwardtype() {
            return forwardtype;
        }

        public void setForwardtype(String forwardtype) {
            this.forwardtype = forwardtype;
        }

        public String getForwardtypeformat() {
            return forwardtypeformat;
        }

        public void setForwardtypeformat(String forwardtypeformat) {
            this.forwardtypeformat = forwardtypeformat;
        }

        public String getHomearea() {
            return homearea;
        }

        public void setHomearea(String homearea) {
            this.homearea = homearea;
        }

        public String getLonghour() {
            return longhour;
        }

        public void setLonghour(String longhour) {
            this.longhour = longhour;
        }

        public String getNettype() {
            return nettype;
        }

        public void setNettype(String nettype) {
            this.nettype = nettype;
        }

        public String getNettypeformat() {
            return nettypeformat;
        }

        public void setNettypeformat(String nettypeformat) {
            this.nettypeformat = nettypeformat;
        }

        public String getPertotalsm() {
            return pertotalsm;
        }

        public void setPertotalsm(String pertotalsm) {
            this.pertotalsm = pertotalsm;
        }

        public String getReceivebytes() {
            return receivebytes;
        }

        public void setReceivebytes(String receivebytes) {
            this.receivebytes = receivebytes;
        }

        public String getRoamstat() {
            return roamstat;
        }

        public void setRoamstat(String roamstat) {
            this.roamstat = roamstat;
        }

        public String getSendbytes() {
            return sendbytes;
        }

        public void setSendbytes(String sendbytes) {
            this.sendbytes = sendbytes;
        }

        public String getSvcname() {
            return svcname;
        }

        public void setSvcname(String svcname) {
            this.svcname = svcname;
        }

        public String getTotalfee() {
            return totalfee;
        }

        public void setTotalfee(String totalfee) {
            this.totalfee = totalfee;
        }
    }
}
