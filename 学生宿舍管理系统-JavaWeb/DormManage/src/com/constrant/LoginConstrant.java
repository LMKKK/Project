package com.constrant;

/**
 * 登录过程中用到的一些常量
 * 为什么定义这些常量？ 因为要在开发中要避免 Magic 数据
 */
public class LoginConstrant {

    public static final String REMEMBER_ME = "1";

    public static final String CURRENT_USER_TYPE = "currentUserType";
    public static final String CURRENT_USER = "currentUser";
    public static final String COOKIE_KEY = "dormUser";

    public static final int COOKIE_TIME_OUT = 1 * 60 * 60 * 24 * 7;
}
