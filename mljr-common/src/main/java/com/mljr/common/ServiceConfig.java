package com.mljr.common;

import com.mljr.redis.RedisClient;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServiceConfig {
    private static String SETTING_FILE = System.getProperty("user.home") + System.getProperty("file.separator")
            + "ucloud_umq.properties";

    private static Properties properties = new Properties();

    private static final Log log = LogFactory.getLog(ServiceConfig.class);

    static {
        load();
    }

    public static void load() {
        load(SETTING_FILE);
    }

    public static void load(String configFile) {
        InputStream is = null;
        try {
            is = new FileInputStream(configFile);
            properties.load(is);
        } catch (FileNotFoundException e) {
            log.warn("The setting file " + configFile + " was not found.");
        } catch (IOException e) {
            log.warn("Load properties from file " + configFile + " failed");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {

                }
            }
        }
    }

    private static ZkClient zkClient;

    public static ZkClient getZkClient() {
        if (zkClient == null) {
            zkClient = new ZkClient(getZkUrl(), getZkTimeout());
        }
        return zkClient;
    }

    public static String getZkUrl() {
        return properties.getProperty("zookeeper.zkUrl");
    }

    public static Integer getZkTimeout() {
        String timeout = properties.getProperty("zookeeper.timeout");
        return Integer.parseInt(timeout);
    }


    public static RedisClient getSpiderRedisClient() {
        if (redisClient == null) {
            redisClient = new RedisClient(getSpiderRedisHost(),
                    getSpiderRedisPort(), getSpiderRedisTimeout(),
                    getSpiderRedisMaxTotal(), getSpiderRedisMaxIdle(),
                    getSpiderMaxWaitMillis()
            );
        }
        return redisClient;
    }

    private static RedisClient redisClient;

    public static String getSpiderRedisHost() {
        return properties.getProperty("redis.spider.host");
    }

    public static Integer getSpiderRedisPort() {
        return Integer.parseInt(properties.getProperty("redis.spider.port"));
    }

    public static Integer getSpiderRedisTimeout() {
        return Integer.parseInt(properties.getProperty("redis.spider.timeout"));
    }

    public static Integer getSpiderRedisMaxTotal() {
        return Integer.parseInt(properties.getProperty("redis.spider.maxTotal"));
    }

    public static Integer getSpiderRedisMaxIdle() {
        return Integer.parseInt(properties.getProperty("redis.spider.maxIdle"));
    }

    public static Integer getSpiderMaxWaitMillis() {
        return Integer.parseInt(properties.getProperty("redis.spider.maxWaitMillis"));
    }

}
