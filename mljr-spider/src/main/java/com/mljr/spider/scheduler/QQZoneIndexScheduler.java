package com.mljr.spider.scheduler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CharMatcher;
import com.mljr.spider.mq.UMQMessage;
import com.mljr.spider.scheduler.manager.AbstractMessage;
import com.mljr.utils.QQUtils;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;

import java.util.concurrent.BlockingQueue;

/**
 * Created by gaoxi on 2017/1/6.
 * QQ空间首页
 */
public class QQZoneIndexScheduler extends AbstractScheduler {

    public QQZoneIndexScheduler(Spider spider, BlockingQueue<UMQMessage> mqMsgQueue) throws Exception {
        super(spider, mqMsgQueue);
    }

    public QQZoneIndexScheduler(Spider spider, AbstractMessage.PullMsgTask task) throws Exception {
        super(spider, task);
    }

    public QQZoneIndexScheduler(Spider spider, String qid) throws Exception {
        super(spider, qid);
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

    @Override
    boolean pushTask(Spider spider, UMQMessage message) {
        JSONObject jsonObject = JSON.parseObject(message.message);
        String qq = jsonObject.containsKey("qq") ? jsonObject.getString("qq") : null;
        if (null == qq) {
            logger.warn("qq index scheduler rabbitmq json message is error,message:{}", message.message);
            return false;
        }
        push(buildRequst(qq), spider);
        return true;
    }

    @Override
    Request buildRequst(String message) {
        String url = String.format(QQUtils.QQ_INDEX_URL, message);
        url = CharMatcher.WHITESPACE.replaceFrom(CharMatcher.anyOf("\r\n\t").replaceFrom(url, ""), "");
        return new Request(url);
    }
}
