/**
 *
 */
package con.mljr.spider.config;

import com.google.common.base.Joiner;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Site;

import java.util.List;

/**
 * @author Ckex zha </br>
 *         2016年12月17日,上午11:04:51
 */
public class SiteManager {
    protected transient final Logger logger = LoggerFactory.getLogger(SiteManager.class);

    private static CuratorFramework client = null;

    public SiteManager() {
        if (client == null) {
            String connectString = "127.0.0.1:2181";
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 3);
            client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
            client.start();
        }
    }

    public DynamicConfig getSiteByDomain(String domain) {
        return new DynamicConfig();
    }

    public synchronized void setSite(String domain, Site site) {

    }

    public final static String[] ips =
            {"10.9.86.137", "10.9.120.152", "10.9.144.100", "10.9.152.221",
                    "10.9.87.127", "10.9.186.101", "10.9.199.216", "10.9.108.39",
                    "10.9.88.4", "10.9.136.160", "10.9.145.53", "10.9.156.231",
                    "10.9.154.167", "10.9.169.120", "10.9.180.171"};

    public static void main(String[] args) {
        SiteManager siteManager = new SiteManager();
        siteManager.writeAllSiteConfig();
    }

    public void writeAllSiteConfig(){
        List<Site> siteList = ConfigUtils.getSiteList();
        for (String ip : ips) {
            for (Site site : siteList) {
                sentDataToZookeeper(ip, site.getDomain(), site.toString().getBytes());
            }
        }
    }

    public void sentDataToZookeeper(String ip, String domain, byte[] bytes) {

        try {
            String CONFIG_PATH = Joiner.on("/").join("/test4", ip, domain);
            client.create()
                    .creatingParentsIfNeeded()//如果父节点不存在，则创建
                    .withMode(CreateMode.PERSISTENT)
                    .forPath(CONFIG_PATH);

            Stat stat = client.setData().forPath(CONFIG_PATH, bytes);
            System.out.println(stat.getDataLength() + "---------------------");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("zookeeper error", e);
        }
    }

}
