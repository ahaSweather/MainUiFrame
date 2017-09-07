package com.example.a51425.mainuiframe.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a51425.mainuiframe.APP;
import com.example.a51425.mainuiframe.AppManager;
import com.example.a51425.mainuiframe.R;
import com.example.a51425.mainuiframe.utils.LogUtil;
import com.r0adkll.slidr.Slidr;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 *  BaseActivity
 */

public abstract class BaseActivity extends AppCompatActivity {


    protected Handler mUiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dealWith(msg);
        }
    };

    protected View view;
    private Unbinder mUnbinder;
    protected boolean isFirst=true;
    private InputMethodManager mIm;
    protected boolean showSlidr;//控制是否加入侧滑
    protected boolean hideStatusBar;
    private TextView baseTitle;
    private ImageView baseBack;
    private boolean showBaseTitle = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeLoading();
        AppManager.getAppManager().addActivity(this);
        View inflate = null;
        if (showBaseTitle){
            inflate = LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        }else{
            inflate = getContentView();
        }
        super.setContentView(inflate);
        if (showBaseTitle){
            FrameLayout baseFrame = (FrameLayout) inflate.findViewById(R.id.fr_base);
            baseTitle = (TextView) inflate.findViewById(R.id.tv_base_title);
            baseBack = (ImageView) inflate.findViewById(R.id.iv_base_back);
            view = getContentView();
            mUnbinder = ButterKnife.bind(view.getContext(), view);
            baseFrame.addView(view);
        }else {
            mUnbinder = ButterKnife.bind(inflate.getContext(), inflate);
        }
        setSlidr(showSlidr);
        setStatus(hideStatusBar);
        //butterknife绑定

        initView();
        initListener();
    }



    /**
     * 处理开始加载前的操作
     */
    protected void beforeLoading() {

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
     * 处理每次都刷新的逻辑
     */
    protected void needRefresh() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e(getClass().getName()+"destory___");
        //Activity结束时移除handler中的消息
        if (mUiHandler!=null){
            mUiHandler.removeCallbacksAndMessages(null);
        }
        //Activity结束时解除butterknife的绑定
        if (mUnbinder!=null){
            mUnbinder.unbind();
        }
        AppManager.getAppManager().removeStack(this);

    }

    /**
     * Activity跳转
     *
     * @param context
     * @param targetActivity
     * @param bundle
     */
    public void jumpToActivity(Context context, Class<?> targetActivity,
                               Bundle bundle) {
        Intent intent = new Intent(context, targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivityForResult
     *
     * @param context
     * @param targetActivity
     * @param requestCode
     * @param bundle
     */
    public void jumpToActivityForResult(Context context,
                                        Class<?> targetActivity, int requestCode, Bundle bundle) {
        Intent intent = new Intent(context, targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
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
    protected void initView() {

    }

    /**
     * 初始化Listener的代码写在这个方法中
     */
    protected void initListener() {
    }


    /**
     * 处理handler逻辑，
     * @param msg
     */
    protected void dealWith(Message msg) {

    }

    /**
     * 控制状态栏是否消失
     * @param hideStatusBar 默认 false  true看需求可以配合自定义的状态栏
     */
    private void setStatus(boolean hideStatusBar) {
        if (hideStatusBar){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                    this.getWindow().setStatusBarColor(Color.TRANSPARENT);
                } else {
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                }
//                getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            }
        }

    }

    /**
     * 设置侧滑是否出现 默认 false  true需要配合相应的 theme ,需要解决滑动冲突
     * @param showSlidr
     */
    private void setSlidr(boolean showSlidr) {
        if (showSlidr){
            try{
                Slidr.attach(this);
            }catch (Exception e){
                LogUtil.e(Log.getStackTraceString(e));
            }
        }

    }

    /**
     * 控制是否显示通用的title，默认为true，显示
     * @param baseTitleStatus
     */
    public void setBaseTitleStatus(boolean baseTitleStatus){
        showBaseTitle = baseTitleStatus;
    }




    /**
     * 获取状态栏的高度
     * @return
     */
    protected int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public void setBaseTitle(String title){
        LogUtil.e("setTitle 调用了");
        baseTitle.setText(title);
    }

    protected void setBaseBackStatus(int status){
        baseBack.setVisibility(status);
    }

}
