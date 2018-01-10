package com.cyxk.wrframelibrary.net;

import java.util.Map;

/**
 * Created by 51425 on 2017/7/2.
 */

public interface IHttpProcessor {

    void post(String url, Map<String, Object> params, String cacheName, Object tag, ICallback callback);

    void postFile(String url, Map<String, Object> params, String cacheName, Object tag, boolean successToast, boolean errorToast, ICallback callback);

    void get(String url, Map<String, Object> params, String cacheName, Object tag, ICallback callback);

    <T> T postExecute(String url, Map<String, Object> params, String cacheName, Object tag, Class<T> tClass);


}
