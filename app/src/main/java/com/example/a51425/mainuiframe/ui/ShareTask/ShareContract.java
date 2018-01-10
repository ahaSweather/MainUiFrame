package com.example.a51425.mainuiframe.ui.ShareTask;


import com.cyxk.wrframelibrary.base.BaseModel;
import com.cyxk.wrframelibrary.base.BasePresenter2;
import com.cyxk.wrframelibrary.base.BaseView;

/**
 * 作者：Created by wr
 * 时间: 2017/11/29 09:45
 */
interface ShareContract {
    interface View extends BaseView {

    }
    interface Model extends BaseModel {

    }
    abstract class Presenter<T, M> extends BasePresenter2<T, M> {


        public Presenter(T fragment, M model) {
            super(fragment, model);
        }
    }
}
