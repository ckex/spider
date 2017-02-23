/**
 * 
 */
package com.mljr.sync.task;

import com.mljr.sync.service.BitautoService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
// @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BitautoTask extends AbstractTask {

  @Autowired
  private BitautoService bitautoService;

  public BitautoTask() {
    super();
  }

  @Override
  void execute() {
    try {
      bitautoService.syncCarinfo();
    } catch (Exception ex) {
      if (logger.isDebugEnabled()) {
        ex.printStackTrace();
      }
      logger.error("Execute task error. " + ExceptionUtils.getStackTrace(ex));
    }
  }

}
