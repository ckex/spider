/**
 * 
 */
package com.mljr.sync.task;

import com.mljr.sync.service.LbsService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LbsTask extends AbstractTask {

	@Autowired
	private LbsService lbsService;

	public LbsTask() {
		super();
	}

	@Override
	void execute() {
		try {
			lbsService.syncLbsInfo();
		} catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				ex.printStackTrace();
			}
			logger.error("Execute task error. " + ExceptionUtils.getStackTrace(ex));
		}
	}

}