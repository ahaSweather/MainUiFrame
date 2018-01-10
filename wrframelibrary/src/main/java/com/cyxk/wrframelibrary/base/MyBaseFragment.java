package com.cyxk.wrframelibrary.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyxk.wrframelibrary.config.ConfigUtil;
import com.cyxk.wrframelibrary.utils.LogUtil;
import com.cyxk.wrframelibrary.utils.SharedPreferanceUtils;
import com.cyxk.wrframelibrary.view.StateLayout;

import java.util.ArrayList;


/**
 * BaseFragment
 */
public abstract class MyBaseFragment<V extends BaseView,M extends BaseModel> extends Fragment {

    public BaseActivity mActivity;
    protected StateLayout stateLayout;
    protected boolean isFirst = true;
    protected boolean isPrepared;

    private boolean isShow;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dealMessage(msg);
        }
    };
    private boolean mShareClick = true;
    public SharedPreferanceUtils mSharedPreferanceUtils;


    //获得可靠的上下文
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
    }

    public BaseActivity getmActivity() {
        return mActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSharedPreferanceUtils = SharedPreferanceUtils.getSp();
        //防止Fragment重复加载
        if (stateLayout == null) {
            // 说明这个Fragemnt的onCreateView方法是第一次执行
            View view = getContentView();
            registerBind(this, view);
            //对Fragment展示的布局进一步封装
            stateLayout = StateLayout.newInstance(mActivity, view);
            initView();
            initListener();
            //
            isPrepared = true;
        } else {
            ViewGroup parent = (ViewGroup) stateLayout.getParent();
            if (parent != null) {
                parent.removeView(stateLayout);
            }
        }
        return stateLayout;
    }

    protected abstract void registerBind(MyBaseFragment<V, M> vmMyBaseFragment, View view);


    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            isVisible = true;
//            LogUtil.e(getClass().getName()+"__________setUserVisibleHint");
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.e(getClass().getName()+"__________onResume");
        onVisible();
    }

    /**
     * 对initData再进一步的封装
     */
    protected void onVisible() {
        //保证ViewPager能够实现懒加载的方式并保证只有第一次进入这个Fragment的时候才进行数据的加载
//        LogUtil.e("isFirst _____________" + isFirst);
//        LogUtil.e("isVisible _____________" + isVisible);
//        LogUtil.e("isPrepared _____________" + isPrepared);
        if (!isFirst || !isVisible || !isPrepared) {
            return;
        }
        LogUtil.e(getClass().getName()+"开始了加载数据");
        initData();
        isFirst = false;
    }


    /**
     * 不可见
     */
    protected void onInvisible() {


    }


    /**
     * 查找View，增加这个方法是为了略强转
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T findView(int id) {
        T view = (T) stateLayout.findViewById(id);
        return view;
    }

    public boolean clickLimit() {



        if (!mShareClick) {
            LogUtil.e("防止多次点击");
            return true;
        }
        mShareClick = false;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mShareClick = true;
            }
        }, ConfigUtil.CLICK_MIDDLE_TIME);
        return false;
    }

    /**
     * 判断是否强制跳到登陆界面
     * @return
     */
    public boolean haneToLoginActivity(){
        if (TextUtils.isEmpty(mSharedPreferanceUtils.getString(ConfigUtil.TOKEN,""))){

            return true;
        }
        return false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null){
            handler.removeCallbacksAndMessages(null);
        }
        unRegister();

    }

    protected abstract void unRegister();

    protected void dealMessage(Message msg) {

    }

    /**
     * 返回Fragment自己的名称
     */
    protected  CharSequence getTitle(){
        return getClass().getName();
    };

    /**
     * 初始化View相关的代码写在这个方法中
     */
    public abstract void initView();

    /**
     * 初始化Listener的代码写在这个方法中
     */
    public abstract void initListener();

    /**
     * 初始化数据的代码写在这个方法中，这个方法才是加载数据的方法，onVisible 内做了判断，保证 initData() 方法只执行了一次
     */
    public abstract void initData();

    /**
     * 返回正常的界面想要展示的View或View的Id
     */
    public abstract View getContentView();


    public  void getData(ArrayList data){};

    public boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }




}
