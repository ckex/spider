package com.mljr.sync.task;

import com.mljr.sync.service.CommonService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fulin on 2017/2/24.
 */
@Component
public class TruckCarHomeFlagTask  extends AbstractTask {
    @Override
    void execute() {
        try {
            String flag = "360che-" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
            CommonService.sendFlag("", "360che_flag", flag);
        } catch (Exception ex) {
            if (logger.isDebugEnabled()) {
                ex.printStackTrace();
            }
            logger.error("Execute task error. " + ExceptionUtils.getStackTrace(ex));
        }
    }
}
