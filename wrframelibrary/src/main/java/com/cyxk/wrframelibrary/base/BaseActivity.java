package com.cyxk.wrframelibrary.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cyxk.wrframelibrary.R;
import com.cyxk.wrframelibrary.config.ConfigUtil;
import com.cyxk.wrframelibrary.net.HttpHelper;
import com.cyxk.wrframelibrary.net.OkGoProcessor;
import com.cyxk.wrframelibrary.utils.LogUtil;
import com.cyxk.wrframelibrary.utils.SharedPreferanceUtils;
import com.cyxk.wrframelibrary.utils.Utils;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;




/**
 * BaseActivity
 */

public abstract class BaseActivity extends AppCompatActivity {


    public Handler mUiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            dealWith(msg);
        }
    };
    protected View view;
//    private Unbinder mUnbinder;
    protected boolean isFirst = true;
    private InputMethodManager mIm;
    protected boolean showSlidr;//控制是否加入侧滑
    protected boolean hideStatusBar;
    private TextView baseTitle;
    private ImageView baseBack;
    private boolean showBaseTitle = true;
    private RelativeLayout baseTitleView;
    private ProgressBar baseLoding;
    protected SharedPreferanceUtils sharedPreferanceUtils;
    private RelativeLayout failView;
    private RelativeLayout emptyView;
    private FrameLayout mBaseFrame;
    private boolean mShareClick = true;
    private TextView release;
    private String cancleTag;
    public ArrayList<String> cancleList = new ArrayList<>();
    private ImageView imgRelease;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //懒加载，并做了单例防止重复加载
        OkGoProcessor okGoProcessor = OkGoProcessor.getOkGoProcessor(APP.getApplication());
        HttpHelper.init(okGoProcessor);
        sharedPreferanceUtils = SharedPreferanceUtils.getSp();
        AppManager.getAppManager().addActivity(this);
        //在开始加载前做一些操作，不是强制的，例如控制是否使用
        beforeLoading();
        View inflate = null;
        if (showBaseTitle) {
            inflate = LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        } else {
            inflate = getContentView();
        }
        super.setContentView(inflate);
        if (showBaseTitle) {
            mBaseFrame = (FrameLayout) inflate.findViewById(R.id.fr_base);
//            baseTitleView = (RelativeLayout) inflate.findViewById(R.id.rl_base_title);
            baseTitle = (TextView) inflate.findViewById(R.id.tv_basic_title);
            baseBack = (ImageView) inflate.findViewById(R.id.iv_base_back);
            imgRelease = (ImageView) inflate.findViewById(R.id.iv_release);
//            baseLoding = (RelativeLayout) inflate.findViewById(R.id.rl_base_loading);
            baseLoding = (ProgressBar) inflate.findViewById(R.id.loadingView);

            failView = (RelativeLayout) inflate.findViewById(R.id.failView);
            emptyView = (RelativeLayout) inflate.findViewById(R.id.emptyView);
            release = (TextView) inflate.findViewById(R.id.tv_release);
            view = getContentView();
//            mUnbinder = ButterKnife.bind(view.getContext(), view);
            registerBind(view.getContext(), view);
            mBaseFrame.addView(view);
            failView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLayoutClickListener != null) {
                        onLayoutClickListener.click();
                    }
                }
            });
            emptyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLayoutClickListener != null) {
                        onLayoutClickListener.click();
                    }
                }
            });

        } else {
            registerBind(inflate.getContext(), inflate);
//            mUnbinder = ButterKnife.bind(inflate.getContext(), inflate);
        }

        setSlidr(showSlidr);
        setStatus(true);
