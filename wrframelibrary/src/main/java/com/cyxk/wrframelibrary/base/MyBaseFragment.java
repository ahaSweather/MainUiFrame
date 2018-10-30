package com.cyxk.wrframelibrary.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyxk.wrframelibrary.base.rxbase.RxManager;
import com.cyxk.wrframelibrary.view.StateLayout;

import java.lang.ref.WeakReference;


/**
 * BaseFragment
 */
public abstract class MyBaseFragment<T extends ViewDataBinding> extends Fragment implements IContext {
    protected String mTAG = getClass().getSimpleName();
    public BaseActivity mActivity;
    protected StateLayout stateLayout;
    protected boolean isFirst = true;
    protected boolean isPrepared;
    protected BasePresenter mPresenter;
    // 布局view
    protected T bindingView;
    private boolean mFragmentDestroyed = true;
    private RxManager mRxManager;
    private Handler mFragmentHandler;


    //获得可靠的上下文
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentHandler = new FragmentHandler(this);
    }

    public BaseActivity getmActivity() {
        return mActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentDestroyed = false;
        //防止Fragment重复加载
        if (stateLayout == null) {
            // 说明这个Fragemnt的onCreateView方法是第一次执行
            bindingView = DataBindingUtil.inflate(getActivity().getLayoutInflater(), getLayoutId(), null, false);
            //对Fragment展示的布局进一步封装
            stateLayout = StateLayout.newInstance(mActivity, bindingView.getRoot());
            mRxManager = new RxManager();
            initPresenter();
            initView();
            initListener();
        } else {
            ViewGroup parent = (ViewGroup) stateLayout.getParent();
            if (parent != null) {
                parent.removeView(stateLayout);
            }
        }
        return stateLayout;
    }

    protected abstract void initPresenter();

    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean isAlive() {
        return isFragmentAlive();
    }

    public boolean isFragmentAlive() {
        return !isFragmentDestroyed();
    }

    public boolean isFragmentDestroyed() {
        return mFragmentDestroyed;
    }

    /**
     * 对initData再进一步的封装
     */
    protected void onVisible() {
        //保证ViewPager能够实现懒加载的方式并保证只有第一次进入这个Fragment的时候才进行数据的加载
//        LogUtil.e("isFirst _____________" + isFirst);
//        LogUtil.e("isVisible _____________" + isVisible);
//        LogUtil.e("isPrepared _____________" + isPrepared);
        onVisibleLoad();
        if (!isFirst || !isVisible || !isPrepared) {
            return;
        }
        loadData();
    }

    protected void onVisibleLoad() {
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

    @Override
    public void onDestroy() {
        destory();
        super.onDestroy();
    }

    private void destory() {
        mFragmentDestroyed = true;
        if (mPresenter != null)
            mPresenter.unsubscribe();
        if (mRxManager != null)
            mRxManager.clear();
        if (getHandler() != null)
            getHandler().removeCallbacksAndMessages(null);
        unRegister();
    }

    private void unRegister() {
    }

    /**
     * 返回Fragment自己的名称
     */
    protected CharSequence getTitle() {
        return getClass().getName();
    }

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
    public abstract void loadData();

    /**
     * 返回正常的界面想要展示的View的Id
     */
    public abstract int getLayoutId();


    /**
     * 获取handler
     */
    protected Handler getHandler() {
        return mFragmentHandler;
    }

    protected void handleMessage(Message msg) {
    }

    protected static class FragmentHandler extends Handler {

        private WeakReference<MyBaseFragment> mFragmentWeakReference;

        FragmentHandler(MyBaseFragment fragment) {
            mFragmentWeakReference = new WeakReference<>(fragment);
        }

        @Override
        public void dispatchMessage(Message msg) {
            MyBaseFragment fragment = mFragmentWeakReference.get();
            if (fragment != null && fragment.isFragmentAlive()) {
                super.dispatchMessage(msg);
            }
        }

        @Override
        public void handleMessage(Message msg) {
            MyBaseFragment fragment = mFragmentWeakReference.get();
            if (fragment != null && fragment.isFragmentAlive()) {
                fragment.handleMessage(msg);
            }
        }
    }
}
