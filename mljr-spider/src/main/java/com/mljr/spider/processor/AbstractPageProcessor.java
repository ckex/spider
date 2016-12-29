/**
 *
 */
package com.mljr.spider.processor;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.HashBasedTable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mljr.common.ServiceConfig;
import com.mljr.entity.MonitorData;
import com.mljr.entity.SiteConfig;
import com.mljr.redis.RedisClient;
import com.mljr.spider.config.SiteManager;
import com.mljr.utils.IpUtils;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    private static ZkClient zkClient = ServiceConfig.getZkClient();

    private static HashSet<String> pathSet = new HashSet<>();

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
            Object object = zkClient.readData(path,true);

            if(object==null){
                // zk 找不到配置
                zkClient.createPersistent(path, true);
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

    private RedisClient redisClient = ServiceConfig.getSpiderRedisClient();

    private final AtomicInteger parseFailCount = new AtomicInteger(0);

    private Long beginTime;

    private HashBasedTable<String, Integer, Integer> table = HashBasedTable.create();

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

    public synchronized void parseFail(){
        if(beginTime==null){
            beginTime=System.currentTimeMillis();
        }
        parseFailCount.incrementAndGet();

        String ip = IpUtils.getHostName();
        int timeDiff = (int) (System.currentTimeMillis() - beginTime) / 1000;
        // 一分钟写一次库
        if (timeDiff >= 60) {
            String time = sdf.format(new Date());
            String key = Joiner.on("-").join("status-code", ip, domain);
            // 写库
            List<String> list =redisClient.use(new Function<Jedis, List<String>>() {

                @Override
                public List<String> apply(Jedis jedis) {
                    return jedis.lrange(key,0,0);
                }
            });

            if(CollectionUtils.isNotEmpty(list)){
                MonitorData data = JSON.parseObject(list.get(0),MonitorData.class);
                data.setFreqParseFail(parseFailCount.get());
                redisClient.use(new Function<Jedis, String>() {

                    @Override
                    public String apply(Jedis jedis) {
                        return jedis.lset(key,0,JSON.toJSONString(data));
                    }
                });
            }
            parseFailCount.set(0);
            beginTime=null;
        }
    }


    @Override
    public void process(Page page) {
        if (!onProcess(page)){
            try{
                parseFail();
            }catch (Exception e){
                logger.error("解析失败监控错误",e);
            }

        }
    }

    @Override
    public Site getSite() {
        return SiteManager.getSiteByDomain(domain);
    }

}
