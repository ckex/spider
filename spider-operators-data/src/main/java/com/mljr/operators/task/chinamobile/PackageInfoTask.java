package com.mljr.operators.task.chinamobile;

import com.google.gson.Gson;
import com.mljr.operators.dao.primary.operators.PackageInfoMapper;
import com.mljr.operators.entity.model.operators.PackageInfo;
import com.mljr.operators.service.ChinaMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * Created by songchi on 17/2/23.
 */
@Component
public class PackageInfoTask implements Runnable {

    protected static final Logger logger = LoggerFactory.getLogger(PackageInfoTask.class);
    @Autowired
    private ChinaMobileService chinaMobileService;

    @Autowired
    private PackageInfoMapper packageInfoMapper;

    public Long userInfoId;

    public Map<String, String> cookies;

    public void setParams(Long userInfoId, Map<String, String> cookies) {
        this.userInfoId = userInfoId;
        this.cookies = cookies;
    }


    @Override
    public void run() {
        try {
            String data = chinaMobileService.getPackageInfo(cookies);
            PackInfoResponse response = new Gson().fromJson(data, PackInfoResponse.class);

            if (response.getError().getCode() == 0) {
                String productName = response.getValue().getPlan_name();
                String brandName = response.getValue().getBrand_name();
                PackageInfo packageInfo = new PackageInfo();
                packageInfo.setCreateTime(new Date());
                packageInfo.setUpdateTime(new Date());
                packageInfo.setUserInfoId(userInfoId);

                packageInfo.setProductName(productName);
                packageInfo.setBrandName(brandName);
                packageInfoMapper.insertSelective(packageInfo);

            } else {
                throw new RuntimeException("获取套餐信息失败 " + data);
            }


        } catch (Exception e) {
            logger.error("PackageInfoTask error", e);

        }
    }

    class PackInfoResponse {

        /**
         * error : {"code":0,"hint":"","message":""}
         * value : {"plan_name":"预付费4G飞享套餐48元档","brand_name":"全球通","tel_no":"13681668945"}
         */

        private ErrorBean error;
        private ValueBean value;

        public ErrorBean getError() {
            return error;
        }

        public void setError(ErrorBean error) {
            this.error = error;
        }

        public ValueBean getValue() {
            return value;
        }

        public void setValue(ValueBean value) {
            this.value = value;
        }

        public class ErrorBean {
            /**
             * code : 0
             * hint :
             * message :
             */

            private int code;
            private String hint;
            private String message;

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public String getHint() {
                return hint;
            }

            public void setHint(String hint) {
                this.hint = hint;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }
        }

        public class ValueBean {
            /**
             * plan_name : 预付费4G飞享套餐48元档
             * brand_name : 全球通
             * tel_no : 13681668945
             */

            private String plan_name;
            private String brand_name;
            private String tel_no;

            public String getPlan_name() {
                return plan_name;
            }

            public void setPlan_name(String plan_name) {
                this.plan_name = plan_name;
            }

            public String getBrand_name() {
                return brand_name;
            }

            public void setBrand_name(String brand_name) {
                this.brand_name = brand_name;
            }

            public String getTel_no() {
                return tel_no;
            }

            public void setTel_no(String tel_no) {
                this.tel_no = tel_no;
            }
        }
    }

}