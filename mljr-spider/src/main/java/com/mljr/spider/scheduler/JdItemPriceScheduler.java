package com.mljr.spider.scheduler;

import com.mljr.spider.mq.UMQMessage;
import com.mljr.spider.scheduler.manager.AbstractMessage.PullMsgTask;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;

import java.util.concurrent.BlockingQueue;

public class JdItemPriceScheduler extends AbstractScheduler {


    public JdItemPriceScheduler(Spider spider, PullMsgTask task) throws Exception {
        super(spider, task);
    }

    public JdItemPriceScheduler(Spider spider, String qid) throws Exception {
        super(spider, qid);
    }

    public JdItemPriceScheduler(Spider spider, BlockingQueue<UMQMessage> queue) throws Exception {
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
        return new Request("https://p.3.cn/prices/mgets?skuIds=J_" + message);
    }
}
