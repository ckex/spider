package com.mljr.sync.task;

import com.mljr.sync.service.CarHomeNetService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by fulin on 2017/2/21.
 */
@Component
public class CarHomeNetTask extends AbstractTask {
  @Autowired
  CarHomeNetService carHomeNetService;

  @Override
  void execute() {
    try {
      carHomeNetService.consume();
    } catch (Exception e) {
      if (logger.isDebugEnabled()) {
        e.printStackTrace();
      }
      logger.error("Execute task error. " + ExceptionUtils.getStackTrace(e));
    }
  }
}
