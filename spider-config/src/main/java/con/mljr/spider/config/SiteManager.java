/**
 *
 */
package con.mljr.spider.config;

import org.I0Itec.zkclient.ZkClient;
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

    private static ZkClient client = ZkUtils.getZkClient();

    public DynamicConfig getSiteByDomain(String domain) {
        return new DynamicConfig();
    }

    public synchronized void setSite(String domain, Site site) {

    }

    public static void main(String[] args) throws Exception {
        SiteManager siteManager = new SiteManager();
        siteManager.writeAllSiteConfig();
    }

    public void writeAllSiteConfig() {
        List<Site> siteList = ConfigUtils.getSiteList();
        String[] ips = ConfigUtils.ips;
        for (String ip : ips) {
            for (Site site : siteList) {
                sentDataToZookeeper(ip, site.getDomain(), site.toString().getBytes());
            }
        }
    }

    public void sentDataToZookeeper(String ip, String domain, byte[] bytes) {

        try {
            String CONFIG_PATH = String.format("/config/%s/%s", ip, domain);
            System.out.println(CONFIG_PATH);

            ZkUtils.createPath(CONFIG_PATH);

            client.writeData(CONFIG_PATH, bytes);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("zookeeper error", e);
        }
    }

}
