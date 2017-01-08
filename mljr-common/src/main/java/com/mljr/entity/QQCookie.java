package com.mljr.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaoxi on 2017/1/6.
 */
public class QQCookie implements Serializable {

    private static final long serialVersionUID = 3539754193877470622L;

    private String user;//账号

    private String password;//密码

    private List<CCookie> cookies;//cookies

    private long timestamp = System.currentTimeMillis();

    public QQCookie() {

    }

    public QQCookie(String user, String password, List<CCookie> cookies, long timestamp) {
        this.user = user;
        this.password = password;
        this.cookies = cookies;
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<CCookie> getCookies() {
        return cookies;
    }

    public void setCookies(List<CCookie> cookies) {
        this.cookies = cookies;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
