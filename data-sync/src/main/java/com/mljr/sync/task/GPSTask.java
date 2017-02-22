/**
 *
 */
package com.mljr.sync.task;

import com.mljr.sync.service.CommonService;
import com.ucloud.umq.common.ServiceConfig;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component("gpsTask")
public class GPSTask extends AbstractTask {

    @Override
    void execute() {
        try {
            String flag = "gps-" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
            CommonService.sendFlag(ServiceConfig.getGPSExchange(),
                    ServiceConfig.getGPSRoutingKey(), flag);
        } catch (Exception ex) {
            if (logger.isDebugEnabled()) {
                ex.printStackTrace();
            }
            logger.error("Execute task error. " + ExceptionUtils.getStackTrace(ex));
        }
    }

}
