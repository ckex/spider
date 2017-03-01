package com.mljr.operators.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by songchi on 17/3/1.
 */
public class ApiData {


    /**
     * status : success
     * update_time : 2015-08-25 19:33:00
     * request_args : [{"token":"2a38566356b84265965f468b7d8f99a4"},{"env":"www"}]
     * transactions : [{"token":"2a38566356b84265965f468b7d8f99a4","version":"1","datasource":"chinaunicom","calls":[{"update_time":"2015-08-25 19:04:03","place":"上海","other_cell_phone":"159****9882","subtotal":0,"start_time":"2015-03-02 17:10:15","cell_phone":"156****9149","init_type":"被叫","call_type":"本地通话","use_time":15},{"update_time":"2015-08-25 19:04:03","place":"上海","other_cell_phone":"186****0730","subtotal":0,"start_time":"2015-03-02 12:28:26","cell_phone":"156****9149","init_type":"主叫","call_type":"国内长途","use_time":65}],"basic":{"update_time":"2015-08-25 19:04:03","cell_phone":"156****9149","idcard":"3707****6736","reg_time":"2014-10-29 00:00:00","real_name":"王**"},"juid":"","transactions":[{"update_time":"2015-08-25 19:04:03","total_amt":136,"bill_cycle":"2015-04-01 00:00:00","pay_amt":null,"plan_amt":76,"cell_phone":"156****9149"},{"update_time":"2015-08-25 19:04:03","total_amt":136.1,"bill_cycle":"2015-03-01 00:00:00","pay_amt":null,"plan_amt":76,"cell_phone":"156****9149"}],"nets":[{"update_time":"2015-08-25 19:04:04","place":"上海","net_type":"3G网络","start_time":"2015-03-01 10:16:43","cell_phone":"156****9149","subtotal":6.76,"subflow":676,"use_time":488},{"update_time":"2015-08-25 19:04:04","place":"上海","net_type":"3G网络","start_time":"2015-03-01 10:16:41","cell_phone":"156****9149","subtotal":5.04,"subflow":504,"use_time":490}],"smses":[{"update_time":"2015-08-25 19:04:03","start_time":"2015-08-23 12:28:17","init_type":null,"place":"021","other_cell_phone":"182****8830","cell_phone":"156****9149","subtotal":0},{"update_time":"2015-08-25 19:04:03","start_time":"2015-08-23 12:26:45","init_type":null,"place":"021","other_cell_phone":"182****8830","cell_phone":"156****9149","subtotal":0}]}]
     * error_code : 31200
     * error_msg : 请求用户数据成功
     */

    private String status;
    private String update_time;
    private int error_code;
    private String error_msg;
    private List<RequestArgsBean> request_args;
    private List<TransactionsBeanX> transactions;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public List<RequestArgsBean> getRequest_args() {
        return request_args;
    }

    public void setRequest_args(List<RequestArgsBean> request_args) {
        this.request_args = request_args;
    }

