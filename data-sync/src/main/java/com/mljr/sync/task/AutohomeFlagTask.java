/**
 *
 */
package com.mljr.sync.task;

import com.mljr.sync.service.CommonService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class AutohomeFlagTask extends AbstractTask {

    @Override
    void execute() {
        try {
            String flag = "autohome-" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
            CommonService.sendFlag("", "autohome_flag", flag);
        } catch (Exception ex) {
            if (logger.isDebugEnabled()) {
                ex.printStackTrace();
            }
            logger.error("Execute task error. " + ExceptionUtils.getStackTrace(ex));
        }
    }

}
