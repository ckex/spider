package com.mljr.operators.service.chinaunicom.impl;

import com.mljr.operators.entity.dto.chinaunicom.LoginDTO;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

/**
 * @author gaoxi
 * @Time 2017/2/19
 */
@Service("chinaUnicomService")
public class DefaultChinaUnicomServiceImpl extends AbstractChinaUnicomServiceImpl {

    private final int DEFAULT_TIME_OUT = 20;

    @Override
    public String getCookies(LoginDTO loginDTO) {
        WebDriver webDriver = null;
        try {
            webDriver = new PhantomJSDriver();

            webDriver.get(LOGIN_URL);

            WebElement webElement = loadCompleteByClassName(webDriver, "banner");

            if (webElement != null) {

                webDriver.switchTo().frame(webElement.findElement(By.xpath("//div[1]/iframe")));

                WebElement account_input = webDriver.findElement(By.id("userName"));//账号输入狂
                WebElement password_input = webDriver.findElement(By.id("userPwd"));//密码输入框

                account_input.clear();
                password_input.clear();

                account_input.sendKeys(loginDTO.getMobile());
                password_input.sendKeys(loginDTO.getPassword());

                WebElement login_button = webDriver.findElement(By.id("login1"));//登陆按钮

                login_button.click();

                if (loadCompleteByCssSelector(webDriver, "#menu_query > span") != null) {

                    StringBuilder builder = new StringBuilder();

                    if (webDriver.manage().getCookies() != null && webDriver.manage().getCookies().size() > 0) {
                        webDriver.manage().getCookies().forEach(cookie -> builder.append(cookie.getName()).append("=").append(cookie.getValue()).append(";"));
                    }

                    if (!StringUtils.isBlank(builder.toString())) {
                        String cookies = builder.toString();
                        return cookies.substring(0, cookies.length() - 1);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("get chinaunicom cookies failure.params:{}", loginDTO.toString(), e);
        } finally {
            if (webDriver != null) {
                webDriver.quit();
            }
        }
        return null;
    }

    private WebElement loadCompleteByClassName(WebDriver webDriver, String className) {
        try {
            return (new WebDriverWait(webDriver, DEFAULT_TIME_OUT)).until(new ExpectedCondition<WebElement>() {
                @Override
                public WebElement apply(WebDriver driver) {
                    return webDriver.findElement(By.className(className));
                }
            });
        } catch (Exception e) {
            logger.error("load login index failure.", e);
        }
        return null;
    }

    private WebElement loadCompleteByCssSelector(WebDriver webDriver, String cssSelector) {
        try {
            return (new WebDriverWait(webDriver, DEFAULT_TIME_OUT)).until(new ExpectedCondition<WebElement>() {
                @Override
                public WebElement apply(WebDriver driver) {
                    return webDriver.findElement(By.cssSelector(cssSelector));
                }
            });
        } catch (Exception e) {
            logger.error("load login after html failure.", e);
        }
        return null;
    }
}
