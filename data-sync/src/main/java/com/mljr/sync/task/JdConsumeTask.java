package com.mljr.sync.task;

import com.mljr.sync.service.JdConsumeService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by songchi on 17/2/15.
 */
@Component
public class JdConsumeTask extends AbstractTask {
    @Autowired
    private JdConsumeService jdConsumeService;

    @Override
    void execute() {
        try {
            jdConsumeService.consume();
        } catch (Exception ex) {
            if (logger.isDebugEnabled()) {
                ex.printStackTrace();
            }
            logger.error("Execute task error. " + ExceptionUtils.getStackTrace(ex));
        }
    }

}
