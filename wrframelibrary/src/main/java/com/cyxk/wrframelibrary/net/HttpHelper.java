package com.cyxk.wrframelibrary.net;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 51425 on 2017/7/2.
 */

public class HttpHelper implements IHttpProcessor {

    private static IHttpProcessor mIHttpProcessor = null;
    private static HttpHelper mInstance;
    private Map<String,Object> mParams;
    private HttpHelper(){
        mParams = new HashMap<>();
    }

    public static void init(IHttpProcessor httpProcessor){
        if (mIHttpProcessor == null){
            mIHttpProcessor = httpProcessor;
        }

    }
    public static HttpHelper obtain(){
        if (mInstance == null ){
            return new HttpHelper();
        }
        return  mInstance;
    }


    @Override
    public void post(String url, Map<String, Object> params,String cacheName,Object tag, ICallback callback) {
        mIHttpProcessor.post(url,params,cacheName,tag,callback);
    }

    @Override
    public void postFile(String url, Map<String, Object> params, String cacheName, Object tag, boolean successToast, boolean errorToast, ICallback callback) {
        mIHttpProcessor.postFile(url,params,cacheName,tag,successToast,errorToast,callback);
    }

    @Override
    public void get(String url, Map<String, Object> params,String cacheName,Object tag,ICallback callback) {
        mIHttpProcessor.get(url,params,cacheName,tag,callback);
    }


    public <T> T postExecute(String url, Map<String, Object> params, String cacheName, Object tag,Class<T> tClass){
        return  mIHttpProcessor.postExecute(url,params,cacheName,tag,tClass);
    }
}
