

package com.mljr.spider.scheduler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mljr.spider.mq.UMQMessage;
import com.mljr.spider.scheduler.manager.AbstractMessage.PullMsgTask;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;

import java.util.concurrent.BlockingQueue;

/**
 * Created by fulin on 2016/12/20.
 */
public class BlackIdCardScheduler extends AbstractScheduler {
    private static final String URL = "http://idcard.gxsky.com/more_card.asp?page=%s";

    public BlackIdCardScheduler(Spider spider, PullMsgTask task) throws Exception {
        super(spider, task);
    }

    public BlackIdCardScheduler(Spider spider, String qid) throws Exception {
        super(spider, qid);
    }

    public BlackIdCardScheduler(Spider spider, BlockingQueue<UMQMessage> queue) throws Exception {
        super(spider, queue);
    }

    @Override
    boolean pushTask(Spider spider, UMQMessage message) {
        push(buildRequst(message.message), spider);
        return true;
    }

    @Override
    Request buildRequst(String message) {
        JSONObject json = JSON.parseObject(message);
        String pagenum = json.getInteger("page").toString();
        String url = String.format(URL, pagenum);
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