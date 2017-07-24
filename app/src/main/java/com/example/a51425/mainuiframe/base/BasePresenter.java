package com.example.a51425.mainuiframe.base;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import com.example.a51425.mainuiframe.ui.fragment.HomeFragment;
import com.example.a51425.mainuiframe.ui.fragment.MessageFragment;
import com.example.a51425.mainuiframe.ui.fragment.MyFragment;
import com.example.a51425.mainuiframe.utils.SharedPreferanceUtils;

public class BasePresenter<T> {
    public   T mIview;
    public  T mContext;

    protected Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dealWith(msg);
        }
    };

    public BasePresenter(T t) {
        this.mContext = t;
        this.mIview = t;
    }


    protected void dealWith(Message msg) {

    }

}