//        setStatus(hideStatusBar);
        initView();
        initListener();
    }

    protected abstract void registerBind(Context context, View view);

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
            isFirst = false;
        } else {
            needRefresh();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    /**
     * 处理每次都刷新的逻辑
     */
    protected void needRefresh() {

    }


    /**
     * 设置取消tag
     *
     * @param tag
     */
    public void setCallTag(String tag) {
        this.cancleTag = tag;
    }


    @Override
    protected void onDestroy() {

        LogUtil.e(getClass().getName() + "destory___");
        //Activity结束时移除handler中的消息
        if (mUiHandler != null) {
            mUiHandler.removeCallbacksAndMessages(null);
        }
        //Activity结束时解除butterknife的绑定
            
//        if (mUnbinder != null) {
//            mUnbinder.unbind();
//        }
        

        if (!TextUtils.isEmpty(cancleTag)) {
            LogUtil.e("cancleTag  ----" + cancleTag);
            OkGo.cancelTag(OkGoProcessor.getOkHttpClient(), cancleTag);
            RxApiManager.get().cancel(cancleTag);
        }
        if (cancleList != null && cancleList.size() != 0){
            for (int i = 0; i < cancleList.size(); i++) {
                LogUtil.e("cancleList  ----"+i + cancleList.get(i));
                OkGo.cancelTag(OkGoProcessor.getOkHttpClient(), cancleList.get(i));
                RxApiManager.get().cancel(cancleList.get(i));
            }
        }
        AppManager.getAppManager().removeStack(this);
        unRegister();
        super.onDestroy();
    }

    protected abstract void unRegister();

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
//        startActivity(intent);
        start_Activity(this, intent);
    }

//    public void toMineActivity(BaseActivity mActivity, Class<MinActivity> minActivityClass, Bundle bundle) {
//        Intent intent = new Intent(mActivity, minActivityClass);
//        if (null != bundle) {
//            intent.putExtras(bundle);
//        }
//        this.startActivity(intent);
//        this.overridePendingTransition(R.anim.push_bottom_out, R.anim.stop);
//    }

    public void finishMineActivity(Activity activity) {
        activity.finish();
        this.overridePendingTransition(R.anim.stop, R.anim.push_bottom_in);
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
//        startActivityForResult(intent, requestCode);
        start_ActivityForResult(this, intent, requestCode);
    }


    /**
     * 必须实现，返回想要展示的view
     *
     * @return
     */
    protected abstract View getContentView();

    /**
     * 进行数据的更新
     */
    protected abstract void initData();

    /**
     * 初始化View相关的代码写在这个方法中
     */
    protected abstract void initView();

    /**
     * 初始化Listener的代码写在这个方法中
     */
    protected abstract void initListener();


    /**
     * 处理handler逻辑，
     *
     * @param msg
     */
    protected void dealWith(Message msg) {

    }

    /**
     * 控制状态栏是否消失
     *
     * @param hideStatusBar 默认 false  true看需求可以配合自定义的状态栏
     */
    @Deprecated
    private void setStatus(boolean hideStatusBar) {
//        if (hideStatusBar) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                    this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                    this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//                    this.getWindow().setStatusBarColor(Color.TRANSPARENT);
//                } else {
//                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                }
////                getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
////                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//            }
//        }

        //安卓5.0
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setNavigationBarColor(Color.BLACK);
//        }


    }

    /**
     * 设置侧滑是否出现 默认 false  true需要配合相应的 theme
     *
     * @param showSlidr
     *
     */
    @Deprecated
    private void setSlidr(boolean showSlidr) {
        if (showSlidr) {
//            this.showSlidr = showSlidr;
//            try{
//                Slidr.attach(this);
//            }catch (Exception e){
//                LogUtil.e(Log.getStackTraceString(e));
//            }
//


        }

    }


    /**
     * 控制是否显示通用的title，默认为true，显示
     *
     * @param baseTitleStatus
     */
    public void setBaseTitleStatus(boolean baseTitleStatus) {
        showBaseTitle = baseTitleStatus;
    }


    /**
     * 获取状态栏的高度
     *
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


    /**
     * 设置公共标题内容
     *
     * @param title
     */
    public void setBaseTitle(String title) {
        baseTitle.setText(title);
    }

    /**
     * 返回公共标题内容
     *
     * @return
     */
    public TextView getBaseTitle() {
        return baseTitle;
    }

    /**
     * 设置公共返回键的头部状态
     *
     * @param status
     */
    protected void setBaseBackStatus(int status) {
        baseBack.setVisibility(status);
    }

    /**
     * 返回公共返回键
     *
     * @return
     */
    public ImageView getBaseBack() {
        return baseBack;
    }

    /**
     * 设置公共标题的头部状态
     *
     * @param status
     */
    public void setBaseTitleViewStatus(int status) {
        baseTitleView.setVisibility(status);
    }

    /**
     * 返回公共标题头部
     *
     * @return
     */
    public RelativeLayout getBaseTitleView() {
        return baseTitleView;
    }

    /**
     * 返回公共的release
     *
     * @return
     */
    public TextView getRelease() {
        return release;
    }

    public ImageView getImgRelease() {
        return imgRelease;
    }

    public void setImgReleaseBg(int resource) {
        Utils.setSrc(imgRelease,resource);
    }

    public void setReleaseStatus(int status) {
        release.setVisibility(status);
    }

    //点击空白区域自动隐藏软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 关闭 Activity
     *
     * @param activity
     */
    public void finish(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    /**
     * 打开Activity
     */
    public void start_Activity(Activity activity, Intent intent
    ) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }

    /**
     * 打开Activity
     */
    public void start_ActivityForResult(Activity activity, Intent intent, int code
    ) {
        activity.startActivityForResult(intent, code);
        activity.overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }


    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    public void hideKeyboard(IBinder token) {
        if (token != null) {
            mIm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mIm.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    protected boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                v.clearFocus();
                return true;
            }
        }
