/**
 *
 */
package com.mljr.sync.task;

import com.mljr.sync.service.QqNumberService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QqNumberTask extends AbstractTask {

  @Autowired
  private QqNumberService qqNumberService;

  public QqNumberTask() {
    super();
  }

  @Override
  void execute() {
    try {
      qqNumberService.syncQqNumber();
    } catch (Exception ex) {
      if (logger.isDebugEnabled()) {
        ex.printStackTrace();
      }
      logger.error("Execute task error. " + ExceptionUtils.getStackTrace(ex));
    }
  }

}
