/**
 *
 */
package com.mljr.sync.task;

import com.mljr.sync.service.JdService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JdTask extends AbstractTask {

    @Autowired
    private JdService jdService;

    public JdTask() {
        super();
    }

    @Override
    void execute() {
        try {
            jdService.sendUrls();
        } catch (Exception ex) {
            if (logger.isDebugEnabled()) {
                ex.printStackTrace();
            }
            logger.error("Execute task error. " + ExceptionUtils.getStackTrace(ex));
        }
    }

}
