package com.mljr.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.mljr.common.ServiceConfig;
import com.mljr.constant.RedisConstant;
import com.mljr.entity.CCookie;
import com.mljr.entity.QQCookie;
import org.openqa.selenium.Cookie;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * Created by gaoxi on 2017/1/8.
 */
public class QQUtils {

    private QQUtils() {
    }

    public static Set<String> getAllQQ() {
        return ServiceConfig.getSpiderRedisClient().use(new Function<Jedis, Set<String>>() {
            @Override
            public Set<String> apply(Jedis jedis) {
                return jedis.smembers(RedisConstant.QQ_GROUPS_KEY);
            }
        });
    }

    /**
     * 随机获取QQ号码
     *
     * @param count 一次获取多少个QQ
     * @return
     */
    public static List<String> getRandomKey(final int count) {
        return ServiceConfig.getSpiderRedisClient().use(new Function<Jedis, List<String>>() {
            @Override
            public List<String> apply(Jedis jedis) {
                return jedis.srandmember(RedisConstant.QQ_GROUPS_KEY, count);
            }
        });
    }

    /**
     * 获取redis cookie
     *
     * @param key QQ号码
     * @return
     */
    public static QQCookie getRedisCookie(final String key) {
        return ServiceConfig.getSpiderRedisClient().use(new Function<Jedis, QQCookie>() {
            @Override
            public QQCookie apply(Jedis jedis) {
                return JSON.parseObject(jedis.get(key), QQCookie.class);
            }
        });
    }

    public static void setRedisKey(QQCookie cookie){
        ServiceConfig.getSpiderRedisClient().use(new Function<Jedis, Object>() {
            @Override
            public Object apply(Jedis jedis) {
                return jedis.set(cookie.getUser(),JSON.toJSONString(cookie));
            }
        });
    }

    /**
     * 分割QQ user:password
     *
     * @param key
     * @return
     */
    public static String[] spiltQQ(String key) {
        if (key.indexOf(":") < 0)
            throw new IllegalArgumentException("key is error.");
        return key.split(":");
    }

    public static CCookie convert(Cookie cookie) {
        CCookie entity = new CCookie();
        entity.setDomain(cookie.getDomain());
        entity.setExpiry(cookie.getExpiry());
        entity.setHttpOnly(cookie.isHttpOnly());
        entity.setName(cookie.getName());
        entity.setValue(cookie.getValue());
        entity.setPath(cookie.getPath());
        entity.setSecure(cookie.isSecure());
        return entity;
    }

    public static Cookie convert(CCookie cookie) {
        Cookie.Builder builder = new Cookie.Builder(cookie.getName(), cookie.getValue())
                .domain(cookie.getDomain())
                .expiresOn(cookie.getExpiry())
                .isHttpOnly(cookie.isHttpOnly())
                .isSecure(cookie.isSecure())
                .path(cookie.getPath());
        return builder.build();
    }
}
