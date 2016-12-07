/**
 * BaiduMobileScheduler.java by Ckex.zha
 */
package com.mljr.spider.scheduler;

import com.google.common.base.CharMatcher;
import com.mljr.spider.mq.UMQMessage;
import com.mljr.spider.scheduler.manager.AbstractMessage.PullMsgTask;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;

import java.util.concurrent.BlockingQueue;

/**
 * @author Ckex.zha <br/>
 *         Nov 17, 20163:06:26 PM
 */
public class Huoche114Scheduler extends AbstractScheduler {

	private static final String URL = "http://www.114huoche.com/shouji/%s";

	public Huoche114Scheduler(Spider spider, PullMsgTask task) throws Exception {
		super(spider, task);
	}

	public Huoche114Scheduler(Spider spider, String qid) throws Exception {
		super(spider, qid);
	}

	public Huoche114Scheduler(Spider spider, BlockingQueue<UMQMessage> queue) throws Exception {
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
		String url = String.format(URL, message.message);
		url = CharMatcher.WHITESPACE.replaceFrom(CharMatcher.anyOf("\r\n\t").replaceFrom(url, ""), "");
		push(new Request(url), spider);
		return true;
	}

}
