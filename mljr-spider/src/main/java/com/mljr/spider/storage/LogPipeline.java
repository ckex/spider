/**
 * 
 */
package com.mljr.spider.storage;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author Ckex zha </br>
 *         2016年11月10日,上午9:48:18
 *
 */
public class LogPipeline implements Pipeline {

	private static final Logger LOG = LoggerFactory.getLogger(LogPipeline.class);
	private final Logger logger; // = LoggerFactory.getLogger("gps-data");

	public LogPipeline(String name) {
		logger = LoggerFactory.getLogger(name);
	}

	@Override
	public void process(ResultItems resultItems, Task task) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("backup result--->" + resultItems.toString());
		}
		Map<String, Object> result = resultItems.getAll();
		for (Object obj : result.values()) {
			logger.info(obj.toString());
		}
	}

}
