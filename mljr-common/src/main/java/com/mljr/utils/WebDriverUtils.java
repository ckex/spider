package com.mljr.utils;

import com.mljr.constant.WebDriverKind;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gaoxi on 2017/1/8.
 */
public class WebDriverUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverUtils.class);

    private GenericObjectPool<WebDriver> pool = null;

    public WebDriverUtils() {
        this(WebDriverKind.PHANTOMJS);
    }

    public WebDriverUtils(WebDriverKind kind) {
        this.pool = new GenericObjectPool<WebDriver>(new WebDriverFactory(kind));
    }

    public WebDriver getResource() {
        try {
            return pool.borrowObject();
        } catch (Exception e) {
            throw new RuntimeException("Could not get a resource from the pool", e);
        }
    }

}

class WebDriverFactory extends BasePooledObjectFactory<WebDriver> {

    private WebDriverKind kind;

    public WebDriverFactory(WebDriverKind kind) {
        this.kind = kind;
    }

    @Override
    public WebDriver create() throws Exception {
        WebDriver webDriver = null;
        switch (kind) {
            case PHANTOMJS:
                webDriver = new PhantomJSDriver();
                break;
            default:
                webDriver = new PhantomJSDriver();
                break;
        }
        return webDriver;
    }

    @Override
    public PooledObject<WebDriver> wrap(WebDriver obj) {
        return new DefaultPooledObject<>(obj);
    }

    public static void main(String[] args) {

        WebDriverUtils webDriverUtils=new WebDriverUtils();

        for(int i=0;i<10 ; i++){
            if(i>=7){
                //webDriverUtils.
            }
            WebDriver webDriver=webDriverUtils.getResource();
            System.out.println(webDriver.toString()+"===========");
        }
    }
}