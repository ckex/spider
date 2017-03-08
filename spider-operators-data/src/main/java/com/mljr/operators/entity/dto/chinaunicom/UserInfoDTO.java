package com.mljr.operators.entity.dto.chinaunicom;

import java.io.Serializable;
import java.util.List;

/**
 * @author gaoxi
 * @Time 2017/2/17
 * 用户信息
 */
public class UserInfoDTO implements Serializable {

    private static final long serialVersionUID = 219896899577957636L;

    private ResultBean result;
    private int bindNumber;
    private PackageInfoBeanXX packageInfo;
    private UserInfoBean userInfo;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getBindNumber() {
        return bindNumber;
    }

    public void setBindNumber(int bindNumber) {
        this.bindNumber = bindNumber;
    }

    public PackageInfoBeanXX getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfoBeanXX packageInfo) {
        this.packageInfo = packageInfo;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public static class ResultBean {

        private String pukcode;
        private MyDetailBean MyDetail;
        private String payType;

        public String getPukcode() {
            return pukcode;
        }

        public void setPukcode(String pukcode) {
            this.pukcode = pukcode;
        }

        public MyDetailBean getMyDetail() {
            return MyDetail;
        }

        public void setMyDetail(MyDetailBean MyDetail) {
            this.MyDetail = MyDetail;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public static class MyDetailBean {

            private String usernumber;
            private String certnum;
            private String sendemail;
            private String opendate;
            private String paytype;
            private String citycode;
            private String acctid;
            private String custid;
            private String custname;
            private String brand;
            private String productid;
            private String productname;
            private String custlvl;
            private String subscrbstat;
            private String laststatdate;
            private String certtype;
            private String custsex;
            private String certaddr;
            private String landlvl;
            private String roamstat;
            private String sendflag;
            private String subscrbid;
            private String simcard;
            private String managername;
            private String managercontact;
            private String sendname;
            private String sendpost;
            private String sendaddr;
            private String vpnname;
            private String creditvale;
            private String transid;
            private String busiorder;
            private String rspcode;
            private String rspdesc;
            private String rspts;
            private String rspsign;
            private String trxid;

            public String getUsernumber() {
                return usernumber;
            }

            public void setUsernumber(String usernumber) {
                this.usernumber = usernumber;
            }

            public String getCertnum() {
                return certnum;
            }

            public void setCertnum(String certnum) {
                this.certnum = certnum;
            }

            public String getSendemail() {
                return sendemail;
            }

            public void setSendemail(String sendemail) {
                this.sendemail = sendemail;
            }

            public String getOpendate() {
                return opendate;
            }

            public void setOpendate(String opendate) {
                this.opendate = opendate;
            }

            public String getPaytype() {
                return paytype;
            }

            public void setPaytype(String paytype) {
                this.paytype = paytype;
            }

            public String getCitycode() {
                return citycode;
            }

            public void setCitycode(String citycode) {
                this.citycode = citycode;
            }

            public String getAcctid() {
                return acctid;
            }

            public void setAcctid(String acctid) {
                this.acctid = acctid;
            }

            public String getCustid() {
                return custid;
            }

            public void setCustid(String custid) {
                this.custid = custid;
            }

            public String getCustname() {
                return custname;
            }

            public void setCustname(String custname) {
                this.custname = custname;
            }

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public String getProductid() {
                return productid;
            }

            public void setProductid(String productid) {
                this.productid = productid;
            }

            public String getProductname() {
                return productname;
            }

            public void setProductname(String productname) {
                this.productname = productname;
            }

            public String getCustlvl() {
                return custlvl;
            }

            public void setCustlvl(String custlvl) {
                this.custlvl = custlvl;
            }

            public String getSubscrbstat() {
                return subscrbstat;
            }

            public void setSubscrbstat(String subscrbstat) {
                this.subscrbstat = subscrbstat;
            }

            public String getLaststatdate() {
                return laststatdate;
            }

            public void setLaststatdate(String laststatdate) {
                this.laststatdate = laststatdate;
            }

            public String getCerttype() {
                return certtype;
            }

            public void setCerttype(String certtype) {
                this.certtype = certtype;
            }

            public String getCustsex() {
                return custsex;
            }

            public void setCustsex(String custsex) {
                this.custsex = custsex;
            }

            public String getCertaddr() {
                return certaddr;
            }

            public void setCertaddr(String certaddr) {
                this.certaddr = certaddr;
            }

            public String getLandlvl() {
                return landlvl;
            }

            public void setLandlvl(String landlvl) {
                this.landlvl = landlvl;
            }

            public String getRoamstat() {
                return roamstat;
            }

            public void setRoamstat(String roamstat) {
                this.roamstat = roamstat;
            }

            public String getSendflag() {
                return sendflag;
            }

            public void setSendflag(String sendflag) {
                this.sendflag = sendflag;
            }

            public String getSubscrbid() {
                return subscrbid;
            }

            public void setSubscrbid(String subscrbid) {
                this.subscrbid = subscrbid;
            }

            public String getSimcard() {
                return simcard;
            }

            public void setSimcard(String simcard) {
                this.simcard = simcard;
            }

            public String getManagername() {
                return managername;
            }

            public void setManagername(String managername) {
                this.managername = managername;
            }

            public String getManagercontact() {
                return managercontact;
            }

            public void setManagercontact(String managercontact) {
                this.managercontact = managercontact;
            }

            public String getSendname() {
                return sendname;
            }

            public void setSendname(String sendname) {
                this.sendname = sendname;
            }

            public String getSendpost() {
                return sendpost;
            }

            public void setSendpost(String sendpost) {
                this.sendpost = sendpost;
            }

            public String getSendaddr() {
                return sendaddr;
            }

            public void setSendaddr(String sendaddr) {
                this.sendaddr = sendaddr;
            }

            public String getVpnname() {
                return vpnname;
            }

            public void setVpnname(String vpnname) {
                this.vpnname = vpnname;
            }

            public String getCreditvale() {
                return creditvale;
            }

            public void setCreditvale(String creditvale) {
                this.creditvale = creditvale;
            }

            public String getTransid() {
                return transid;
            }

            public void setTransid(String transid) {
                this.transid = transid;
            }

            public String getBusiorder() {
                return busiorder;
            }

            public void setBusiorder(String busiorder) {
                this.busiorder = busiorder;
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

            public String getRspts() {
                return rspts;
            }

            public void setRspts(String rspts) {
                this.rspts = rspts;
            }

            public String getRspsign() {
                return rspsign;
            }

            public void setRspsign(String rspsign) {
                this.rspsign = rspsign;
            }

            public String getTrxid() {
                return trxid;
            }

            public void setTrxid(String trxid) {
                this.trxid = trxid;
            }
        }
    }

    public static class PackageInfoBeanXX {

        private boolean success;
        private String busiOrder;
        private String respCode;
        private String respDesc;
        private List<ProductInfoBean> productInfo;
        private List<NextProductInfoBean> nextProductInfo;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getBusiOrder() {
            return busiOrder;
        }

        public void setBusiOrder(String busiOrder) {
            this.busiOrder = busiOrder;
        }

        public String getRespCode() {
            return respCode;
        }

        public void setRespCode(String respCode) {
            this.respCode = respCode;
        }

        public String getRespDesc() {
            return respDesc;
        }

        public void setRespDesc(String respDesc) {
            this.respDesc = respDesc;
        }

        public List<ProductInfoBean> getProductInfo() {
            return productInfo;
        }

        public void setProductInfo(List<ProductInfoBean> productInfo) {
            this.productInfo = productInfo;
        }

        public List<NextProductInfoBean> getNextProductInfo() {
            return nextProductInfo;
        }

        public void setNextProductInfo(List<NextProductInfoBean> nextProductInfo) {
            this.nextProductInfo = nextProductInfo;
        }

        public static class ProductInfoBean {

            private String productName;
            private String productId;
            private String productType;
            private String productFee;
            private List<PackageInfoBean> packageInfo;

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getProductType() {
                return productType;
            }

            public void setProductType(String productType) {
                this.productType = productType;
            }

            public String getProductFee() {
                return productFee;
            }

            public void setProductFee(String productFee) {
                this.productFee = productFee;
            }

            public List<PackageInfoBean> getPackageInfo() {
                return packageInfo;
            }

            public void setPackageInfo(List<PackageInfoBean> packageInfo) {
                this.packageInfo = packageInfo;
            }

            public static class PackageInfoBean {

                private String packageName;
                private String packageType;
                private String packageId;
                private List<DiscntInfoBean> discntInfo;

                public String getPackageName() {
                    return packageName;
                }

                public void setPackageName(String packageName) {
                    this.packageName = packageName;
                }

                public String getPackageType() {
                    return packageType;
                }

                public void setPackageType(String packageType) {
                    this.packageType = packageType;
                }

                public String getPackageId() {
                    return packageId;
                }

                public void setPackageId(String packageId) {
                    this.packageId = packageId;
                }

                public List<DiscntInfoBean> getDiscntInfo() {
                    return discntInfo;
                }

                public void setDiscntInfo(List<DiscntInfoBean> discntInfo) {
                    this.discntInfo = discntInfo;
                }

                public static class DiscntInfoBean {

                    private String discntId;
                    private String discntName;
                    private String discntFee;

                    public String getDiscntId() {
                        return discntId;
                    }

                    public void setDiscntId(String discntId) {
                        this.discntId = discntId;
                    }

                    public String getDiscntName() {
                        return discntName;
                    }

                    public void setDiscntName(String discntName) {
                        this.discntName = discntName;
                    }

                    public String getDiscntFee() {
                        return discntFee;
                    }

                    public void setDiscntFee(String discntFee) {
                        this.discntFee = discntFee;
                    }
                }
            }
        }

        public static class NextProductInfoBean {

            private String productName;
            private String productId;
            private String productType;
            private List<PackageInfoBeanX> packageInfo;

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getProductType() {
                return productType;
            }

            public void setProductType(String productType) {
                this.productType = productType;
            }

            public List<PackageInfoBeanX> getPackageInfo() {
                return packageInfo;
            }

            public void setPackageInfo(List<PackageInfoBeanX> packageInfo) {
                this.packageInfo = packageInfo;
            }

            public static class PackageInfoBeanX {

                private String packageName;
                private String packageType;
                private String packageId;
                private List<DiscntInfoBeanX> discntInfo;

                public String getPackageName() {
                    return packageName;
                }

                public void setPackageName(String packageName) {
                    this.packageName = packageName;
                }

                public String getPackageType() {
                    return packageType;
                }

                public void setPackageType(String packageType) {
                    this.packageType = packageType;
                }

                public String getPackageId() {
                    return packageId;
                }

                public void setPackageId(String packageId) {
                    this.packageId = packageId;
                }

                public List<DiscntInfoBeanX> getDiscntInfo() {
                    return discntInfo;
                }

                public void setDiscntInfo(List<DiscntInfoBeanX> discntInfo) {
                    this.discntInfo = discntInfo;
                }

                public static class DiscntInfoBeanX {

                    private String discntId;
                    private String discntName;
                    private String discntFee;

                    public String getDiscntId() {
                        return discntId;
                    }

                    public void setDiscntId(String discntId) {
                        this.discntId = discntId;
                    }

                    public String getDiscntName() {
                        return discntName;
                    }

                    public void setDiscntName(String discntName) {
                        this.discntName = discntName;
                    }

                    public String getDiscntFee() {
                        return discntFee;
                    }

                    public void setDiscntFee(String discntFee) {
                        this.discntFee = discntFee;
                    }
                }
            }
        }
    }

    public static class UserInfoBean {

        private Object vip_level;
        private String packageName;
        private String userNettype;
        private Object is_vip;
        private String province;
        private String custlvl;
        private String currentId;
        private String openDate;
        private String loginType;
        private String mobile;

        public Object getVip_level() {
            return vip_level;
        }

        public void setVip_level(Object vip_level) {
            this.vip_level = vip_level;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getUserNettype() {
            return userNettype;
        }

        public void setUserNettype(String userNettype) {
            this.userNettype = userNettype;
        }

        public Object getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(Object is_vip) {
            this.is_vip = is_vip;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCustlvl() {
            return custlvl;
        }

        public void setCustlvl(String custlvl) {
            this.custlvl = custlvl;
        }

        public String getCurrentId() {
            return currentId;
        }

        public void setCurrentId(String currentId) {
            this.currentId = currentId;
        }

        public String getOpenDate() {
            return openDate;
        }

        public void setOpenDate(String openDate) {
            this.openDate = openDate;
        }

        public String getLoginType() {
            return loginType;
        }

        public void setLoginType(String loginType) {
            this.loginType = loginType;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}


