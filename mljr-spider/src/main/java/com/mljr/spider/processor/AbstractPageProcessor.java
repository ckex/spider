/**
 * 
 */
package com.mljr.spider.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author Ckex zha </br> 2016年11月6日,下午8:00:35
 *
 */
public abstract class AbstractPageProcessor implements PageProcessor {

  protected transient Logger logger = LoggerFactory.getLogger(getClass());

  protected Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();

  public static final String JSON_FIELD = "JSON";

  public static final String UTF_8 = "UTF-8";
  public static final String GBK = "GBK";

  public AbstractPageProcessor() {
    super();
  }
}
