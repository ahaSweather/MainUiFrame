package com.example.a51425.mainuiframe.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.a51425.mainuiframe.APP;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 *  BaseActivity
 */

public abstract class BaseActivity extends AppCompatActivity {


    protected Handler mUiHandler;
    protected View view;
    private Unbinder mUnbinder;
    protected boolean isFirst=true;
    private InputMethodManager mIm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        APP.getInstance().addActivity(this);
        view = getContentView();
        setContentView(view);
        //butterknife绑定
        mUnbinder = ButterKnife.bind(view.getContext(), view);
        initView();
        initListener();
    }




    @Override
    protected void onResume() {
        super.onResume();
        if (isFirst) {
            initData();
            isFirst=false;
        }else{
             needRefresh();
        }
    }

    /**
     * 需要每次都刷新的时候，里面和initData中有重合的地方可以封装一下使用
     */
    protected void needRefresh() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity结束时移除handler中的消息
        if (mUiHandler!=null){
            mUiHandler.removeCallbacksAndMessages(null);
        }
        //Activity结束时接触butterknife的绑定
        if (mUnbinder!=null){
            mUnbinder.unbind();
        }
        APP.rmoveActivity(this);

    }

    /**
     * 必须实现，返回想要展示的view
     * @return
     */
    protected abstract View getContentView();

    /**
     * 进行数据的更新
     */
    protected  void initData(){}

    /**
     * 初始化View相关的代码写在这个方法中
     */
    private void initView() {

    }

    /**
     * 初始化Listener的代码写在这个方法中
     */
    protected void initListener() {
    }





}
