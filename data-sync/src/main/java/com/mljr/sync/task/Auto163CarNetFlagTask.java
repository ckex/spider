package com.mljr.sync.task;

import com.mljr.sync.service.CommonService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fulin on 2017/3/15.
 */
@Component
public class Auto163CarNetFlagTask  extends AbstractTask{
  @Override
  void execute() {
    try {
      String flag = "auto163-" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
      CommonService.sendFlag("", "auto163_flag", flag);
    } catch (Exception ex) {
      if (logger.isDebugEnabled()) {
        ex.printStackTrace();
      }
      logger.error("Execute task error. " + ExceptionUtils.getStackTrace(ex));
    }
  }
}
