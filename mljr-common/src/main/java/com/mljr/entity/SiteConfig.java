package com.mljr.entity;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.apache.http.HttpHost;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.proxy.ProxyPool;
import us.codecraft.webmagic.utils.UrlUtils;

import java.io.Serializable;
import java.util.*;

/**
 * Created by songchi on 16/12/21.
 */
public class SiteConfig implements Serializable {

  public SiteConfig() {
    // nothing
  }

  public SiteConfig(Site site) {
    this.setDomain(site.getDomain());
    this.setSleepTime(site.getSleepTime());
    this.setCharset(site.getCharset());
    this.setCycleRetryTimes(site.getCycleRetryTimes());
    this.setRetrySleepTime(site.getRetrySleepTime());
    this.setRetryTimes(site.getRetryTimes());
    this.setUserAgent(site.getUserAgent());
    Map<String, String> cookieMap = site.getCookies();
    if (!cookieMap.isEmpty()) {
      for (Map.Entry<String, String> entry : cookieMap.entrySet()) {
        this.addCookie(entry.getKey(), entry.getValue());
      }
    }
    Map<String, String> headerMap = site.getHeaders();
    if (!headerMap.isEmpty()) {
      for (Map.Entry<String, String> entry : headerMap.entrySet()) {
        this.addHeader(entry.getKey(), entry.getValue());
      }
    }
  }

  public Site toSite() {
    Site site = new Site();
    site.setDomain(this.getDomain());
    site.setSleepTime(this.getSleepTime());
    site.setCharset(this.getCharset());
    site.setCycleRetryTimes(this.getCycleRetryTimes());
    site.setRetrySleepTime(this.getRetrySleepTime());
    site.setRetryTimes(this.getRetryTimes());
    site.setUserAgent(this.getUserAgent());
    Map<String, String> cookieMap = this.getCookies();
    if (!cookieMap.isEmpty()) {
      for (Map.Entry<String, String> entry : cookieMap.entrySet()) {
        site.addCookie(entry.getKey(), entry.getValue());
      }
    }
    Map<String, String> headerMap = this.getHeaders();
    if (!headerMap.isEmpty()) {
      for (Map.Entry<String, String> entry : headerMap.entrySet()) {
        site.addHeader(entry.getKey(), entry.getValue());
      }
    }
    return site;
  }

  private String domain;

  private String userAgent;

  private Map<String, String> defaultCookies = new LinkedHashMap<String, String>();

  private Table<String, String, String> cookies = HashBasedTable.create();

  private String charset;

  /**
   * startUrls is the urls the crawler to start with.
   */
  private List<Request> startRequests = new ArrayList<Request>();

  private int sleepTime = 5000;

  private int retryTimes = 0;

  private int cycleRetryTimes = 0;

  private int retrySleepTime = 1000;

  private int timeOut = 5000;

  private static final Set<Integer> DEFAULT_STATUS_CODE_SET = new HashSet<Integer>();

  private Set<Integer> acceptStatCode = DEFAULT_STATUS_CODE_SET;

  private Map<String, String> headers = new HashMap<String, String>();

  private HttpHost httpProxy;

  private ProxyPool httpProxyPool;

  private boolean useGzip = true;

  /**
   * @see us.codecraft.webmagic.utils.HttpConstant.Header
   * @deprecated
   */
  public static interface HeaderConst {

    public static final String REFERER = "Referer";
  }


  static {
    DEFAULT_STATUS_CODE_SET.add(200);
  }


  /**
   * Add a cookie with domain {@link #getDomain()}
   *
   * @param name name
   * @param value value
   * @return this
   */
  public SiteConfig addCookie(String name, String value) {
    defaultCookies.put(name, value);
    return this;
  }

  /**
   * Add a cookie with specific domain.
   *
   * @param domain domain
   * @param name name
   * @param value value
   * @return this
   */
  public SiteConfig addCookie(String domain, String name, String value) {
    cookies.put(domain, name, value);
    return this;
  }

