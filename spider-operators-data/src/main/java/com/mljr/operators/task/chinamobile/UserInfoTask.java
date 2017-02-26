package com.mljr.operators.task.chinamobile;

import com.google.gson.Gson;
import com.mljr.operators.dao.primary.operators.UserInfoMapper;
import com.mljr.operators.entity.model.operators.UserInfo;
import com.mljr.operators.service.ChinaMobileService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by songchi on 17/2/23.
 */
public class UserInfoTask implements Runnable {
    @Autowired
    private ChinaMobileService chinaMobileService;

    @Autowired
    private UserInfoMapper userInfoMapper;

    private Map<String, String> cookies;

    public UserInfoTask(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    @Override
    public void run() {
        try {
            String userInfoStr = chinaMobileService.getUserInfo(cookies);
            userInfoStr = "{\"error\":{\"code\":0,\"hint\":\"\",\"message\":\"\"},\"value\":{\"loginName\":\"*池\",\"loginMobil\":\"13681668945\"}}";
            UserInfoResponse response =new Gson().fromJson(userInfoStr,UserInfoResponse.class);
            if(response.getError().getCode()==0){
                UserInfo record = new UserInfo();
                record.setMobile(response.getValue().getLoginMobil());
//                record.set
            }else{
                throw new RuntimeException("获取用户信息失败  " + userInfoStr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class UserInfoResponse {

        /**
         * error : {"code":0,"hint":"","message":""}
         * value : {"loginName":"*池","loginMobil":"13681668945"}
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
             * loginName : *池
             * loginMobil : 13681668945
             */

            private String loginName;
            private String loginMobil;

            public String getLoginName() {
                return loginName;
            }

            public void setLoginName(String loginName) {
                this.loginName = loginName;
            }

            public String getLoginMobil() {
                return loginMobil;
            }

            public void setLoginMobil(String loginMobil) {
                this.loginMobil = loginMobil;
            }
        }
    }
}
