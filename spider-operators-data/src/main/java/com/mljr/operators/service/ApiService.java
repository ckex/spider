package com.mljr.operators.service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.mljr.common.ServiceConfig;
import com.mljr.operators.common.constant.ErrorCodeEnum;
import com.mljr.operators.common.constant.RequestInfoEnum;
import com.mljr.operators.entity.ApiData;
import com.mljr.operators.entity.PhoneInfo;
import com.mljr.operators.entity.model.operators.CallInfo;
import com.mljr.operators.entity.model.operators.FlowInfo;
import com.mljr.operators.entity.model.operators.SMSInfo;
import com.mljr.operators.entity.model.operators.UserInfo;
import com.mljr.operators.service.primary.operators.*;
import com.mljr.redis.RedisClient;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by songchi on 17/3/1.
 */
@Service
public class ApiService {

    Gson gson = new Gson();

    public final static String PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    ICallInfoService callInfoService;

    @Autowired
    IUserInfoService userInfoService;

    @Autowired
    IFlowInfoService flowInfoService;

    @Autowired
    ISMSInfoService smsInfoService;

    @Autowired
    IBillInfoService billInfoService;

    RedisClient redisClient = ServiceConfig.getSpiderRedisClient();

    public final static String TOKEN_KEY = "token-uid";

    public final static String COOKIES_KEY = "operators-cookies";

    // TODO
    public RequestInfoEnum checkState(String token) {
        return RequestInfoEnum.SUCCESS;
    }

    public Long findUidByToken(String token) {
        return redisClient.use(new Function<Jedis, Long>() {
            @Nullable
            @Override
            public Long apply(@Nullable Jedis jedis) {
                List<String> retList = jedis.hmget(TOKEN_KEY, token);
                if (CollectionUtils.isNotEmpty(retList)) {
                    return Long.parseLong(retList.get(0));
                }
                return -1L;
            }
        });
    }

    public UserInfo findUserByToken(String token) {
        return userInfoService.getById(findUidByToken(token));
    }

