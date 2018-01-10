package com.cyxk.wrframelibrary.base;


/**
 * View 基类 定义Viewz 接口可以继承接口，抽象类智能实现接口
 * @param <T>
 */
public interface BaseView<T> {
    //    提示错误消息
//    void showErrorWithStatus(String msg);

    void showLoadingView();
    void showEmptyView();
    void showFailView();
    void showContentView();

}