    public List<TransactionsBeanX> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionsBeanX> transactions) {
        this.transactions = transactions;
    }

    public static class RequestArgsBean {
        /**
         * token : 2a38566356b84265965f468b7d8f99a4
         * env : www
         */

        private String token;
        private String env;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getEnv() {
            return env;
        }

        public void setEnv(String env) {
            this.env = env;
        }
    }

    public static class TransactionsBeanX {
        /**
         * token : 2a38566356b84265965f468b7d8f99a4
         * version : 1
         * datasource : chinaunicom
         * calls : [{"update_time":"2015-08-25 19:04:03","place":"上海","other_cell_phone":"159****9882","subtotal":0,"start_time":"2015-03-02 17:10:15","cell_phone":"156****9149","init_type":"被叫","call_type":"本地通话","use_time":15},{"update_time":"2015-08-25 19:04:03","place":"上海","other_cell_phone":"186****0730","subtotal":0,"start_time":"2015-03-02 12:28:26","cell_phone":"156****9149","init_type":"主叫","call_type":"国内长途","use_time":65}]
         * basic : {"update_time":"2015-08-25 19:04:03","cell_phone":"156****9149","idcard":"3707****6736","reg_time":"2014-10-29 00:00:00","real_name":"王**"}
         * juid :
         * transactions : [{"update_time":"2015-08-25 19:04:03","total_amt":136,"bill_cycle":"2015-04-01 00:00:00","pay_amt":null,"plan_amt":76,"cell_phone":"156****9149"},{"update_time":"2015-08-25 19:04:03","total_amt":136.1,"bill_cycle":"2015-03-01 00:00:00","pay_amt":null,"plan_amt":76,"cell_phone":"156****9149"}]
         * nets : [{"update_time":"2015-08-25 19:04:04","place":"上海","net_type":"3G网络","start_time":"2015-03-01 10:16:43","cell_phone":"156****9149","subtotal":6.76,"subflow":676,"use_time":488},{"update_time":"2015-08-25 19:04:04","place":"上海","net_type":"3G网络","start_time":"2015-03-01 10:16:41","cell_phone":"156****9149","subtotal":5.04,"subflow":504,"use_time":490}]
         * smses : [{"update_time":"2015-08-25 19:04:03","start_time":"2015-08-23 12:28:17","init_type":null,"place":"021","other_cell_phone":"182****8830","cell_phone":"156****9149","subtotal":0},{"update_time":"2015-08-25 19:04:03","start_time":"2015-08-23 12:26:45","init_type":null,"place":"021","other_cell_phone":"182****8830","cell_phone":"156****9149","subtotal":0}]
         */

        private String token;
        private String version;
        private String datasource;
        private BasicBean basic;
        private String juid;
        private List<CallsBean> calls;
        private List<TransactionsBean> transactions;
        private List<NetsBean> nets;
        private List<SmsesBean> smses;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getDatasource() {
            return datasource;
        }

        public void setDatasource(String datasource) {
            this.datasource = datasource;
        }

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public String getJuid() {
            return juid;
        }

        public void setJuid(String juid) {
            this.juid = juid;
        }

        public List<CallsBean> getCalls() {
            return calls;
        }

        public void setCalls(List<CallsBean> calls) {
            this.calls = calls;
        }

        public List<TransactionsBean> getTransactions() {
            return transactions;
        }

        public void setTransactions(List<TransactionsBean> transactions) {
            this.transactions = transactions;
        }

        public List<NetsBean> getNets() {
            return nets;
        }

        public void setNets(List<NetsBean> nets) {
            this.nets = nets;
        }

        public List<SmsesBean> getSmses() {
            return smses;
        }

        public void setSmses(List<SmsesBean> smses) {
            this.smses = smses;
        }

        public static class BasicBean {
            /**
             * update_time : 2015-08-25 19:04:03
             * cell_phone : 156****9149
             * idcard : 3707****6736
             * reg_time : 2014-10-29 00:00:00
             * real_name : 王**
             */

            private String update_time;
            private String cell_phone;
            private String idcard;
            private String reg_time;
            private String real_name;

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getCell_phone() {
                return cell_phone;
            }

            public void setCell_phone(String cell_phone) {
                this.cell_phone = cell_phone;
            }

            public String getIdcard() {
                return idcard;
            }

            public void setIdcard(String idcard) {
                this.idcard = idcard;
            }

            public String getReg_time() {
                return reg_time;
            }

            public void setReg_time(String reg_time) {
                this.reg_time = reg_time;
            }

            public String getReal_name() {
                return real_name;
            }

            public void setReal_name(String real_name) {
                this.real_name = real_name;
            }
        }

        public static class CallsBean {
            /**
             * update_time : 2015-08-25 19:04:03
             * place : 上海
             * other_cell_phone : 159****9882
             * subtotal : 0
             * start_time : 2015-03-02 17:10:15
             * cell_phone : 156****9149
             * init_type : 被叫
             * call_type : 本地通话
             * use_time : 15
             */

            private String update_time;
            private String place;
            private String other_cell_phone;
            private BigDecimal subtotal;
            private String start_time;
            private String cell_phone;
            private String init_type;
            private String call_type;
            private int use_time;

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getPlace() {
                return place;
            }

            public void setPlace(String place) {
                this.place = place;
            }

            public String getOther_cell_phone() {
                return other_cell_phone;
            }

            public void setOther_cell_phone(String other_cell_phone) {
                this.other_cell_phone = other_cell_phone;
            }

            public BigDecimal getSubtotal() {
                return subtotal;
            }

            public void setSubtotal(BigDecimal subtotal) {
                this.subtotal = subtotal;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getCell_phone() {
                return cell_phone;
            }

            public void setCell_phone(String cell_phone) {
                this.cell_phone = cell_phone;
            }

            public String getInit_type() {
                return init_type;
            }

            public void setInit_type(String init_type) {
                this.init_type = init_type;
            }

            public String getCall_type() {
                return call_type;
            }

            public void setCall_type(String call_type) {
                this.call_type = call_type;
            }

            public int getUse_time() {
                return use_time;
            }

            public void setUse_time(int use_time) {
                this.use_time = use_time;
            }
        }

        public static class TransactionsBean {
            /**
             * update_time : 2015-08-25 19:04:03
             * total_amt : 136
             * bill_cycle : 2015-04-01 00:00:00
             * pay_amt : null
             * plan_amt : 76
             * cell_phone : 156****9149
             */

            private String update_time;
            private int total_amt;
            private String bill_cycle;
            private Object pay_amt;
            private int plan_amt;
            private String cell_phone;

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public int getTotal_amt() {
                return total_amt;
            }

            public void setTotal_amt(int total_amt) {
                this.total_amt = total_amt;
            }

            public String getBill_cycle() {
                return bill_cycle;
            }

            public void setBill_cycle(String bill_cycle) {
                this.bill_cycle = bill_cycle;
            }

            public Object getPay_amt() {
                return pay_amt;
            }

            public void setPay_amt(Object pay_amt) {
                this.pay_amt = pay_amt;
            }

            public int getPlan_amt() {
                return plan_amt;
            }

            public void setPlan_amt(int plan_amt) {
                this.plan_amt = plan_amt;
            }

            public String getCell_phone() {
                return cell_phone;
            }

            public void setCell_phone(String cell_phone) {
                this.cell_phone = cell_phone;
            }
        }

        public static class NetsBean {
            /**
             * update_time : 2015-08-25 19:04:04
             * place : 上海
             * net_type : 3G网络
             * start_time : 2015-03-01 10:16:43
             * cell_phone : 156****9149
             * subtotal : 6.76
             * subflow : 676
             * use_time : 488
             */

            private String update_time;
            private String place;
            private String net_type;
            private String start_time;
            private String cell_phone;
            private double subtotal;
            private float subflow;
            private int use_time;

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getPlace() {
                return place;
            }

            public void setPlace(String place) {
                this.place = place;
            }

            public String getNet_type() {
                return net_type;
            }

            public void setNet_type(String net_type) {
                this.net_type = net_type;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getCell_phone() {
                return cell_phone;
            }

            public void setCell_phone(String cell_phone) {
                this.cell_phone = cell_phone;
            }

            public double getSubtotal() {
                return subtotal;
            }

            public void setSubtotal(double subtotal) {
                this.subtotal = subtotal;
            }

            public float getSubflow() {
                return subflow;
            }

            public void setSubflow(float subflow) {
                this.subflow = subflow;
            }

            public int getUse_time() {
                return use_time;
            }

            public void setUse_time(int use_time) {
                this.use_time = use_time;
            }
        }

        public static class SmsesBean {
            /**
             * update_time : 2015-08-25 19:04:03
             * start_time : 2015-08-23 12:28:17
             * init_type : null
             * place : 021
             * other_cell_phone : 182****8830
             * cell_phone : 156****9149
             * subtotal : 0
             */

            private String update_time;
            private String start_time;
            private Object init_type;
            private String place;
            private String other_cell_phone;
            private String cell_phone;
            private BigDecimal subtotal;

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public Object getInit_type() {
                return init_type;
            }

            public void setInit_type(Object init_type) {
                this.init_type = init_type;
            }

            public String getPlace() {
                return place;
            }

            public void setPlace(String place) {
                this.place = place;
            }

            public String getOther_cell_phone() {
                return other_cell_phone;
            }

            public void setOther_cell_phone(String other_cell_phone) {
                this.other_cell_phone = other_cell_phone;
            }

            public String getCell_phone() {
                return cell_phone;
            }

            public void setCell_phone(String cell_phone) {
                this.cell_phone = cell_phone;
            }

            public BigDecimal getSubtotal() {
                return subtotal;
            }

            public void setSubtotal(BigDecimal subtotal) {
                this.subtotal = subtotal;
            }
        }
    }
}
