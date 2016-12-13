package com.mljr.spider.scheduler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CharMatcher;
import com.mljr.spider.mq.UMQMessage;
import com.mljr.spider.scheduler.manager.AbstractMessage;
import com.mljr.spider.util.StringUtil;
import com.ucloud.umq.common.ServiceConfig;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;

import java.util.concurrent.BlockingQueue;

/**
 * Created by xi.gao
 * Date:2016/12/6
 */
public class LBSAMapGeoScheduler extends AbstractScheduler {

    private static final String URL = "http://restapi.amap.com/v3/geocode/geo?key=%s&address=%s&city=%s&output=JSON";

    public LBSAMapGeoScheduler(Spider spider, BlockingQueue<UMQMessage> mqMsgQueue) throws Exception {
        super(spider, mqMsgQueue);
    }

    public LBSAMapGeoScheduler(Spider spider, AbstractMessage.PullMsgTask task) throws Exception {
        super(spider, task);
    }

    public LBSAMapGeoScheduler(Spider spider, String qid) throws Exception {
        super(spider, qid);
    }

    @Override
    public boolean pushTask(Spider spider, UMQMessage message) {
        if(null==message || StringUtils.isBlank(message.message)){
            logger.warn("lbs amap geo mq message is empty");
            return false;
        }
        push(buildRequst(message.message), spider);
        return true;
    }

    @Override
    Request buildRequst(String message) {
        JSONObject jsonObject = JSON.parseObject(message);
        String url = String.format(URL, ServiceConfig.getLBSAMapKey(), "","");
        if(jsonObject.containsKey("city") && jsonObject.containsKey("address")){
            String[] cityArray=jsonObject.getString("city").split(" ");
            String address=jsonObject.getString("address");
            String city="";
            switch (cityArray.length) {
                case 1:
                    city = cityArray[0];
                    break;
                case 2:
                    if (cityArray[0].endsWith("省")) {
                        city = cityArray[1];
                    } else if (cityArray[0].endsWith("市")) {
                        city = cityArray[0];
                    } else {
                        city = cityArray[1];
                    }
                    break;
                default:
                    city = cityArray[1];
                    break;
            }
            url = String.format(URL, ServiceConfig.getLBSAMapKey(), address,city);
            url = CharMatcher.WHITESPACE.replaceFrom(CharMatcher.anyOf("\r\n\t").replaceFrom(url, ""), "");
        }
        return new Request(url);
    }

    @Override
    public int getLeftRequestsCount(Task task) {
        return 0;
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        return 0;
    }

    @Override
    public void push(Request request, Task task) {
        put(request);
    }

    @Override
    public Request poll(Task task) {
        return take();
    }
}
