package com.mljr.spider.task.schedule;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.mljr.common.ServiceConfig;
import com.mljr.redis.RedisClient;
import com.mljr.spider.task.utils.WebDriverUtils;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Created by gaoxi on 2017/1/5.
 * QQ空间Cookie获取
 */
@Service("qqZoneCookieScheduleTask")
public class QQZoneCookieScheduleTask implements IScheduleTaskDealSingle<Cookie> {

    private static final Logger LOGGER = LoggerFactory.getLogger(QQZoneCookieScheduleTask.class);

    private static final String REDIS_KEY="%s==%s";

    private static RedisClient redisClient = ServiceConfig.getSpiderRedisClient();

    private WebDriverUtils webDriverUtils=new WebDriverUtils(new PhantomJSDriver());

    private static final String QQ_ZONE_LOGIN_URL = "http://qzone.qq.com";

    @Override
    public boolean execute(Cookie task, String ownSign) throws Exception {
        return false;
    }

    @Override
    public List<Cookie> selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
        return null;
    }

    @Override
    public Comparator<Cookie> getComparator() {
        return null;
    }

    private Set<Cookie> getCookie(String user, String password) throws Exception {

        LOGGER.info("start get qq cookie.user:{},password:{}", user, password);

        WebDriver webDriver =webDriverUtils.getResource();

        webDriver.get(QQ_ZONE_LOGIN_URL);

        webDriver.switchTo().frame("login_frame");

        webDriver.findElement(By.id("switcher_plogin")).click();

        WebElement account_input = webDriver.findElement(By.id("u"));//账号输入狂

        WebElement password_input = webDriver.findElement(By.id("p"));//密码输入框

        WebElement login_button = webDriver.findElement(By.id("login_button"));//登陆按钮

        account_input.clear();

        password_input.clear();

        account_input.sendKeys(user);//QQ账号

        password_input.sendKeys(password);//QQ密码

        login_button.click();

        Set<Cookie> cookieSet = webDriver.manage().getCookies();//获取cookies信息

        redisClient.use(new Function<Jedis, String>() {
            @Override
            public String apply(@Nullable Jedis jedis) {
                return jedis.set(String.format(REDIS_KEY,user,password), JSON.toJSONString(cookieSet));
            }
        });

        return cookieSet;
    }
}
