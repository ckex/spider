package com.mljr.operators.service.chinaunicom.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mljr.operators.convert.ChinaUnicomConvert;
import com.mljr.operators.entity.dto.chinaunicom.*;
import com.mljr.operators.entity.vo.chinaunicom.BillVO;
import com.mljr.operators.entity.vo.chinaunicom.CallVO;
import com.mljr.operators.entity.vo.chinaunicom.SMSVO;
import com.mljr.operators.entity.vo.chinaunicom.UserInfoVO;
import com.mljr.operators.service.chinaunicom.IChinaUnicomService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author gaoxi
 * @Time 2017/2/13
 */
public abstract class AbstractChinaUnicomServiceImpl implements IChinaUnicomService {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractChinaUnicomServiceImpl.class);

    protected static final String LOGIN_URL = "https://uac.10010.com/portal/mallLogin.jsp?redirectURL=http://www.10010.com";

    protected static final String USER_INFO_INDEX = "http://iservice.10010.com/e4/query/basic/personal_xx_iframe.html";

    /**
     * 获取用户基本信息+套餐信息
     */
    protected static final String USER_INFO_URL = "http://iservice.10010.com/e3/static/query/searchPerInfo/?_=%s";

    /**
     * 获取历史账单信息
     */
    protected static final String HISTORY_BILL_URL = "http://iservice.10010.com/e3/static/query/queryHistoryBill?_=%s&querytype=0001&querycode=0001&billdate=%s&flag=2";

    /**
     * 剩余话费、流量URL
     */
    protected static final String REMAINING_RESOURCE_URL = "http://iservice.10010.com/e3/static/query/userinfoquery?_=%s";

    /**
     * 通话详情
     */
    protected static final String CALL_DETAIL_URL = "http://iservice.10010.com/e3/static/query/callDetail?_=%s&pageNo=%s&pageSize=100&beginDate=%s&endDate=%s";

    /**
     * 短信URL
     */
    protected static final String SMS_URL = "http://iservice.10010.com/e3/static/query/sms?_=%s&pageSize=100&pageNo=%s&begindate=%s&enddate=%s";

    protected static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:51.0) Gecko/20100101 Firefox/51.0";

    //自助服务
    private static final String SELF_SERVICE_PAGE = "body > div.center980.contentBg > div:nth-child(1) > div.navIndex.navMar > div.navIndexC > div.nav > ul > li:nth-child(2) > a";

    @Override
    public UserInfoVO queryUserInfo(String cookies) throws Exception {
        UserInfoVO entity = new UserInfoVO();
        String url = String.format(USER_INFO_URL, new Date().getTime());
        try {
            String respString = request(url, cookies);
            if (null != respString) {
                PersonInfoDTO jsonEntity = JSON.parseObject(respString, PersonInfoDTO.class);
                if (null != jsonEntity) {
                    entity = ChinaUnicomConvert.convert(jsonEntity);
                }
            }
        } catch (IOException e) {
            throw new IOException("request failure.url:" + url, e);
        } catch (Exception e) {
            throw new Exception("query user info failure.url:" + url, e);
        }
        return entity;
    }

    @Override
    public List<BillVO> queryHistoryBill(String cookies, int billYear, int billMonth) throws Exception {
        List<BillVO> list = Lists.newArrayList();
        LocalDate localDate = LocalDate.of(billYear, billMonth, 1);
        String month = localDate.getMonthValue() >= 10 ? localDate.getMonthValue() + "" : "0" + localDate.getMonthValue();
        String billDate = localDate.getYear() + "" + month;
        String url = String.format(HISTORY_BILL_URL, new Date().getTime(), billDate);
        try {
            String respStr = request(url, cookies);
            if (null != respStr) {
                BillDTO billDTO = JSON.parseObject(respStr, BillDTO.class);
                if (null != billDTO) {
                    list = ChinaUnicomConvert.convert(billDTO);
                }
            }
        } catch (IOException e) {
            throw new IOException("request failure.url:" + url, e);
        } catch (Exception e) {
            throw new Exception("query history bill failure.url:" + url, e);
        }
        return list;
    }

    @Override
    public String queryAcctBalance(String cookies) throws Exception {
        String url = String.format(REMAINING_RESOURCE_URL, new Date());
        try {
            String respStr = request(url, cookies);
            if (null != respStr) {
                RemainingDTO dto = JSON.parseObject(respStr, RemainingDTO.class);
                if (null != dto && dto.getResource() != null) {
                    return dto.getResource().getAcctBalance();
                }
            }
        } catch (IOException e) {
            throw new IOException("request acctBalance failure.url:" + url, e);
        } catch (Exception e) {
            throw new Exception("query acctBalance failure.url:" + url, e);
        }
        return "";
    }

    @Override
    public CallVO queryCallDetail(String cookies, int callYear, int callMonth, int pageNo) throws Exception {
        CallVO entity = new CallVO();
        String startDate = LocalDate.of(callYear, callMonth, 1).toString();
        String endDate = LocalDate.of(callYear, callMonth + 1, 1).minusDays(1).toString();
        String url = String.format(CALL_DETAIL_URL, new Date().getTime(), pageNo, startDate, endDate);
        try {
            String respStr = request(url, cookies);
            if (null != respStr) {
                CallDTO callDTO = JSON.parseObject(respStr, CallDTO.class);
                if (callDTO != null && callDTO.getPageMap() != null) {
                    if (callDTO.getPageMap().getResult() != null && callDTO.getPageMap().getResult().size() > 0) {
                        List<CallVO.CallDetailVO> detailList = Lists.newArrayList();
                        callDTO.getPageMap().getResult().forEach(callDetailDTO -> {
                            detailList.add(ChinaUnicomConvert.convert(callDetailDTO));
                        });
                        entity.setCallDetail(detailList);
                    }
                    entity.setPageNo(callDTO.getPageMap().getPageNo());
                    entity.setTotalPages(callDTO.getPageMap().getTotalPages());
                }
            }
        } catch (IOException e) {
            throw new IOException("request callDetail failure.url:" + url, e);
        } catch (Exception e) {
            throw new IOException("query callDetail failure.url:" + url, e);
        }
        return entity;
    }

    @Override
    public SMSVO querySMS(String cookies, int smsYear, int smsMonth, int pageNo) throws Exception {
        SMSVO entity = new SMSVO();
        String startDate = LocalDate.of(smsYear, smsMonth, 1).format(DateTimeFormatter.BASIC_ISO_DATE);
        String endDate = LocalDate.of(smsYear, smsMonth + 1, 1).minusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE);
        String url = String.format(SMS_URL, new Date().getTime(), pageNo, startDate, endDate);
        try {
            String respStr = request(url, cookies);
            if (null != respStr) {
                SMSDTO smsdto = JSON.parseObject(respStr, SMSDTO.class);
                if (smsdto != null && smsdto.getPageMap() != null) {
                    if (smsdto.getPageMap().getResult() != null && smsdto.getPageMap().getResult().size() > 0) {
                        List<SMSVO.SMSDetailVO> detailList = Lists.newArrayList();
                        smsdto.getPageMap().getResult().forEach(smsDetailDTO -> {
                            detailList.add(ChinaUnicomConvert.convert(smsDetailDTO));
                        });
                        entity.setSmsDetails(detailList);
                    }
                    entity.setTotalPages(smsdto.getPageMap().getTotalPages());
                    entity.setPageNo(smsdto.getPageMap().getPageNo());
                }
            }
        } catch (IOException e) {
            throw new IOException("request sms failure.url:" + url, e);
        } catch (Exception e) {
            throw new IOException("query sms failure.url:" + url, e);
        }
        return entity;
    }

    protected String request(String url, String cookies) throws IOException {
        Connection connection = Jsoup.connect(url)
                .timeout(60 * 1000)
                .method(Connection.Method.POST)
                .header("User-Agent", USER_AGENT)
                .ignoreContentType(true);
        if (!StringUtils.isBlank(cookies)) {
            connection.header("Cookie", cookies);
        }
        Connection.Response response = connection.execute();
        if (response.statusCode() == 200 && !StringUtils.isBlank(response.body())) {
            logger.info("get reslut json url:{} data:{}", url, response.body());
            return response.body();
        }
        return null;
    }


    private Map<String, String> doLogin(LoginDTO loginDTO) {
        Map<String, String> cookies = Maps.newHashMap();
        WebDriver webDriver = null;
        try {
            webDriver = new PhantomJSDriver();
            webDriver.get(LOGIN_URL);
            WebElement webElement = loadComplete(webDriver, "login_iframe", 30);
            if (webElement != null) {
                webDriver.switchTo().frame(webElement);

                WebElement account_input = webDriver.findElement(By.id("userName"));//账号输入狂

                WebElement password_input = webDriver.findElement(By.id("userPwd"));//密码输入框

                account_input.clear();

                password_input.clear();

                account_input.sendKeys(loginDTO.getMobile());

                password_input.sendKeys(loginDTO.getPassword());

                WebElement login_button = webDriver.findElement(By.id("login1"));//登陆按钮

                login_button.click();

                if (loginComplete(webDriver, SELF_SERVICE_PAGE, 30)) {

                    webDriver.get(USER_INFO_INDEX);

                    if (loginComplete(webDriver, "#menu_query > span", 30)) {
                        Thread.sleep(10000);
                        webDriver.manage().getCookies().stream().forEach(cookie -> cookies.put(cookie.getName(), cookie.getValue()));
                    }
                }
            }
        } catch (Exception e) {
            logger.error(String.format("chinaUnicom get cookies failure.mobile:{%s} password:{%s}", loginDTO.getMobile(), loginDTO.getPassword()), e);
        } finally {
            if (webDriver != null) {
                webDriver.quit();
            }
        }
        return cookies;
    }


    protected Map<String, String> getCookies(LoginDTO loginDTO) {
        return doLogin(loginDTO);
    }

    protected WebElement loadComplete(WebDriver webDriver, final String className, int timeOutInSeconds) {
        WebElement webElement = null;
        try {
            webElement = (new WebDriverWait(webDriver, timeOutInSeconds)).until(new ExpectedCondition<WebElement>() {
                @Override
                public WebElement apply(WebDriver driver) {
                    return webDriver.findElement(By.className(className));
                }
            });
        } catch (Exception e) {
            logger.error(String.format("from url:{%s} find element {%s} failure.", webDriver.getCurrentUrl(), className), e);
        }
        return webElement;
    }

    protected boolean loginComplete(WebDriver webDriver, final String name, int timeOutInSeconds) {
        try {
            (new WebDriverWait(webDriver, timeOutInSeconds)).until(new ExpectedCondition<WebElement>() {
                @Override
                public WebElement apply(WebDriver driver) {
                    return webDriver.findElement(By.cssSelector(name));
                }
            });
            return true;
        } catch (Exception e) {
            logger.error(String.format("from url:{%s} find element {%s} failure.", webDriver.getCurrentUrl(), name), e);
        }
        return false;
    }
}
