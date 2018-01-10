package com.example.a51425.mainuiframe.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyxk.wrframelibrary.utils.ImageUtils;
import com.example.a51425.mainuiframe.R;
import com.example.a51425.mainuiframe.bean.HomeFragmentBean;

import java.util.List;

/**
 * Created by 51425 on 2017/6/14.
 */
public class ShareFragmentAdapter extends BaseMultiItemQuickAdapter<HomeFragmentBean,BaseViewHolder> {


    public ShareFragmentAdapter(List shareList) {
        super(shareList);

        addItemType(HomeFragmentBean.VIEDEO, R.layout.item_shar_fragment_first_item);
        addItemType(HomeFragmentBean.SECOND, R.layout.item_shar_fragment_second_item);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, HomeFragmentBean item) {

        switch (item.getItemType()){
            case HomeFragmentBean.VIEDEO:

                ImageUtils.loaderNoCache(mContext,item.getShareImageUrl(), (ImageView) viewHolder.getView(R.id.iv_video_bottom));
                ImageView view = viewHolder.getView(R.id.iv_video_top);
                if (item.isVideo()){
                    view.setVisibility(View.VISIBLE);
                }else{
                    view.setVisibility(View.GONE);
                }

                break;

            case HomeFragmentBean.SECOND:

                break;

        }
    }
}
