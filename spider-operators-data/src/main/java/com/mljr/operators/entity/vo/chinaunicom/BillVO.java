package com.mljr.operators.entity.vo.chinaunicom;

import java.io.Serializable;
import java.util.List;

/**
 * @author gaoxi
 * @Time 2017/2/17
 */
public class BillVO implements Serializable {

    private static final long serialVersionUID = 6004148136392327211L;

    private String queryDate;//查询日期

    private List<BillDetailVO> billDetail;

    public String getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }

    public List<BillDetailVO> getBillDetail() {
        return billDetail;
    }

    public void setBillDetail(List<BillDetailVO> billDetail) {
        this.billDetail = billDetail;
    }

    public static class BillDetailVO {

        private String feeName;//费用名称

        private String fee;//费用

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getFeeName() {
            return feeName;
        }

        public void setFeeName(String feeName) {
            this.feeName = feeName;
        }

        public String getFee() {
            return fee;
        }

    }


}
