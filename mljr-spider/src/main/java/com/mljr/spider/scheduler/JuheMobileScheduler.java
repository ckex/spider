/**
 * 
 */
package com.mljr.spider.scheduler;

import java.util.concurrent.BlockingQueue;

import com.google.common.base.CharMatcher;
import com.mljr.spider.mq.UMQMessage;
import com.mljr.spider.scheduler.manager.AbstractMessage.PullMsgTask;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;

/**
 * @author Ckex zha </br>
 *         2016年11月10日,上午11:37:15
 *
 */
public class JuheMobileScheduler extends AbstractScheduler {

	public static final String URL = "http://op.juhe.cn/onebox/phone/query?tel=%s&key=d3baaded0db0ea2dd0a359fb485e3d60";

	public JuheMobileScheduler(final Spider spider, BlockingQueue<UMQMessage> queue) throws Exception {
		super(spider, queue);
	}

	public JuheMobileScheduler(Spider spider, PullMsgTask task) throws Exception {
		super(spider, task);
	}

	public JuheMobileScheduler(Spider spider, String queueId) throws Exception {
		super(spider, queueId);
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
		String url = String.format(URL, message);
		url = CharMatcher.WHITESPACE.replaceFrom(CharMatcher.anyOf("\r\n\t").replaceFrom(url, ""), "");
		return new Request(url);
	}

}
