/**
 * 
 */
package con.mljr.spider.config;

import us.codecraft.webmagic.Site;

/**
 * @author Ckex zha </br>
 *         2016年12月17日,上午11:04:51
 *
 */
public class SiteManager {

	/**
	 * 
	 */
	public SiteManager() {
		// TODO Auto-generated constructor stub
	}

	public DynamicConfig getSiteByDomain(String domain) {
		return new DynamicConfig();
	}

	public synchronized void setSite(String domain, Site site) {
		
	}

}
