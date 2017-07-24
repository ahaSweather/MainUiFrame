package com.example.a51425.mainuiframe.ui.presenter;

import com.example.a51425.mainuiframe.base.BasePresenter;
import com.example.a51425.mainuiframe.bean.HomeFragmentBean;
import com.example.a51425.mainuiframe.ui.adapter.ShareFragmentAdapter;
import com.example.a51425.mainuiframe.ui.fragment.HomeFragment;
import com.example.a51425.mainuiframe.ui.fragment.IView.IHomeFragmentView;
import com.example.a51425.mainuiframe.utils.LogUtil;
import com.example.a51425.mainuiframe.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 51425 on 2017/6/12.
 */

public class HomeFragmentPresenter extends BasePresenter<HomeFragment> {


    public HomeFragmentPresenter(HomeFragment homeFragment) {
        super(homeFragment);

    }

    public void initData(int page){

        LogUtil.e("page____"+page);
        List<HomeFragmentBean> mData = new ArrayList<>();
        if ( page < 4){

            for (int i = 0; i <6; i++) {
                HomeFragmentBean homeFragmentBean = new HomeFragmentBean(HomeFragmentBean.VIEDEO);
                String shareTitle = "呵呵"+i;
                String shareContent = "嘿嘿"+i;
                String shareImageUrl = "";
                String jumpUrl = "";
                if (i%2==0){
                    shareImageUrl = "http://img2.3lian.com/2014/f2/37/d/40.jpg";
                    jumpUrl = "http://blog.csdn.net/ahaSweater/article/details/73171159";
                    homeFragmentBean.setVideo(false);
                }else{
                    shareImageUrl = "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg";
                    jumpUrl = "http://www.tudou.com/l/e3XqM6nAiPU/&iid=11507091/v.swf";
                    homeFragmentBean.setVideo(true);
                }
                homeFragmentBean.setShareTitle(shareTitle);
                homeFragmentBean.setShareContent(shareContent);
                homeFragmentBean.setShareImageUrl(shareImageUrl);
                homeFragmentBean.setJumpUrl(jumpUrl);
                mData.add(homeFragmentBean);

            }

        }
            mContext.dealData(mData);

//        if (mData.size()!=0){
//            //将每次请求下来的新数据添加到原先的集合中
//            mShareAdapter.setData(data.size(),mData); //更新新添加的数据
//            data.addAll(mData);
//            mIHomeFragmentView.showContentView();
//            if (page>1){
//                mShareAdapter.loadMoreComplete();
//            }
//        }else{
//            mShareAdapter.loadMoreEnd();
//        }


    }

}
