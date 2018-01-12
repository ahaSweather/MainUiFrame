package com.example.a51425.mainuiframe.ui.TestTask2;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyxk.wrframelibrary.net.HttpCallBack;
import com.cyxk.wrframelibrary.utils.LogUtil;
import com.cyxk.wrframelibrary.utils.ToastUtil;
import com.cyxk.wrframelibrary.utils.Utils;
import com.example.a51425.mainuiframe.APP;
import com.example.a51425.mainuiframe.R;
import com.example.a51425.mainuiframe.bean.SpeciesBean;
import com.example.a51425.mainuiframe.bean.TestBean;

/**
 * Created by 51425 on 2017/6/12.
 */

public class TestFragmentPresenter2 extends TestContract2.Presenter<TestFragment2, TestLogic2> {


    public TestFragmentPresenter2(TestFragment2 fragment, TestLogic2 model) {
        super(fragment, model);
    }

    @Override
    public void initData() {
        mModel.getData(new HttpCallBack<SpeciesBean>() {
            @Override
            public void onSuccess(SpeciesBean bean) {
                context.detailData(bean);

            }

            @Override
            public void onJsonError() {

            }

            @Override
            public void mustExit(String needShow) {

            }

            @Override
            public void onFailure(String error) {
                ToastUtil.showToast(APP.getContext(), error);

            }
        });

    }

    public void listenClick(View v) {
        int tag = (int) v.getTag();
        TestBean testBeanEnd = context.mData.get(tag);
        if (testBeanEnd.getItemType() == TestBean.Title) {
            return;
        }
        TestBean testBean2 = null;
        for (int i = 0; i < context.mData.size(); i++) {
            testBean2 = context.mData.get(i);
            //如果选中一个条目
            if (i == tag) {
                boolean allowCheck = testBean2.isAllowCheck();
                //判断当前的条目是否已被选中
                if (allowCheck) {
                    //选中了再判断当前总共有几个已经选中的
                    if (context.mAlreadyCheck.size() == 1) {
                        ToastUtil.showToast(APP.getContext(), "当前只剩一个条目时不能取消");
                        break;
                    } else {
                        //如果多余一个
                        context.mAlreadyCheck.remove(tag);
                        testBean2.setAllowCheck(false);
                    }
                } else {
                    //如果当前条目没有被选中，直接添加
                    testBean2.setAllowCheck(true);
                    context.mAlreadyCheck.put(tag, testBean2);
                }

                //更改选择view的状态
                if (testBean2.isAllowCheck()) {
                    Utils.setBackGroundDrawable(v,R.drawable.bg_tv_test_checked);
                    ImageView ivChildSpecies = (ImageView) v.findViewById(R.id.iv_test_content);
                    ivChildSpecies.setBackgroundColor(context.mActivity.getResources().getColor(R.color.blue));
                } else {
                    Utils.setBackGroundDrawable(v,R.drawable.bg_tv_test_unchecked);
                    ImageView ivChildSpecies = (ImageView) v.findViewById(R.id.iv_test_content);
                    ivChildSpecies.setBackgroundColor(context.mActivity.getResources().getColor(R.color.red_ff1851));
                }
                //更改顶部发布的按钮的状态
                if (context.mAlreadyCheck.size() > 0){
                    context.mComplete.setTextColor(context.mActivity.getResources().getColor(R.color.red_ff1851));
                }else{
                    context.mComplete.setTextColor(context.mActivity.getResources().getColor(R.color.blue));

                }

            }


        }


    }
}