//        v.clearFocus();
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
        LogUtil.e("onSaveInstance走了————————");
    }


    public void setBaseLodingStatus(int status) {
        baseLoding.setVisibility(status);
    }

    protected void showFail() {
        if (isMainThread()) {
            setFailView();
        } else {
            mUiHandler.post(new Runnable() {
                @Override
                public void run() {
                    setFailView();
                }
            });
        }

    }



    protected void showEmpty() {
        if (!isMainThread()) {
            mUiHandler.post(new Runnable() {
                @Override
                public void run() {
                    setEmptyView();
                }
            });
        } else {
            setEmptyView();
        }


    }




    protected void showContent() {
        if (isMainThread()) {
            setContent();

        } else {
            mUiHandler.post(new Runnable() {
                @Override
                public void run() {
                    setContent();
                }
            });
        }


    }

    private void setFailView() {
        Utils.setVisibility1(failView);
        Utils.setGone(emptyView);
        Utils.setGone(mBaseFrame);
        Utils.setGone(baseLoding);
    }
    private void setEmptyView() {
        Utils.setVisibility1(emptyView);
        Utils.setGone(failView);
        Utils.setGone(mBaseFrame);
        Utils.setGone(baseLoding);
    }
    private void setContent() {
        Utils.setGone(emptyView);
        Utils.setGone(failView);
        Utils.setGone(baseLoding);
        Utils.setVisibility1(mBaseFrame);
    }
    /**
     * false 继续 true 中止
     *
     * @return
     */
    public boolean clickLimit() {

        if (!mShareClick) {
            LogUtil.e("防止多次点击");
            return true;
        }
        mShareClick = false;
        mUiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mShareClick = true;
            }
        }, ConfigUtil.CLICK_MIDDLE_TIME);
        return false;
    }

    public interface OnLayoutClickListener {
        void click();
    }

    public OnLayoutClickListener onLayoutClickListener;

    public void setOnLayoutClickListener(OnLayoutClickListener onLayoutClickListener) {
        this.onLayoutClickListener = onLayoutClickListener;
    }


    public boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (this instanceof MinActivity){
//                finishMineActivity(this);
//            }else{
//                finish(this);
//            }

            finish(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
