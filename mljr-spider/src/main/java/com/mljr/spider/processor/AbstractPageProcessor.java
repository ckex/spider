/**
 *
 */
package com.mljr.spider.processor;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mljr.common.ServiceConfig;
import com.mljr.entity.SiteConfig;
import com.mljr.spider.config.SiteManager;
import com.mljr.utils.IpUtils;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Ckex zha </br>
 *         2016年11月6日,下午8:00:35
 */
public abstract class AbstractPageProcessor implements PageProcessor {

  protected transient Logger logger = LoggerFactory.getLogger(getClass());

  public static final String JSON_FIELD = "JSON";
  public static final String UTF_8 = "UTF-8";
  public static final String GBK = "GBK";

  protected Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();

  private static ZkClient zkClient = ServiceConfig.getZkClient();

  private static HashSet<String> pathSet = new HashSet<>();

  private final List<PageListener> listeners = new ArrayList<>();

  private final String domain;

  abstract boolean onProcess(Page page);

  public AbstractPageProcessor(String domain) {
    super();
    this.domain = domain;
  }

  public AbstractPageProcessor(Site site) {
    super();
    this.domain = site.getDomain();
    try {
      String ip = IpUtils.getHostName();
      String path = String.format("/config/%s/%s", ip, domain);
      logger.debug("path =================" + path);
      Object object = zkClient.readData(path, true);
      if (object == null) {
        zkClient.createPersistent(path, true); // zk 找不到配置
        zkClient.writeData(path, new SiteConfig(site).toJSONString());
        SiteManager.setSite(domain, site);
      } else {
        Site newSite = JSON.parseObject(String.valueOf(object), SiteConfig.class).toSite();
        SiteManager.setSite(domain, newSite);
      }
      if (pathSet.contains(path)) {
        return;
      } else {
        pathSet.add(path);
      }
      zkClient.subscribeDataChanges(path, new IZkDataListener() {

        public void handleDataDeleted(String dataPath) throws Exception {
          logger.debug("the node 'dataPath'===>");
        }

        public void handleDataChange(String dataPath, Object data) throws Exception {
          Site site = JSON.parseObject(String.valueOf(data), SiteConfig.class).toSite();
          SiteManager.setSite(site.getDomain(), site);
        }
      });

    } catch (Exception e) {
      if (logger.isDebugEnabled()) {
        e.printStackTrace();
      }
      logger.error("zookeeper error" + ExceptionUtils.getStackTrace(e));
      throw new RuntimeException("zookeeper error", e);
    }
  }

  public AbstractPageProcessor() {
    super();
    this.domain = "";
  }

  @Override
  public void process(Page page) {
    boolean isSuccess = onProcess(page);
    try {
      listeners.forEach(fn -> fn.afterProcess(isSuccess, domain, page));
    } catch (Exception e) {
      if (logger.isDebugEnabled()) {
        e.printStackTrace();
      }
      logger.error("解析失败监控错误" + ExceptionUtils.getStackTrace(e));
    }
  }

  @Override
  public Site getSite() {
    return SiteManager.getSiteByDomain(domain);
  }

  public void addPageProcessListener(PageListener listener) {
    listeners.add(listener);
  }

  public interface PageListener {
    void afterProcess(boolean isSuccess, String domain, Page page);
  }

}
