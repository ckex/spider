package com.mljr.spider.scheduler;

import com.google.common.collect.Maps;
import com.mljr.spider.mq.UMQMessage;
import com.mljr.spider.scheduler.manager.AbstractMessage;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Created by xi.gao
 * Date:2016/12/5
 */
public class ChaYHKDataScheduler extends AbstractScheduler {

    /**
     * 请求URL
     */
    private static final String URL = "http://cha.yinhangkadata.com/";

    /**
     * form表单提交时，需要用到且是固定
     * 另外还能重写selectRequestMethod方法
     *
     * @see us.codecraft.webmagic.downloader.HttpClientDownloader#selectRequestMethod
     */
    private static final String FORM_PARAMS_KEY = "nameValuePair";

    /**
     * 请求参数
     */
    private static final String REQUEST_PARAM_FILED = "card";

    public ChaYHKDataScheduler(Spider spider, BlockingQueue<UMQMessage> mqMsgQueue) throws Exception {
        super(spider, mqMsgQueue);
    }

    public ChaYHKDataScheduler(Spider spider, AbstractMessage.PullMsgTask task) throws Exception {
        super(spider, task);
    }

    public ChaYHKDataScheduler(Spider spider, String qid) throws Exception {
        super(spider, qid);
    }


    @Override
    public boolean pushTask(Spider spider, UMQMessage message) {
        Request request = new Request(URL);

        request.setMethod(HttpConstant.Method.POST);

        NameValuePair[] values = new NameValuePair[1];

        values[0] = new BasicNameValuePair(REQUEST_PARAM_FILED,message.message);

        Map<String, Object> nameValuePair = Maps.newHashMap();

        nameValuePair.put(FORM_PARAMS_KEY, values);

        request.setExtras(nameValuePair);

        push(request, spider);
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
