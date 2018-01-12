package com.example.a51425.mainuiframe.ui.TestTask2;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cyxk.wrframelibrary.base.MyBaseFragment;
import com.cyxk.wrframelibrary.utils.LogUtil;
import com.cyxk.wrframelibrary.utils.NetWorkUtils;
import com.cyxk.wrframelibrary.utils.ToastUtil;
import com.cyxk.wrframelibrary.utils.Utils;
import com.example.a51425.mainuiframe.APP;
import com.example.a51425.mainuiframe.R;
import com.example.a51425.mainuiframe.bean.SpeciesBean;
import com.example.a51425.mainuiframe.bean.TestBean;
import com.example.a51425.mainuiframe.constant.Constant;
import com.example.a51425.mainuiframe.constant.Constants;
import com.example.a51425.mainuiframe.ui.view.FlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class TestFragment2 extends MyBaseFragment implements TestContract2.View  {


    @BindView(R.id.ll_test_root)
    public LinearLayout mLlTestRoot;

    @BindView(R.id.tv_basic_title)
    TextView mTitle;
    @BindView(R.id.tv_release)
    TextView mComplete;

    public List<TestBean> mData = new ArrayList<>();
    private Unbinder mBind;
    private TestFragmentPresenter2 mPresenter;
    private int mOldPosition = -1;
    public HashMap mAlreadyCheck = new HashMap<Integer,TestBean>();
    private boolean allowRefresh = true;
    private int mCurrentPosition = 0;


    @Override //返回fragment需要的布局
    public View getContentView() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.test_fragment2, null);
        mPresenter = new TestFragmentPresenter2(this,new TestLogic2());
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
        Utils.setVisibility1(mComplete);
        mComplete.setText("发布");
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
                int size = mAlreadyCheck.size();
                if (size == 0){
                    ToastUtil.showToast(APP.getContext(),"请先选择内容");
                    return;
                }
                ToastUtil.showToast(APP.getContext(),"选中了"+size+"个条目");
            }
        });


    }
    @Override
    public void initData() {
        if (NetWorkUtils.getAPNType(mActivity) == 0){
            ToastUtil.showToast(APP.getContext(), Constants.NET_ERROR_DESC);
            showFailView();
        }else {
            mActivity.setCallTag(Constants.net_text);
            mPresenter.initData();
        }
    }

    public void detailData(SpeciesBean bean) {

        showContentView();
        View root = null;
        for (int i = 0; i < bean.getResult().size(); i++) {
            SpeciesBean.Result result = bean.getResult().get(i);
//            LogUtil.e("title ---"+result.getTitle());
            final TestBean testBean = new TestBean(TestBean.Title);
            testBean.setId(result.getId());
            testBean.setPid(result.getPid());
            testBean.setShowTitle(result.getTitle());
            testBean.setChildrenList(result.getChildren());
            mData.add(testBean);
            testBean.setPosition(mData.size()-1);
            //获取装载flowLayout的布局
            root = LayoutInflater.from(mActivity).inflate(R.layout.item_species, null);
            TextView tvSpecies = (TextView) root.findViewById(R.id.tv_species);
            tvSpecies.setText(testBean.getShowTitle());
            LinearLayout mLlAddRoot = (LinearLayout) root.findViewById(R.id.ll_add_flowLayout);
            FlowLayout flowLayout = new FlowLayout(mActivity);
            for (int j = 0; j < result.getChildren().size(); j++) {
                SpeciesBean.Children children = result.getChildren().get(j);
                final TestBean testBean2 = new TestBean(TestBean.Content);
                testBean2.setAllowCheck(false);
                testBean2.setId(children.getId());
                testBean2.setPid(children.getPid());
                testBean2.setShowTitle(children.getTitle());
                mData.add(testBean2);
                testBean.setPosition(mData.size()-1);
                testBean2.setChild_children_list(children.getChildren());
                //获取flowLayout装载的布局
                View inflate = LayoutInflater.from(mActivity).inflate(R.layout.item_child_spcies, null);
                //设置tag，为了点击事件寻找position
                inflate.setTag(mData.size()-1);
                TextView tvChildSpecies = (TextView) inflate.findViewById(R.id.tv_test_content);
                tvChildSpecies.setText(testBean2.getShowTitle());
                //flowLayout 开始添加
                flowLayout.addView(inflate);
                //对 flowLayout 添加的每一个布局设置listener
                inflate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.listenClick(v);
                        //如果只剩一个条目时再取消就不刷新

                    }
                });
            }
            //当flowLayout添加完一个种类的布局时，将flowLayout添加到相对布局中
            mLlAddRoot.addView(flowLayout);
            //根布局添加整体的view(包含了标题和 flowLayout的父布局)
            mLlTestRoot.addView(root);
        }



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
