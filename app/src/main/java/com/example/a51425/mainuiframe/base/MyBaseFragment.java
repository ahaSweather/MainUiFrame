package com.example.a51425.mainuiframe.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a51425.mainuiframe.utils.LogUtil;
import com.example.a51425.mainuiframe.ui.view.StateLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * BaseFragment
 */
public abstract class MyBaseFragment extends Fragment {

    protected Context context;
    protected StateLayout stateLayout;
    protected boolean isFirst = true;
    protected boolean isPrepared;
    private Unbinder mUnbinder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        //防止Fragment重复加载
        if (stateLayout == null) {
            // 说明这个Fragemnt的onCreateView方法是第一次执行
            View view = getContentView();
            mUnbinder = ButterKnife.bind(this, view);
            //对Fragment展示的布局进一步封装
            stateLayout = StateLayout.newInstance(context, view);
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


    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            isVisible = true;
            LogUtil.e(getClass().getName()+"__________setUserVisibleHint");
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
        LogUtil.e("isFirst _____________" + isFirst);
        LogUtil.e("isVisible _____________" + isVisible);
        LogUtil.e("isPrepared _____________" + isPrepared);
        if (!isFirst || !isVisible || !isPrepared) {
            return;
        }
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




    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder!=null){
            mUnbinder.unbind();
        }

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
     * 初始化数据的代码写在这个方法中
     */
    public abstract void initData();

    /**
     * 返回正常的界面想要展示的View或View的Id
     */
    public abstract View getContentView();
}
