package com.cyxk.wrframelibrary.net;

import com.lzy.okgo.model.Response;

import rx.Observable;

/**
 * Created by 51425 on 2017/7/2.
 */

public interface ICallback {
    void Success(String result, String cacheName);
    void onFailure(String error);
    void getData(Object tag, String cacheName, Observable<Response<String>> observable, boolean successToast, boolean errorToast, ICallback callback);
}
