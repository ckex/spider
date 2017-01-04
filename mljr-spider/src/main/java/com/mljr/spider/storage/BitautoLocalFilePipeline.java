/**
 *
 */
package com.mljr.spider.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

public class BitautoLocalFilePipeline extends LocalFilePipeline {

    protected transient Logger logger = LoggerFactory.getLogger(getClass());

    public BitautoLocalFilePipeline(String path) {
        super(path);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String url = resultItems.getRequest().getUrl();
        if (url != null && url.contains("peizhi")) {
            super.process(resultItems, task);
        }

    }

}
