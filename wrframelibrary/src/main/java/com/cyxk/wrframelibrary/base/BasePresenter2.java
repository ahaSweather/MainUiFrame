package com.cyxk.wrframelibrary.base;

import android.os.Handler;
import android.os.Message;

import com.cyxk.wrframelibrary.utils.SharedPreferanceUtils;


/**
 * 作者：Created by wr
 * 时间: 2017/11/27 11:21
 */
public abstract class BasePresenter2<T  ,M >  {

    protected SharedPreferanceUtils sharedPreferanceUtils = SharedPreferanceUtils.getSp();
    protected T context;
    protected M mModel;



    public BasePresenter2(T fragment, M model) {
        context = fragment;
        mModel =  model;

    }

    public abstract void initData();

}
