package com.cyxk.wrframelibrary.data.api;


/**
 * Created by wr on 2017/2/8.
 */

public class ApiConstants {
    public static final int TYPE_COUNT = 2;

    public static final int HOST_COMMON = 0;
    public static final int HOST_PASSPORT = 1;
    public static final int HOST_API_URL = 3;


    public static final String HOST_URL_COMMON = "";//正服
//        public static final String HOST_URL_COMMON = "";//测服
    public static final String HOST_URL_PASSPORT = "";//登陆正服
//    public static final String HOST_URL_PASSPORT = "";//登陆测服
//
    public static final String API_URL = "https://api.weixin.qq.com";

    public static String getHost(int hostType) {
        String host;
        switch (hostType) {
            case HOST_COMMON:
                host = HOST_URL_COMMON;
                break;
            case HOST_PASSPORT:
                host = HOST_URL_PASSPORT;
                break;
            case HOST_API_URL:
                host = API_URL;
                break;
            default:
                host = HOST_URL_COMMON;
                break;
        }
        return host;
    }
}
