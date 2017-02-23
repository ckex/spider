/**
 * 
 */
package com.mljr.spider.storage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.FilePipeline;

/**
 * @author Ckex zha </br>
 *         2016年11月25日,下午5:54:00
 *
 */
public class LocalFilePipeline extends FilePipeline {

  protected transient Logger logger = LoggerFactory.getLogger(getClass());

  public LocalFilePipeline(String path) {
    super(path);
  }

  @Override
  public void process(ResultItems resultItems, Task task) {
    String path = buildPath(task.getUUID());
    try {
      PrintWriter printWriter = new PrintWriter(
          new OutputStreamWriter(new FileOutputStream(getFile(path + DigestUtils.md5Hex(resultItems.getRequest().getUrl()) + ".html")), "UTF-8"));
      printWriter.println("url:\t" + resultItems.getRequest().getUrl());
      for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
        if (entry.getValue() instanceof Iterable) {
          Iterable value = (Iterable) entry.getValue();
          printWriter.println(entry.getKey() + ":");
          for (Object o : value) {
            printWriter.println(o);
          }
        } else {
          printWriter.println(entry.getKey() + ":\t" + entry.getValue());
        }
      }
      printWriter.close();
    } catch (IOException e) {
      logger.warn("write file error", e);
    }
  }

  private String buildPath(String uuid) {
    String hourStr = DateFormatUtils.format(new Date(), "yyyy-MM-dd-HH");
    return this.path + PATH_SEPERATOR + hourStr + PATH_SEPERATOR + uuid + PATH_SEPERATOR;
  }

}
