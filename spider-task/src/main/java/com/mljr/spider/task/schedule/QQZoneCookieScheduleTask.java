package com.mljr.spider.task.schedule;

import com.google.common.collect.Lists;
import com.mljr.entity.CCookie;
import com.mljr.entity.QQCookie;
import com.mljr.utils.QQUtils;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gaoxi on 2017/1/5. QQ空间Cookie获取
 */
@Service("qqZoneCookieScheduleTask")
public class QQZoneCookieScheduleTask implements IScheduleTaskDealSingle<String> {

  private static final Logger LOGGER = LoggerFactory.getLogger(QQZoneCookieScheduleTask.class);

  private static final String QQ_ZONE_LOGIN_URL = "http://i.qq.com";

  @Override
  public boolean execute(String task, String ownSign) throws Exception {
    LOGGER.info("execute qq {}", task);
    String[] qqStr = QQUtils.spiltQQ(task);
    List<CCookie> cookieList = Lists.newArrayList();
    Set<Cookie> cookies = getCookie(qqStr[0], qqStr[1]);
    cookies.forEach(cookie -> cookieList.add(QQUtils.convert(cookie)));
    QQCookie cookie = new QQCookie();
    cookie.setCookies(cookieList);
    cookie.setUser(qqStr[0]);
    cookie.setPassword(qqStr[1]);
    cookie.setTimestamp(System.currentTimeMillis());
    QQUtils.setRedisKey(cookie);
    return true;
  }

  @Override
  public List<String> selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum)
      throws Exception {
    List<String> result = Lists.newArrayList();
    // 获取QQ
    Set<String> qqSet = QQUtils.getAllQQ();
    final int time = Integer.parseInt(taskParameter);// 多长时间重新登陆
    if (qqSet == null || qqSet.size() == 0)
      return result;
    qqSet.forEach(qq -> {
      LOGGER.info("get qq key." + qq);
      String[] qqStr = QQUtils.spiltQQ(qq);
      QQCookie qqCookie = QQUtils.getRedisCookie(qqStr[0]);
      if (null == qqCookie || System.currentTimeMillis() - qqCookie.getTimestamp() >= time) { //
        result.add(qq);
      }
    });
    return result;
  }

  @Override
  public Comparator<String> getComparator() {
    return null;
  }

  private Set<Cookie> getCookie(String user, String password) {

    LOGGER.info("start get qq cookie.user:{},password:{}", user, password);

    Set<Cookie> cookieSet = new HashSet<Cookie>();

    WebDriver webDriver = null;

    try {
      webDriver = new PhantomJSDriver();

      webDriver.get(QQ_ZONE_LOGIN_URL);

      webDriver.switchTo().frame("login_frame");

      webDriver.findElement(By.id("switcher_plogin")).click();

      WebElement account_input = webDriver.findElement(By.id("u"));// 账号输入狂

      WebElement password_input = webDriver.findElement(By.id("p"));// 密码输入框

      WebElement login_button = webDriver.findElement(By.id("login_button"));// 登陆按钮

      account_input.clear();

      password_input.clear();

      account_input.sendKeys(user);// QQ账号

      password_input.sendKeys(password);// QQ密码

      login_button.click();

      try {
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        // ingore
      }
      cookieSet = webDriver.manage().getCookies();// 获取cookies信息
    } catch (Exception e) {
      LOGGER.error("qq login exception.user:" + user + "  password:" + password, e);
    } finally {
      if (null != webDriver) {
        webDriver.quit();
      }
    }
    return cookieSet;
  }
}