  /**
   * set user agent
   *
   * @param userAgent userAgent
   * @return this
   */
  public SiteConfig setUserAgent(String userAgent) {
    this.userAgent = userAgent;
    return this;
  }

  /**
   * get cookies
   *
   * @return get cookies
   */
  public Map<String, String> getCookies() {
    return defaultCookies;
  }

  /**
   * get cookies of all domains
   *
   * @return get cookies
   */
  public Map<String, Map<String, String>> getAllCookies() {
    return cookies.rowMap();
  }

  /**
   * get user agent
   *
   * @return user agent
   */
  public String getUserAgent() {
    return userAgent;
  }

  /**
   * get domain
   *
   * @return get domain
   */
  public String getDomain() {
    return domain;
  }

  /**
   * set the domain of site.
   *
   * @param domain domain
   * @return this
   */
  public SiteConfig setDomain(String domain) {
    this.domain = domain;
    return this;
  }

  /**
   * Set charset of page manually.<br>
   * When charset is not set or set to null, it can be auto detected by Http header.
   *
   * @param charset charset
   * @return this
   */
  public SiteConfig setCharset(String charset) {
    this.charset = charset;
    return this;
  }

  /**
   * get charset set manually
   *
   * @return charset
   */
  public String getCharset() {
    return charset;
  }

  public int getTimeOut() {
    return timeOut;
  }

  /**
   * set timeout for downloader in ms
   *
   * @param timeOut timeOut
   * @return this
   */
  public SiteConfig setTimeOut(int timeOut) {
    this.timeOut = timeOut;
    return this;
  }

  /**
   * Set acceptStatCode.<br>
   * When status code of http response is in acceptStatCodes, it will be processed.<br>
   * {200} by default.<br>
   * It is not necessarily to be set.<br>
   *
   * @param acceptStatCode acceptStatCode
   * @return this
   */
  public SiteConfig setAcceptStatCode(Set<Integer> acceptStatCode) {
    this.acceptStatCode = acceptStatCode;
    return this;
  }

  /**
   * get acceptStatCode
   *
   * @return acceptStatCode
   */
  public Set<Integer> getAcceptStatCode() {
    return acceptStatCode;
  }

  /**
   * get start urls
   *
   * @return start urls
   * @see #getStartRequests
   * @deprecated
   */
  @Deprecated
  public List<String> getStartUrls() {
    return UrlUtils.convertToUrls(startRequests);
  }

  public List<Request> getStartRequests() {
    return startRequests;
  }

  /**
   * Add a url to start url.<br>
   * Because urls are more a Spider's property than MySite, move it to
   * {@link Spider#addUrl(String...)}}
   *
   * @param startUrl startUrl
   * @return this
   * @see Spider#addUrl(String...)
   * @deprecated
   */
  public SiteConfig addStartUrl(String startUrl) {
    return addStartRequest(new Request(startUrl));
  }

  /**
   * Add a url to start url.<br>
   * Because urls are more a Spider's property than MySite, move it to
   * {@link Spider#addRequest(Request...)}}
   *
   * @param startRequest startRequest
   * @return this
   * @see Spider#addRequest(Request...)
   * @deprecated
   */
  public SiteConfig addStartRequest(Request startRequest) {
    this.startRequests.add(startRequest);
    if (domain == null && startRequest.getUrl() != null) {
      domain = UrlUtils.getDomain(startRequest.getUrl());
    }
    return this;
  }

  /**
   * Set the interval between the processing of two pages.<br>
   * Time unit is micro seconds.<br>
   *
   * @param sleepTime sleepTime
   * @return this
   */
  public SiteConfig setSleepTime(int sleepTime) {
    this.sleepTime = sleepTime;
    return this;
  }

