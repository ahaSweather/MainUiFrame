package com.example.a51425.mainuiframe.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.a51425.mainuiframe.R;
import com.example.a51425.mainuiframe.base.MyBaseFragment;
import com.example.a51425.mainuiframe.bean.HomeFragmentBean;
import com.example.a51425.mainuiframe.ui.activity.WebViewActivity;
import com.example.a51425.mainuiframe.ui.adapter.ShareFragmentAdapter;
import com.example.a51425.mainuiframe.ui.activity.MainActivity;
import com.example.a51425.mainuiframe.ui.fragment.IView.IHomeFragmentView;
import com.example.a51425.mainuiframe.ui.presenter.HomeFragmentPresenter;
import com.example.a51425.mainuiframe.ui.view.PullToRefreshView;
import com.example.a51425.mainuiframe.ui.view.RentalsSunHeaderView;
import com.example.a51425.mainuiframe.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;


public class HomeFragment extends MyBaseFragment implements IHomeFragmentView {


    @BindView(R.id.shareFragmentRecyclerview)
    public RecyclerView mRecyclerView;
    @BindView(R.id.store_house_ptr_frame)
    public PtrFrameLayout ptrFrame;
    @BindView(R.id.tv_base_title)
    TextView mTitle;

    private HomeFragmentPresenter mHomeFragmentPresenter;
    private MainActivity mainActivity;

    private LinearLayoutManager mLinearLayoutManager;
    private List shareList;
    private ShareFragmentAdapter mShareAdapter;
    private List<HomeFragmentBean> mData = new ArrayList<>();
    private int page ;

    @Override //返回fragment需要的布局
    public View getContentView() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.home_fragment, null);
        mainActivity = (MainActivity) mActivity;
        mHomeFragmentPresenter = new HomeFragmentPresenter(this);

        return view;
    }

    @Override
    public void initView() {
        initRecyclerView();
        initHead();


    }



    @Override
    public void initListener() {
        mShareAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (mData.size()==0){
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean",mData.get(position));
                mainActivity.jumpToActivity(mainActivity,WebViewActivity.class,bundle);
            }
        });

        mShareAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                LogUtil.e("loadMore");
                page++;
                mHomeFragmentPresenter.initData(mData,page, mShareAdapter);

            }
        },mRecyclerView);

        ptrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrFrame.autoRefresh(true);
            }
        }, 100);

        ptrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                mHomeFragmentPresenter.initData(mData,page,mShareAdapter);
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.e("开始下拉刷新——————");
                        ptrFrame.refreshComplete();
                    }
                }, 1800);
            }
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                // 默认实现，根据实际情况做改动
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });


    }
    @Override
    public void initData() {
        mTitle.setText("hello3");
        LogUtil.e(getClass().getName()+"_________initData");
//        stateLayout.showContentView();
        page = 1;
        mHomeFragmentPresenter.initData(mData,page,mShareAdapter);

    }





    /**
     * 初始化主RecyclerView
     */
    private void initRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mShareAdapter = new ShareFragmentAdapter(shareList);
        mRecyclerView.setAdapter(mShareAdapter);
//        mShareAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
    }
    /**
     * 初始化头部
     */
    private void initHead() {
        final RentalsSunHeaderView header = new RentalsSunHeaderView(getContext());
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, PtrLocalDisplay.dp2px(10));
        header.setUp(ptrFrame);
        ptrFrame.setLoadingMinTime(1000);
        ptrFrame.setDurationToCloseHeader(1500);
        ptrFrame.setHeaderView(header);
        ptrFrame.addPtrUIHandler(header);
    }


    @Override
    public void showContentView() {
        stateLayout.showContentView();
    }

    @Override
    public void showEmptyView() {
        stateLayout.showEmptyView();
    }

    @Override
    public void showLodingView() {
        stateLayout.showLoadingView();
    }

    @Override
    public void showFailView() {
        stateLayout.showFailView();
    }


}
