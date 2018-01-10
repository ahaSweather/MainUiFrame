package com.cyxk.wrframelibrary.framework;

/**
 * 作者：Administrator
 * 时间: 2017/11/8 13:34
 * 版本：${VERSION}
 */
public interface ImageListener<T> {

    void onSuccess(T t);
    void onFailed();
}
