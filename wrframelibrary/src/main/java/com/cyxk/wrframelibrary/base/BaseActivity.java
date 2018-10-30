package com.cyxk.wrframelibrary.base;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.cyxk.wrframelibrary.R;
import com.cyxk.wrframelibrary.base.rxbase.RxManager;
import com.cyxk.wrframelibrary.utils.NetWorkUtils;
import com.cyxk.wrframelibrary.utils.StatusBarUtil;
import com.cyxk.wrframelibrary.utils.ToastUitl;
import com.cyxk.wrframelibrary.view.swipbackhelper.SwipeBackHelper;
import com.cyxk.wrframelibrary.view.swipbackhelper.SwipeBackPage;

import java.lang.ref.WeakReference;


/**
 * BaseActivity
 */

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity implements IContext {

    protected View view;
    public RxManager mRxManager;
    protected boolean isLightStatusBar = true;
    protected T mViewBinding;
    private Handler mActivityHandler;
    private boolean mResumed;
    //当前Activity是否走了destory
    private boolean mActivityDestroyed = true;
    //当点击外部布局时是否需要隐藏软件盘
    protected boolean mNeedHideKeyBord;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mActivityDestroyed = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityDestroyed = false;
        mActivityHandler = new ActivityHandler(this);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        mActivityDestroyed = false;
        mRxManager = new RxManager();
        StatusBarUtil.setMIUIStatusBarDarkIcon(this, isLightStatusBar());
        StatusBarUtil.setMeizuStatusBarDarkIcon(this, isLightStatusBar());
        AppManager.getAppManager().addActivity(this);
        beforeLoading();
        mViewBinding = DataBindingUtil.setContentView(this, getLayoutId());
        initView();
        initListener();
        initData();
        if (isSetSwipeBack()) {
            initSwiperBackHelper();
            setSwipeBack();
        }
    }

    private void setSwipeBack() {
        SwipeBackPage swipeBackPage = SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        if (isSetSwipeBack()) {
            swipeBackPage.setSwipeBackEnable(true);
        } else {
            SwipeBackHelper.getCurrentPage(this).setDisallowInterceptTouchEvent(true);
        }
    }

    protected abstract boolean isSetSwipeBack();

    private void initSwiperBackHelper() {
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(true)
                .setSwipeSensitivity(0.5f)
                .setSwipeRelateEnable(true)
                .setSwipeRelateOffset(300);
    }

    /**
     * 处理开始加载前的操作
     */
    protected void beforeLoading() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mResumed = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mResumed = false;
        //https://blog.csdn.net/z1074971432/article/details/10517449
        if (isFinishing()) {
            destory();
        }
    }

    @Override
    protected void onDestroy() {
        destory();
        super.onDestroy();
    }

    private void destory() {
        if (!mActivityDestroyed) {
            mActivityDestroyed = true;
            //Activity结束时移除handler中的消息
            if (mActivityHandler != null) {
                mActivityHandler.removeCallbacksAndMessages(null);
            }
            unRegister();
            Glide.get(this).clearMemory();
            if (mRxManager != null)
                mRxManager.clear();
            if (isSetSwipeBack()) {
                SwipeBackHelper.onDestroy(this);
            }
            AppManager.getAppManager().finishActivity(this);
        }
    }

    /**
     * 一些需要反注册的操作
     */
    protected abstract void unRegister();

    /**
     * @param context
     * @param targetActivity
     * @param bundle
     * @param needAnimal
     */
    public void jumpToActivity(Context context, Class<?> targetActivity, Bundle bundle, boolean needAnimal) {
        Intent intent = new Intent(context, targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        start_Activity(intent, needAnimal);
    }

    /**
     * @param context
     * @param targetActivity
     * @param requestCode
     * @param bundle
     * @param needAnimal
     */
    public void jumpToActivityForResult(Context context, Class<?> targetActivity, int requestCode, Bundle bundle, boolean needAnimal) {
        Intent intent = new Intent(context, targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        start_ActivityForResult(this, intent, requestCode, needAnimal);
    }


    /**
     * 必须实现，返回想要展示的viewId
     *
     * @return
     */
    protected abstract int getLayoutId();

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

    //点击空白区域自动隐藏软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mNeedHideKeyBord) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideKeyboard(v, ev)) {
                    hideKeyboard(v.getWindowToken());
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    public void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager mIm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mIm.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 关闭 Activity
     */
    public void finishActivity(boolean needAnimal) {
        super.finish();
        if (needAnimal) {
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        }
    }

    /**
     * true代表网络异常
     *
     * @return
     */
    public boolean netError() {
        if (NetWorkUtils.getAPNType(APP.getApplication()) == 0) {
//            ToastUitl.showShort(AppConstants.NET_ERROR);
            return true;
        }
        return false;
    }

    /**
     * 打开Activity
     */
    public void start_Activity(Intent intent, boolean needAnimal) {
        startActivity(intent);
        if (needAnimal) {
            overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_stay_orig);
        }
    }

    /**
     * 打开Activity
     */
    public void start_ActivityForResult(BaseActivity<T> tBaseActivity, Intent intent, int code, boolean needAnimal) {
        startActivityForResult(intent, code);
        if (needAnimal) {
            overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_stay_orig);
        }
    }

    public boolean isActivityDestroyed() {
        return mActivityDestroyed || isDestroyed() || isFinishing();
    }

    public boolean isActivityAlive() {
        return !isActivityDestroyed();
    }

    @Override
    public boolean isAlive() {
        return isActivityAlive();
    }

    public boolean isResume() {
        return mResumed;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // activity销毁时不保存fragment和view树信息,新建时直接创建新的fragment和view树
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

    public boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishActivity(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected boolean isLightStatusBar() {
        return isLightStatusBar;
    }

    /**
     * 获取handler
     */
    protected Handler getHandler() {
        return mActivityHandler;
    }

    protected void handleMessage(Message msg) {
    }

    protected static class ActivityHandler extends Handler {

        private WeakReference<BaseActivity> mActivityWeakReference;

        ActivityHandler(BaseActivity activity) {
            mActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void dispatchMessage(Message msg) {
            BaseActivity activity = mActivityWeakReference.get();
            if (activity != null && activity.isActivityAlive()) {
                super.dispatchMessage(msg);
            }
        }

        @Override
        public void handleMessage(Message msg) {
            BaseActivity activity = mActivityWeakReference.get();
            if (activity != null && activity.isActivityAlive()) {
                super.handleMessage(msg);
            }
        }
    }

}
