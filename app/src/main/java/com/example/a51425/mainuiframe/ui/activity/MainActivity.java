package com.example.a51425.mainuiframe.ui.activity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.cyxk.wrframelibrary.base.BaseActivity;
import com.cyxk.wrframelibrary.utils.LogUtil;
import com.cyxk.wrframelibrary.utils.ToastUtil;
import com.cyxk.wrframelibrary.view.ViewPagerMain;
import com.example.a51425.mainuiframe.R;
import com.example.a51425.mainuiframe.ui.ShareTask.ShareFragment;
import com.example.a51425.mainuiframe.ui.adapter.MainAdapter;
import com.example.a51425.mainuiframe.ui.serivce.JobHandlerService;
import com.example.a51425.mainuiframe.ui.serivce.LocalService;
import com.example.a51425.mainuiframe.ui.serivce.RemoteService;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    ViewPagerMain mViewpagerMain;
    LinearLayout mMainBottomRoot;
    private List<Fragment> fragments;
    private long mExitTime = 0L;

    @Override
    protected boolean isSetSwipeBack() {
        return false;
    }

    @Override
    protected void beforeLoading() {
        super.beforeLoading();
        try {
            getWindow().setFormat(PixelFormat.TRANSLUCENT);
        } catch (Exception e) {
            LogUtil.e(Log.getStackTraceString(e));
        }
    }

    @Override
    protected void unRegister() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        startService(new Intent(this, LocalService.class));
        startService(new Intent(this, RemoteService.class));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startService(new Intent(this, JobHandlerService.class));
        }

        mViewpagerMain = (ViewPagerMain) findViewById(R.id.viewpager_main);
        mMainBottomRoot = (LinearLayout) findViewById(R.id.main_bottom_root);
    }

    @Override
    protected void initListener() {

        //设置不能左右滑动
        mViewpagerMain.setIsScroll(false);
        //viewPager预加载1页
        mViewpagerMain.setOffscreenPageLimit(0);
        mViewpagerMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeUI(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setBottomListener();
    }

    @Override
    protected void initData() {
        initViewPager();
        //刚进来默认展示第1页的数据
        mViewpagerMain.setCurrentItem(0, false);
        changeUI(0);
        LogUtil.e(getClass().getName() + "_________initData");
    }

    /**
     * 初始化viewPager,此处正常应该展示对应的fragment，这里简单的加载了一个
     */
    private void initViewPager() {
        fragments = new ArrayList<>();
        fragments.add(new ShareFragment());
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager(), fragments);
        mViewpagerMain.setAdapter(adapter);
    }

    /**
     * 监听底部按钮的切换状态，mMainBottomRoot为底部按钮的根布局，这样封装的话如果底部按钮的数量有变动
     * 这段代码不用发生什么变动，而且下一个项目用到的话直接复制粘贴
     */
    private void setBottomListener() {
        int childCount = mMainBottomRoot.getChildCount();
        for (int i = 0; i < childCount; i++) {
            //获得子布局,为每个子view设置点击事件
            LinearLayout child = (LinearLayout) mMainBottomRoot.getChildAt(i);
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //通过mMainBottomRoot来获得点击view对应的索引值
                    int indexOfChild = mMainBottomRoot.indexOfChild(view);
                    //根据索引来改变UI效果
                    changeUI(indexOfChild);
                    //viewPager也跳到对应的Fragment中
                    mViewpagerMain.setCurrentItem(indexOfChild, false);
                }
            });
        }
    }

    /**
     * 当viewPager切换的时候更改底部按钮的UI状态
     *
     * @param position
     */
    private void changeUI(int position) {
        for (int i = 0; i < mMainBottomRoot.getChildCount(); i++) {
            if (i == position) {
                setEnable(mMainBottomRoot.getChildAt(i), false);
            } else {
                setEnable(mMainBottomRoot.getChildAt(i), true);
            }
        }
    }

    /**
     * 更改底部按钮UI状态,如果是view直接setEnable，如果是viewGroup递归遍历其中的view setEnable
     *
     * @param childAt
     * @param b
     */
    private void setEnable(View childAt, boolean b) {

        childAt.setEnabled(b);
        if (childAt instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) childAt).getChildCount(); i++) {
                setEnable(((ViewGroup) childAt).getChildAt(i), b);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime < 2000) {
                moveTaskToBack(true);
                return true;
            }
            mExitTime = System.currentTimeMillis();
            ToastUtil.showToast(MainActivity.this, "再按一次退出程序");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
