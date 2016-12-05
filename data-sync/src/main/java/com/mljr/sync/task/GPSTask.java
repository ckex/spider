/**
 * 
 */
package com.mljr.sync.task;

import com.mljr.sync.service.GPSService;
import com.mljr.sync.service.MobileService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GPSTask extends AbstractTask {

	@Autowired
	private GPSService gpsService;

	public GPSTask() {
		super();
	}

	@Override
	void execute() {
		try {
			gpsService.sendFlag();
		} catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				ex.printStackTrace();
			}
			logger.error("Execute task error. " + ExceptionUtils.getStackTrace(ex));
		}
	}

}
