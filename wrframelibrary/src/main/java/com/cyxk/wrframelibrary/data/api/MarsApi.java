package com.cyxk.wrframelibrary.data.api;

import android.util.SparseArray;

import com.cyxk.wrframelibrary.base.APP;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wr on 2018/1/23.
 */

public class MarsApi {
    private static final int DEFAULT_TIMEOUT = 50000;
//    private static final int DEFAULT_TIMEOUT = 20;
    private MarsService sService;
    private Retrofit sRetrofit;

    // Prevent direct instantiation.
    private MarsApi(int hostType) {
        init(hostType);
    }

    private void init(int hostType) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        if (BuildConfig.DEBUG)
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        else loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        //缓存
        File cacheFile = new File(APP.getApplication().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request build = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/json")
                                .build();
                        return chain.proceed(build);
                    }
                })
                .cache(cache);
        sRetrofit = new Retrofit.Builder().baseUrl(ApiConstants.getHost(hostType))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build();
        sService = sRetrofit.create(MarsService.class);
    }


    private static MarsApi retrofitManager;
    private static SparseArray<MarsApi> sRetrofitManager = new SparseArray<>(ApiConstants.TYPE_COUNT);

    public static synchronized MarsService getsService(int hostType) {
        retrofitManager = sRetrofitManager.get(hostType);
        if (retrofitManager == null) {
            retrofitManager = new MarsApi(hostType);
            sRetrofitManager.put(hostType, retrofitManager);
        }
        return retrofitManager.sService;
    }

}
