/**
 *
 */
package com.mljr.spider.processor;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mljr.entity.SiteConfig;
import com.mljr.zk.ZkUtils;
import con.mljr.spider.config.SiteManager;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.net.InetAddress;
import java.util.HashSet;

/**
 * @author Ckex zha </br>
 *         2016年11月6日,下午8:00:35
 */
public abstract class AbstractPageProcessor implements PageProcessor {

    protected transient Logger logger = LoggerFactory.getLogger(getClass());

    protected Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();

    public static final String JSON_FIELD = "JSON";

    public static final String UTF_8 = "UTF-8";
    public static final String GBK = "GBK";

    public static ZkClient zkClient = ZkUtils.getZkClient();

    private static HashSet<String> pathSet = new HashSet<>();

    private final String domain;

    public AbstractPageProcessor(String domain) {
        super();
        this.domain = domain;
    }

    public AbstractPageProcessor(Site site) {
        super();
        this.domain = site.getDomain();

        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            String path = String.format("/config/%s/%s", ip, domain);
            logger.debug("path =================" + path);
            Object object = zkClient.readData(path,true);

            if(object==null){
                // zk 找不到配置
                ZkUtils.createPath(path);
                zkClient.writeData(path,new SiteConfig(site).toJSONString());
                SiteManager.setSite(domain, site);
            }else{
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
                    logger.debug("site 变成了  ===============================  " + String.valueOf(data));
                    Site site = JSON.parseObject(String.valueOf(data), SiteConfig.class).toSite();
                    SiteManager.setSite(site.getDomain(), site);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("zookeeper error", e);
        }
    }

    public AbstractPageProcessor() {
        super();
        this.domain = "";
    }

//    @Override
//    public void process(Page page) {
//        boolean success = onProcess(page);
//        if (success){
//            // TODO ...
//        }else{
//            // TODO ...
//        }
//    }
    @Override
    public Site getSite() {
        return SiteManager.getSiteByDomain(domain);
    }

}
