package com.cyxk.wrframelibrary.net;

import android.app.Application;

import com.cyxk.wrframelibrary.utils.LogUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okrx.adapter.ObservableResponse;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 51425 on 2017/7/12.
 */

public class OkGoProcessor implements IHttpProcessor {

    private static OkHttpClient okHttpClient;


    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    private static OkGoProcessor okGoProcessor;

    public static OkGoProcessor getOkGoProcessor(Application context){
       if (okGoProcessor == null){
           synchronized (OkGoProcessor.class){
              if (okGoProcessor == null){
                  okGoProcessor = new OkGoProcessor(context);
              }
           }
       }
       return okGoProcessor;
    }


    private OkGoProcessor(Application context) {
//        Properties prop=System.getProperties();
////proxyhostIPaddress
//        String proxyHost="61.135.217.7";
////proxyport
//        String proxyPort="80";
//        prop.put("proxySet","true");
//        prop.put("proxyHost",proxyHost);
//
//        prop.put("proxyPort",proxyPort);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .writeTimeout(10000, TimeUnit.MILLISECONDS)
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
        ;
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.WARNING);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志

        okHttpClient = builder.build();
        OkGo.getInstance().init(context)                           //必须调用初始化
                .setOkHttpClient(okHttpClient)               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(0)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
        ;
        //在你发起Http请求之前设置一下属性
        //广东省深圳市 阿里云
//            LogUtil.e("pro2");
//        System.setProperty("http.proxyHost", "120.25.164.134");
//        System.setProperty("http.proxyPort", "8118");


//        System.setProperty("http.proxyHost", "119.36.92.42");
//        System.setProperty("http.proxyPort", "81");
        //全局公共头
//                .addCommonParams(params);                       //全局公共参数
    }

    /**
     * @param url       url还加注释吗
     * @param params    post需要携带的 params
     * @param cacheName 如果需要缓存，缓存的key，不需要传null或""
     * @param tag       1.log打印 2.有的框架可以传入根据的tag进行一系列操作，例如okgo可以根据tag取消请求操作
     * @param callback  回调接口 具体处理数据的操作在这里面
     */
    @Override
    public void post(String url, Map<String, Object> params, final String cacheName, final Object tag, final ICallback callback) {
//        LogUtil.e("okgo 开始请求");

//        if (loadCount %2 == 0){
//            //广东省深圳市 阿里云
//            LogUtil.e("pro2");
//            System.setProperty("http.proxyHost", "120.25.164.134");
//            System.setProperty("http.proxyPort", "8118");
//        }else{
//
//        }
//        loadCount++;
//        //湖南
//        LogUtil.e("pro1");
//        System.setProperty("http.proxyHost", "119.36.92.42");
//        System.setProperty("http.proxyPort", "81");

//
//        PostRequest<String> stringPostRequest = OkGo.<String>post(url)
//                .tag(tag);
//        Set<Map.Entry<String, Object>> entrySet = params.entrySet();
//        for (Map.Entry<String, Object> entry : entrySet) {
//            stringPostRequest.params(entry.getKey(), (String) entry.getValue());
//        }
//        stringPostRequest.execute(new StringCallback() {
//            @Override
//            public void onSuccess(Response<String> response) {
//                LogUtil.e("okgo__" + tag + response.body());
////                CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
////                List<Cookie> allCookie = cookieStore.getAllCookie();
////                LogUtil.e(tag+"所有cookie如下：" + allCookie.toString());
//                callback.Success(response.body(), cacheName);
//            }
//
//            /**
//             *  目前的逻辑是不去主动的去回调onFailure，真正onFailure的回调时机发生在onSuccess中，如果status != 0 直接回调onFailure
//             *  并传出后端想要展示的desc，而请求的onFailure只打印错误log并弹出网络异常Toast
//             */
//            @Override
//            public void onError(Response<String> response) {
//                super.onError(response);
//                LogUtil.e("onError");
//                LogUtil.e(tag + "------" + Log.getStackTraceString(response.getException()));
//                callback.Success(null, cacheName);
//
//            }
//        });
        postFile(url,params,cacheName,tag,false,true,callback);
    }
    /**
     * @param url       url还加注释吗
     * @param params    post需要携带的 params
     * @param cacheName 如果需要缓存，缓存的key，不需要传null或""
     * @param tag       1.log打印 2.有的框架可以传入根据的tag进行一系列操作，例如okgo可以根据tag取消请求操作
     * @param callback  回调接口 具体处理数据的操作在这里面
     */
    @Override
    public void postFile(String url, Map<String, Object> params, final String cacheName, final Object tag,final boolean successToast, final boolean errorToast, final ICallback callback) {

//        PostRequest<String> stringPostRequest = OkGo.<String>post(url)
//                .tag(tag);
//        Set<Map.Entry<String, Object>> entrySet = params.entrySet();
//        for (Map.Entry<String, Object> entry : entrySet) {
//            String key = entry.getKey();
//            Object value = entry.getValue();
//            if ("pic".equals(key)){
//                stringPostRequest.params(key, (File)value );
//            }else{
//                stringPostRequest.params(key, (String) value );
//            }
//        }
//        stringPostRequest.execute(new StringCallback() {
//            @Override
//            public void onSuccess(Response<String> response) {
//                LogUtil.e("okgo__" + tag + response.body());
//                callback.Success(response.body(), cacheName,successToast);
//            }
//
//            /**
//             *  目前的逻辑是不去主动的去回调onFailure，真正onFailure的回调时机发生在onSuccess中，如果status != 0 直接回调onFailure
//             *  并传出后端想要展示的desc，而请求的onFailure只打印错误log并弹出网络异常Toast
//             */
//            @Override
//            public void onError(Response<String> response) {
//                super.onError(response);
//                LogUtil.e("onError");
//                LogUtil.e(tag + "------" + Log.getStackTraceString(response.getException()));
//                callback.Success(null, cacheName,successToast);
//
//            }
//        });
        PostRequest<String> postRequest= OkGo.post(url);
        Set<Map.Entry<String, Object>> entrySet = params.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if ("pic".equals(key)){
                postRequest.params(key, (File)value );
            }else{
                postRequest.params(key, (String) value );
            }
        }
        Observable<Response<String>> observable = postRequest
                .tag(tag)
                .converter(new StringConvert())
                .adapt(new ObservableResponse<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        callback.getData(tag,cacheName,observable,successToast,errorToast,callback);
    }

    @Override
    public void get(String url, Map<String, Object> params, final String cacheName, final Object tag, final ICallback callback) {

//        GetRequest<String> stringGetRequest = OkGo.<String>get(url)
//                .tag(tag);
////                .headers("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; TencentTraveler 4.0)");
//
//        Set<Map.Entry<String, Object>> entrySet = params.entrySet();
//        for (Map.Entry<String, Object> entry : entrySet) {
//            stringGetRequest.params(entry.getKey(), (String) entry.getValue());
//        }
//        stringGetRequest.execute(new StringCallback() {
//            @Override
//            public void onSuccess(Response<String> response) {
////                CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
////                List<Cookie> allCookie = cookieStore.getAllCookie();
////                LogUtil.e(tag+"所有cookie如下：" + allCookie.toString());
//                LogUtil.e("okgo__" + tag + response.body());
////                ToastUtil.showToast(APP.getContext(),response.body());
//                callback.Success(response.body(), cacheName);
//
//            }
//
//            @Override
//            public void onError(Response<String> response) {
//                super.onError(response);
//                LogUtil.e("onError");
//                LogUtil.e(tag + "------" + Log.getStackTraceString(response.getException()));
//                callback.Success(null, cacheName);
//            }
//        });

        GetRequest<String> postRequest= OkGo.get(url);

        if (params!= null){
            Set<Map.Entry<String, Object>> entrySet = params.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                String key = entry.getKey();
                Object value = entry.getValue();
                postRequest.params(key, (String) value );
            }
        }
        Observable<Response<String>> observable = postRequest
                .tag(tag)
                .converter(new StringConvert())
                .adapt(new ObservableResponse<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        callback.getData(tag,cacheName,observable,false,true,callback);
    }



    /**
     * @param url
     * @param params
     * @param cacheName
     * @param tag
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T postExecute(String url, Map<String, Object> params, final String cacheName, final Object tag,Class<T> tClass) {
        LogUtil.e("okgo 开始请求");
        PostRequest<T> request = OkGo.<T>post(url).tag(tag);
        Set<Map.Entry<String, Object>> entrySet = params.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            request.params(entry.getKey(), String.valueOf(entry.getValue()));
        }
        String json = "";
        try {
            okhttp3.Response execute = request.execute();
            json = execute.body().string();
            LogUtil.e("okgo__" + tag + json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpCallBack3 httpCallBack3 = new HttpCallBack3();
        T t = (T) httpCallBack3.getData(tag,json,tClass);

        return t;
    }


}
