package com.example.a51425.mainuiframe.ui.TestTask;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyxk.wrframelibrary.utils.Utils;
import com.example.a51425.mainuiframe.R;
import com.example.a51425.mainuiframe.bean.TestBean;

import java.util.List;

/**
 * 作者：Created by wr
 * 时间: 2018/1/10 15:07
 */
class TestAdapter extends BaseMultiItemQuickAdapter<TestBean,BaseViewHolder> {

    private final TestFragment fragment;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     * @param testFragment
     */
    public TestAdapter(List<TestBean> data, TestFragment testFragment) {
        super(data);
        this.fragment = testFragment;
        addItemType(TestBean.Title, R.layout.item_test_title);
        addItemType(TestBean.Content, R.layout.item_test_content);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {
        switch (item.getItemType()){
            case TestBean.Title:
                helper.setText(R.id.tv_test_title,item.getShowTitle());
                break;

            case TestBean.Content:

                TextView mView = helper.getView(R.id.tv_test_content);
                mView.setText(item.getShowTitle());
                if (item.isAllowCheck()){
                    Utils.setBackGroundDrawable(mView,R.drawable.bg_tv_test_checked);
                    mView.setTextColor(this.mContext.getResources().getColor(R.color.blue));
                    fragment.mComplete.setTextColor(mContext.getResources().getColor(R.color.red_ff1851));
                }else{
                    Utils.setBackGroundDrawable(mView,R.drawable.bg_tv_test_unchecked);
                    mView.setTextColor(this.mContext.getResources().getColor(R.color.black3f));
//                    fragment.mComplete.setTextColor(mContext.getResources().getColor(R.color.gray_82));
                }

                break;

        }
    }
}
