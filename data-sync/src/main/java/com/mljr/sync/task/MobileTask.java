/**
 * 
 */
package com.mljr.sync.task;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mljr.sync.service.MobileService;

/**
 * @author Ckex zha </br>
 *         2016年11月28日,下午12:16:25
 *
 */
@Component
// @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MobileTask extends AbstractTask {

  @Autowired
  private MobileService mobileService;

  public MobileTask() {
    super();
  }

  @Override
  void execute() {
    try {
      mobileService.syncMobile();
    } catch (Exception ex) {
      if (logger.isDebugEnabled()) {
        ex.printStackTrace();
      }
      logger.error("Execute task error. " + ExceptionUtils.getStackTrace(ex));
      throw new RuntimeException(ex);
    }
  }

}
