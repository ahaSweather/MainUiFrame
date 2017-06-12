package com.example.a51425.mainuiframe.interfaces;

/**
 * Created by 51425 on 2017/6/12.
 */

public interface GetResultListener<T> {
    void onSuccess(T t);
    void onError();
}
