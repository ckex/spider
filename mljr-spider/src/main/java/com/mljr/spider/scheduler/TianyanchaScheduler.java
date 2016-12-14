package com.mljr.spider.scheduler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CharMatcher;
import com.mljr.spider.mq.UMQMessage;
import com.mljr.spider.scheduler.manager.AbstractMessage;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;

import java.util.concurrent.BlockingQueue;

public class TianyanchaScheduler extends AbstractScheduler {

    private static final String URL = "http://www.tianyancha.com/search/%s.json";

    public TianyanchaScheduler(Spider spider, AbstractMessage.PullMsgTask task) throws Exception {
        super(spider, task);
    }

    public TianyanchaScheduler(Spider spider, String qid) throws Exception {
        super(spider, qid);
    }

    public TianyanchaScheduler(Spider spider, BlockingQueue<UMQMessage> queue) throws Exception {
        super(spider, queue);
    }

    @Override
    public void push(Request request, Task task) {
        put(request);
    }

    @Override
    public Request poll(Task task) {
        return take();
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
    public boolean pushTask(Spider spider, UMQMessage message) {
        push(buildRequst(message.message), spider);
        return true;
    }

    @Override
    Request buildRequst(String message) {
        if(StringUtils.isBlank(message)||!message.contains("merchantName")){
            return null;
        }
        JSONObject jsonObject = JSON.parseObject(message);
        String merchantName = jsonObject.getString("merchantName");
        String url = String.format(URL, merchantName);
        url = CharMatcher.WHITESPACE.replaceFrom(CharMatcher.anyOf("\r\n\t").replaceFrom(url, ""), "");
        return new Request(url);
    }
}
