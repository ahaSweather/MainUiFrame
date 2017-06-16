package com.example.a51425.mainuiframe.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.a51425.mainuiframe.R;
import com.example.a51425.mainuiframe.base.MyBaseFragment;
import com.example.a51425.mainuiframe.bean.HomeFragmentBean;
import com.example.a51425.mainuiframe.ui.activity.WebViewActivity;
import com.example.a51425.mainuiframe.ui.adapter.ShareFragmentAdapter;
import com.example.a51425.mainuiframe.ui.activity.MainActivity;
import com.example.a51425.mainuiframe.ui.fragment.IView.IHomeFragmentView;
import com.example.a51425.mainuiframe.ui.presenter.HomeFragmentPresenter;
import com.example.a51425.mainuiframe.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class HomeFragment extends MyBaseFragment implements IHomeFragmentView {


    @BindView(R.id.shareFragmentRecyclerview)
    public RecyclerView mRecyclerView;
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

    }

    @Override
    public void initData() {
        LogUtil.e(getClass().getName()+"_________initData");
//        stateLayout.showContentView();
        page = 1;
        mHomeFragmentPresenter.initData(mData,page,mShareAdapter);
//        if (mData.size()!=0){
//            stateLayout.showContentView();
//            mShareAdapter.addData(mData);
//        }
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