  /**
   * Get the interval between the processing of two pages.<br>
   * Time unit is micro seconds.<br>
   *
   * @return the interval between the processing of two pages,
   */
  public int getSleepTime() {
    return sleepTime;
  }

  /**
   * Get retry times immediately when download fail, 0 by default.<br>
   *
   * @return retry times when download fail
   */
  public int getRetryTimes() {
    return retryTimes;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  /**
   * Put an Http header for downloader. <br>
   * Use {@link #addCookie(String, String)} for cookie and {@link #setUserAgent(String)} for
   * user-agent. <br>
   *
   * @param key key of http header, there are some keys constant in {@link HeaderConst}
   * @param value value of header
   * @return this
   */
  public SiteConfig addHeader(String key, String value) {
    headers.put(key, value);
    return this;
  }

  /**
   * Set retry times when download fail, 0 by default.<br>
   *
   * @param retryTimes retryTimes
   * @return this
   */
  public SiteConfig setRetryTimes(int retryTimes) {
    this.retryTimes = retryTimes;
    return this;
  }

  /**
   * When cycleRetryTimes is more than 0, it will add back to scheduler and try download again. <br>
   *
   * @return retry times when download fail
   */
  public int getCycleRetryTimes() {
    return cycleRetryTimes;
  }

  /**
   * Set cycleRetryTimes times when download fail, 0 by default. <br>
   *
   * @param cycleRetryTimes cycleRetryTimes
   * @return this
   */
  public SiteConfig setCycleRetryTimes(int cycleRetryTimes) {
    this.cycleRetryTimes = cycleRetryTimes;
    return this;
  }

  public HttpHost getHttpProxy() {
    return httpProxy;
  }

  /**
   * set up httpProxy for this site
   *
   * @param httpProxy httpProxy
   * @return this
   */
  public SiteConfig setHttpProxy(HttpHost httpProxy) {
    this.httpProxy = httpProxy;
    return this;
  }

  public boolean isUseGzip() {
    return useGzip;
  }

  public int getRetrySleepTime() {
    return retrySleepTime;
  }

  /**
   * Set retry sleep times when download fail, 1000 by default. <br>
   *
   * @param retrySleepTime retrySleepTime
   * @return this
   */
  public SiteConfig setRetrySleepTime(int retrySleepTime) {
    this.retrySleepTime = retrySleepTime;
    return this;
  }

  /**
   * Whether use gzip. <br>
   * Default is true, you can set it to false to disable gzip.
   *
   * @param useGzip useGzip
   * @return this
   */
  public SiteConfig setUseGzip(boolean useGzip) {
    this.useGzip = useGzip;
    return this;
  }


  @Override
  public int hashCode() {
    int result = domain != null ? domain.hashCode() : 0;
    result = 31 * result + (userAgent != null ? userAgent.hashCode() : 0);
    result = 31 * result + (defaultCookies != null ? defaultCookies.hashCode() : 0);
    result = 31 * result + (charset != null ? charset.hashCode() : 0);
    result = 31 * result + (startRequests != null ? startRequests.hashCode() : 0);
    result = 31 * result + sleepTime;
    result = 31 * result + retryTimes;
    result = 31 * result + cycleRetryTimes;
    result = 31 * result + timeOut;
    result = 31 * result + (acceptStatCode != null ? acceptStatCode.hashCode() : 0);
    result = 31 * result + (headers != null ? headers.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "MySite{" + "domain='" + domain + '\'' + ", userAgent='" + userAgent + '\'' + ", cookies=" + defaultCookies + ", charset='" + charset
        + '\'' + ", startRequests=" + startRequests + ", sleepTime=" + sleepTime + ", retryTimes=" + retryTimes + ", cycleRetryTimes="
        + cycleRetryTimes + ", timeOut=" + timeOut + ", acceptStatCode=" + acceptStatCode + ", headers=" + headers + '}';
  }

  public String toJSONString() {
    return JSON.toJSONString(this);
  }
}
