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
 * Date:2016/12/2
 */
public class SogouMobileScheduler extends AbstractScheduler {

    private static final String URL = "https://www.sogou.com/web?query=%s";

    public SogouMobileScheduler(Spider spider, BlockingQueue<UMQMessage> mqMsgQueue) throws Exception {
        super(spider, mqMsgQueue);
    }

    public SogouMobileScheduler(Spider spider, AbstractMessage.PullMsgTask task) throws Exception {
        super(spider, task);
    }

    public SogouMobileScheduler(Spider spider, String qid) throws Exception {
        super(spider, qid);
    }

    @Override
    public boolean pushTask(Spider spider, UMQMessage message) {
        String url = String.format(URL, message.message, message.message);
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
