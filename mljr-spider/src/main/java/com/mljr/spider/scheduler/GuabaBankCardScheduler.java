package com.mljr.spider.scheduler;

import com.google.common.base.CharMatcher;
import com.mljr.spider.mq.UMQMessage;
import com.mljr.spider.scheduler.manager.AbstractMessage;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;

import java.util.concurrent.BlockingQueue;

/**
 * Created by xi.gao
 * Date:2016/12/5
 */
public class GuabaBankCardScheduler extends AbstractScheduler {

    private static final String URL = "http://www.guabu.com/api/bank/?cardid=%s";

    public GuabaBankCardScheduler(Spider spider, BlockingQueue<UMQMessage> mqMsgQueue) throws Exception {
        super(spider, mqMsgQueue);
    }

    public GuabaBankCardScheduler(Spider spider, AbstractMessage.PullMsgTask task) throws Exception {
        super(spider, task);
    }

    public GuabaBankCardScheduler(Spider spider, String qid) throws Exception {
        super(spider, qid);
    }

    @Override
    public boolean pushTask(Spider spider, UMQMessage message) {
        String url = String.format(URL,"6222021001085329875");
        url = CharMatcher.WHITESPACE.replaceFrom(CharMatcher.anyOf("\r\n\t").replaceFrom(url, ""), "");
        push(new Request(url), spider);
        return true;
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
