/**
 * 
 */
package com.mljr.sync.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Ckex zha </br>
 *         2016年11月28日,上午11:59:38
 *
 */
public class Main {

  private transient Logger logger = LoggerFactory.getLogger(Main.class);

  public static ApplicationContext ctx;

  public Main() {
    super();
    ctx = new ClassPathXmlApplicationContext(
        new String[] {"classpath*:/spring/dao.xml", "classpath*:/spring/dao-datasource.xml", "classpath*:/spring/applicationContext.xml"});
    logger.debug("started . ");
  }

  /**
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    new Main();
  }

}
