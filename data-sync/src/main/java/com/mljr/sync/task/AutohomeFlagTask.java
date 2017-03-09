/**
 *
 */
package com.mljr.sync.task;

import com.mljr.sync.service.AutohomeFlagService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AutohomeFlagTask extends AbstractTask {

  @Autowired
  private AutohomeFlagService autohomeFlagService;

  @Override
  void execute() {
    try {
      autohomeFlagService.sendUrls();
    } catch (Exception ex) {
      if (logger.isDebugEnabled()) {
        ex.printStackTrace();
      }
      logger.error("Execute task error. " + ExceptionUtils.getStackTrace(ex));
    }
  }



}
