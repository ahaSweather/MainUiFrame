package com.example.a51425.mainuiframe.ui.TestTask;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cyxk.wrframelibrary.base.MyBaseFragment;
import com.cyxk.wrframelibrary.utils.LogUtil;
import com.cyxk.wrframelibrary.utils.NetWorkUtils;
import com.cyxk.wrframelibrary.utils.ToastUtil;
import com.cyxk.wrframelibrary.utils.Utils;
import com.example.a51425.mainuiframe.APP;
import com.example.a51425.mainuiframe.R;
import com.example.a51425.mainuiframe.bean.SpeciesBean;
import com.example.a51425.mainuiframe.bean.TestBean;
import com.example.a51425.mainuiframe.constant.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class TestFragment extends MyBaseFragment implements TestContract.View  {


    @BindView(R.id.shareFragmentRecyclerview)
    public RecyclerView mRecyclerView;

    @BindView(R.id.tv_base_title)
    TextView mTitle;
    @BindView(R.id.tv_release)
    TextView mComplete;

    private LinearLayoutManager mLinearLayoutManager;


    private List<TestBean> mData = new ArrayList<>();
    private int page ;
    private Unbinder mBind;
    private TestAdapter mAdapter;
    private TestFragmentPresenter mPresenter;
    private int mOldPosition = -1;

    @Override //返回fragment需要的布局
    public View getContentView() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.home_fragment, null);
        mPresenter = new TestFragmentPresenter(this,new TestLogic());
        return view;
    }

    @Override
    protected void registerBind(MyBaseFragment myBaseFragment, View view) {
        mBind = ButterKnife.bind(myBaseFragment, view);
    }

    @Override
    protected void unRegister() {
        if (mBind!= null)mBind.unbind();

    }

    @Override
    public void initView() {
        mTitle.setText("选择话题");
//        mActivity.getRelease().setVisibility(View.VISIBLE);
//        mActivity.getRelease().setText("发布");
        Utils.setVisibility1(mComplete);
        mComplete.setText("发布");

        LogUtil.e(getClass().getName()+"_________initData");
//        stateLayout.showContentView();
        initRecyclerView();
        if (mOldPosition == -1){
            mComplete.setTextColor(getResources().getColor(R.color.gray_82));
        }else{
            //不知道你们用什么颜色
            mComplete.setTextColor(getResources().getColor(R.color.red_ff1851));
        }
    }



    @Override
    public void initListener() {

        mComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOldPosition == -1){
                    ToastUtil.showToast(APP.getContext(),"请先选择内容");
                    return;
                }
                TestBean testBean = mData.get(mOldPosition);
                //打印testBean的属性，把需要的传过去
                LogUtil.e("testBean ---"+testBean.toString());
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ToastUtil.showToast(APP.getContext(),position+"");
                if (mOldPosition == position){
                    return;
                }
                mOldPosition = position;
                TestBean testBean = mData.get(position);
                if (testBean.getItemType() == TestBean.Title){
                    return;
                }
                for (int i = 0; i < mData.size(); i++) {
                    TestBean testBean2 = mData.get(i);
                    if (i == position){
                        testBean2.setAllowCheck(true);
                    }else{
                        testBean2.setAllowCheck(false);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });

    }
    @Override
    public void initData() {
        if (NetWorkUtils.getAPNType(mActivity) == 0){
            ToastUtil.showToast(APP.getContext(), Constants.NET_ERROR_DESC);
            showFailView();
        }else {
            mPresenter.initData();
        }
    }
    public void detailData(SpeciesBean bean) {

        showContentView();
        for (int i = 0; i < bean.getResult().size(); i++) {
            SpeciesBean.Result result = bean.getResult().get(i);
//            LogUtil.e("title ---"+result.getTitle());
            TestBean testBean = new TestBean(TestBean.Title);
            testBean.setId(result.getId());
            testBean.setPid(result.getPid());
            testBean.setShowTitle(result.getTitle());
            testBean.setChildrenList(result.getChildren());
            mData.add(testBean);
            for (int j = 0; j < result.getChildren().size(); j++) {
                SpeciesBean.Children children = result.getChildren().get(j);
//                LogUtil.e("name ---"+children.getTitle());
                TestBean testBean2 = new TestBean(TestBean.Content);
                testBean2.setAllowCheck(false);
                testBean2.setId(children.getId());
                testBean2.setPid(children.getPid());
                testBean2.setShowTitle(children.getTitle());
                testBean2.setChild_children_list(children.getChildren());
                mData.add(testBean2);
            }
        }
        LogUtil.e("mData.size ---"+mData.size());
        mAdapter.setNewData(mData);

    }

    /**
     * 初始化主RecyclerView
     */
    private void initRecyclerView() {
        GridLayoutManager layoutManage = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(layoutManage);
        mAdapter = new TestAdapter(mData,this);
        mRecyclerView.setAdapter(mAdapter);
        View bottom = LayoutInflater.from(mActivity).inflate(R.layout.item_test_bottom, null);
        mAdapter.addFooterView(bottom);
        layoutManage.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position >= mAdapter.getData().size()){
                    return 3;
                }
                int type = mAdapter.getData().get(position).getItemType();//获得返回值
                //LogUtils.e("TAG","type:"+type);
                if (type == TestBean.Title ) {
                    return 3;//占三格
                } else {
                    return 1;
                }
            }
        });
    }


    @Override
    public void showLoadingView() {

    }

    @Override
    public void showEmptyView() {
        stateLayout.showEmptyView();
    }

    @Override
    public void showFailView() {
        stateLayout.showFailView();
    }

    @Override
    public void showContentView() {
        stateLayout.showContentView();
    }


}
