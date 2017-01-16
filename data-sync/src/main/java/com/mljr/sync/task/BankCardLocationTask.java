/**
 * 
 */
package com.mljr.sync.task;

import com.mljr.sync.service.BankCardLocationService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BankCardLocationTask extends AbstractTask {

	@Autowired
	private BankCardLocationService bankCardLocationService;

	public BankCardLocationTask() {
		super();
	}

	@Override
	void execute() {
		try {
			bankCardLocationService.syncBankCard();
		} catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				ex.printStackTrace();
			}
			logger.error("Execute task error. " + ExceptionUtils.getStackTrace(ex));
		}
	}

}
