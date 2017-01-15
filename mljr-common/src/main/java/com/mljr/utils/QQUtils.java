package com.mljr.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.mljr.common.ServiceConfig;
import com.mljr.constant.RedisConstant;
import com.mljr.entity.CCookie;
import com.mljr.entity.QQCookie;
import org.openqa.selenium.Cookie;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gaoxi on 2017/1/8.
 */
public class QQUtils {

    private QQUtils() {
    }

    /**
     * QQ好友URL
     */
    public static final String FRIEND_URL = "http://ic2.s2.qzone.qq.com/cgi-bin/feeds/feeds_html_module?i_uin=%s&i_login_uin=%s&mode=4&previewV8=1&style=31&version=8&needDelOpr=true&transparence=true&hideExtend=false&showcount=%s&MORE_FEEDS_CGI=http%3A%2F%2Fic2.s2.qzone.qq.com%2Fcgi-bin%2Ffeeds%2Ffeeds_html_act_all&refer=2&paramstring=os-mac|100";

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

    public static void setRedisKey(QQCookie cookie) {
        ServiceConfig.getSpiderRedisClient().use(new Function<Jedis, Object>() {
            @Override
            public Object apply(Jedis jedis) {
                return jedis.set(cookie.getUser(), JSON.toJSONString(cookie));
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

    /**
     * @param str cookie中skey值
     * @return 计算g_tk值
     */
    public static String getG_TK(String str) {
        int hash = 5381;
        for (int i = 0, len = str.length(); i < len; ++i) {
            hash += (hash << 5) + (int) (char) str.charAt(i);
        }
        return (hash & 0x7fffffff) + "";
    }

    public static String getJsonFromJsonp(String jsonp) {
        Pattern pattern = Pattern.compile("_Callback\\((.*)\\);");
        Matcher matcher = pattern.matcher(jsonp);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getG_TK("s76Z7fEwM7sE3qz3G1vCtiZsbozX03CFYvjUpwNu7JE_"));

        Date date=new Date(1483940905);

        System.out.println(date.toString());
    }
}
