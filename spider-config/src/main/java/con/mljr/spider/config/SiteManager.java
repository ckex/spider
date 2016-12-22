/**
 *
 */
package con.mljr.spider.config;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Site;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ckex zha </br>
 *         2016年12月17日,上午11:04:51
 */
public class SiteManager {
    protected transient final Logger logger = LoggerFactory.getLogger(SiteManager.class);

//    public DynamicConfig getSiteByDomain(String domain) {
//        return new DynamicConfig();
//    }

    private static Map<String, Site> siteMap = new HashMap<>();

    public static Site getSiteByDomain(String domain) {
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String path = String.format("/config/%s/%s", ip, domain);
        return siteMap.get(path);
    }

    public synchronized void setSite(String domain, Site site) {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            String path = String.format("/config/%s/%s", ip, domain);

            if (siteMap.containsKey(path)) {
                Site oldSite = siteMap.get(path);
                compareSiteObject(oldSite, site);
                siteMap.put(path, oldSite);
            } else {
                siteMap.put(path, site);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("setSite error.", ExceptionUtils.getFullStackTrace(e));

        }
    }

    public void compareSiteObject(Site oldSite, Site newSite) {
        if (!StringUtils.equals(oldSite.getUserAgent(), newSite.getUserAgent())) {
            oldSite.setUserAgent(newSite.getUserAgent());
        }
        if (!StringUtils.equals(oldSite.getDomain(), newSite.getDomain())) {
            oldSite.setDomain(newSite.getDomain());
        }
        if (!StringUtils.equals(oldSite.getCharset(), newSite.getCharset())) {
            oldSite.setCharset(newSite.getCharset());
        }
        if (oldSite.getSleepTime() != newSite.getSleepTime()) {
            oldSite.setSleepTime(newSite.getSleepTime());
        }
        if (oldSite.getCycleRetryTimes() != newSite.getCycleRetryTimes()) {
            oldSite.setCycleRetryTimes(newSite.getCycleRetryTimes());
        }

        if (oldSite.getRetrySleepTime() != newSite.getRetrySleepTime()) {
            oldSite.setRetrySleepTime(newSite.getRetrySleepTime());
        }

        if (oldSite.getRetryTimes() != newSite.getRetryTimes()) {
            oldSite.setRetryTimes(newSite.getRetryTimes());
        }
        if(!oldSite.getCookies().equals(newSite.getCookies())){
            Map<String, String> cookieMap = newSite.getCookies();
            if (!cookieMap.isEmpty()) {
                for (Map.Entry<String, String> entry : cookieMap.entrySet()) {
                    oldSite.addCookie(entry.getKey(), entry.getValue());
                }
            }
        }
        if(!oldSite.getHeaders().equals(newSite.getHeaders())){
            Map<String, String> headerMap = newSite.getHeaders();
            if (!headerMap.isEmpty()) {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    oldSite.addHeader(entry.getKey(), entry.getValue());
                }
            }
        }
    }

}