    public String saveToken(String token, Long uid) {
        return redisClient.use(new Function<Jedis, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Jedis jedis) {
                Map<String, String> map = Maps.newHashMap();
                map.put(token, String.valueOf(uid));
                return jedis.hmset(TOKEN_KEY, map);
            }
        });
    }

    public String saveCookies(String cellphone, String cookies) {
        return redisClient.use(new Function<Jedis, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Jedis jedis) {
                Map<String, String> map = Maps.newHashMap();
                map.put(cellphone, cookies);
                return jedis.hmset(COOKIES_KEY, map);
            }
        });
    }

    public String findCookiesByCellphone(String cellphone) {
        return redisClient.use(new Function<Jedis, String>() {
            @Override
            public String apply(Jedis jedis) {
                List<String> retList = jedis.hmget(COOKIES_KEY, cellphone);
                if (CollectionUtils.isNotEmpty(retList)) {
                    return retList.get(0);
                }
                return null;
            }
        });
    }

    public PhoneInfo getPhoneInfo(String cellphone) {

        String urlPattern = "http://apis.juhe.cn/mobile/get?phone=%s&dtype=json&key=f36726f33204fd46ed1c380826eab4e2";
        String ret = null;
        try {
            ret = Jsoup.connect(String.format(urlPattern, cellphone)).ignoreContentType(true)
                    .timeout(1000 * 60).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gson.fromJson(ret, PhoneInfo.class);
    }

    public ApiData getData(String token) {
        Long uid = findUidByToken(token);
        ApiData data = new ApiData();
        data.setStatus("success");
        data.setUpdate_time(DateFormatUtils.format(new Date(), PATTERN));
        data.setError_code(ErrorCodeEnum.TASK_SUCC.getCode());
        data.setError_msg(ErrorCodeEnum.TASK_SUCC.getDesc());

        ApiData.RequestArgsBean argsBean = new ApiData.RequestArgsBean();
        argsBean.setToken(token);
        List<ApiData.RequestArgsBean> argsBeanList = Lists.newArrayList(argsBean);
        data.setRequest_args(argsBeanList);

        ApiData.TransactionsBeanX tbx = new ApiData.TransactionsBeanX();
        tbx.setToken(token);
        tbx.setVersion("1");
        // datasource skip
        ApiData.TransactionsBeanX.BasicBean basicBean = this.getBasic(uid);
        String cellPhone = basicBean.getCell_phone();

        tbx.setCalls(this.getCalls(uid, cellPhone));

        tbx.setNets(this.getNets(uid, cellPhone));

        tbx.setSmses(this.getSmses(uid, cellPhone));

        tbx.setTransactions(this.getBills(uid));

        data.setTransactions(Lists.newArrayList(tbx));

        return data;
    }


//    "basic": {
//        "update_time": "2015-08-25 19:04:03",
//                "cell_phone": "156****9149",
//                "idcard": "3707****6736",
//                "reg_time": "2014-10-29 00:00:00",
//                "real_name": "王**"
//    },

    public ApiData.TransactionsBeanX.BasicBean getBasic(Long uid) {
        UserInfo info = userInfoService.getById(uid);
        ApiData.TransactionsBeanX.BasicBean basicBean = new ApiData.TransactionsBeanX.BasicBean();
        basicBean.setUpdate_time(DateFormatUtils.format(info.getCreateTime(), PATTERN));
        basicBean.setCell_phone(info.getMobile());
        basicBean.setIdcard(info.getIdcard());
        // TODO
        basicBean.setReg_time(null);
        basicBean.setReal_name(info.getUserName());
        return basicBean;
    }

//    {
//        "update_time": "2015-08-25 19:04:03",
//            "place": "上海",
//            "other_cell_phone": "159****9882",
//            "subtotal": 0,
//            "start_time": "2015-03-02 17:10:15",
//            "cell_phone": "156****9149",
//            "init_type": "被叫",
//            "call_type": "本地通话",
//            "use_time": 15
//    },

    public List<ApiData.TransactionsBeanX.CallsBean> getCalls(Long uid, String cellPhone) {
        List<CallInfo> infoList = callInfoService.selectByUid(uid);
        List<ApiData.TransactionsBeanX.CallsBean> retList = Lists.newArrayList();
        for (CallInfo info : infoList) {
            ApiData.TransactionsBeanX.CallsBean bean = new ApiData.TransactionsBeanX.CallsBean();
            bean.setUpdate_time(DateFormatUtils.format(info.getCreateTime(), PATTERN));
            bean.setPlace(info.getCallLocalAddress());
            bean.setOther_cell_phone(info.getCallNumber());
            bean.setSubtotal(info.getCallFee());
            bean.setStart_time(DateFormatUtils.format(info.getCallDate(), PATTERN));
            bean.setCell_phone(cellPhone);
            bean.setInit_type(info.getCallType());
            bean.setCall_type(info.getLandType());
            //  TODO 转秒
//            bean.setUse_time(info.getCallLongHour());
            retList.add(bean);
        }
        return retList;
    }


    //    {
//        "update_time": "2015-08-25 19:04:04",
//            "place": "上海",
//            "net_type": "3G网络",
//            "start_time": "2015-03-01 10:16:43",
//            "cell_phone": "156****9149",
//            "subtotal": 6.76,
//            "subflow": 676,
//            "use_time": 488
//    },
    public List<ApiData.TransactionsBeanX.NetsBean> getNets(Long uid, String cellPhone) {
        List<FlowInfo> infoList = flowInfoService.selectByUid(uid);
        List<ApiData.TransactionsBeanX.NetsBean> retList = Lists.newArrayList();
        for (FlowInfo info : infoList) {
            ApiData.TransactionsBeanX.NetsBean bean = new ApiData.TransactionsBeanX.NetsBean();
            bean.setUpdate_time(DateFormatUtils.format(info.getCreateTime(), PATTERN));
            bean.setPlace(info.getHomeArea());
            bean.setNet_type(info.getNetType());
            bean.setStart_time(DateFormatUtils.format(info.getStartTime(), PATTERN));
            bean.setCell_phone(cellPhone);
            bean.setSubtotal(info.getFee().doubleValue());
            bean.setSubflow(info.getTotalBytes().floatValue());
            // TODO
//            bean.setUse_time(info.getDuration());
            retList.add(bean);
        }
        return retList;
    }


//    {
//        "update_time": "2015-08-25 19:04:03",
//            "start_time": "2015-08-23 12:28:17",
//            "init_type": null,
//            "place": "021",
//            "other_cell_phone": "182****8830",
//            "cell_phone": "156****9149",
//            "subtotal": 0
//    },

    public List<ApiData.TransactionsBeanX.SmsesBean> getSmses(Long uid, String cellPhone) {
        List<SMSInfo> infoList = smsInfoService.selectByUid(uid);
        List<ApiData.TransactionsBeanX.SmsesBean> retList = Lists.newArrayList();
        for (SMSInfo info : infoList) {
            ApiData.TransactionsBeanX.SmsesBean bean = new ApiData.TransactionsBeanX.SmsesBean();
            bean.setUpdate_time(DateFormatUtils.format(info.getCreateTime(), PATTERN));
            bean.setStart_time(DateFormatUtils.format(info.getSendTime(), PATTERN));
            bean.setInit_type(null);
            bean.setPlace(info.getLocation());
            bean.setOther_cell_phone(info.getSendNum());
            bean.setCell_phone(cellPhone);
            bean.setSubtotal(info.getFee());
            retList.add(bean);
        }
        return retList;
    }


    //    "transactions": [
//    {
//        "update_time": "2015-08-25 19:04:03",
//            "total_amt": 136,
//            "bill_cycle": "2015-04-01 00:00:00",
//            "pay_amt": null,
//            "plan_amt": 76,
//            "cell_phone": "156****9149"
//    },
//    {
//        "update_time": "2015-08-25 19:04:03",
//            "total_amt": 136.1,
//            "bill_cycle": "2015-03-01 00:00:00",
//            "pay_amt": null,
//            "plan_amt": 76,
//            "cell_phone": "156****9149"
//    }
//    ],
    public List<ApiData.TransactionsBeanX.TransactionsBean> getBills(Long uid) {
//        List<SMSInfo> infoList = billInfoService.selectByUid(uid);
//        List<ApiData.TransactionsBeanX.SmsesBean> retList = Lists.newArrayList();
//        for (SMSInfo info : infoList) {
//            ApiData.TransactionsBeanX.SmsesBean bean = new ApiData.TransactionsBeanX.SmsesBean();
//            bean.setUpdate_time(DateFormatUtils.format(info.getCreateTime(), PATTERN));
//            bean.setStart_time(DateFormatUtils.format(info.getSendTime(), PATTERN));
//            bean.setInit_type(null);
//            bean.setPlace(info.getLocation());
//            bean.setOther_cell_phone(info.getSendNum());
//            bean.setCell_phone(cellPhone);
//            bean.setSubtotal(info.getFee());
//            retList.add(bean);
//        }
//        return retList;
        return null;
    }


}
