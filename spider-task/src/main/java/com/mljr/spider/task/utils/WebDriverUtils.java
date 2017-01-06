package com.mljr.spider.task.utils;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.openqa.selenium.WebDriver;

/**
 * Created by gaoxi on 2017/1/6.
 */
public class WebDriverUtils {

    private GenericObjectPool<WebDriver> pool;

    public WebDriverUtils(WebDriver webDriver) {
        this.pool = new GenericObjectPool<WebDriver>(new BasePooledObjectFactory<WebDriver>() {
            @Override
            public WebDriver create() throws Exception {
                return webDriver;
            }

            @Override
            public PooledObject<WebDriver> wrap(WebDriver obj) {
                return new DefaultPooledObject<>(obj);
            }
        });
    }

    public WebDriver getResource() throws Exception {
        return pool.borrowObject();
    }


}
