package com.example.a51425.mainuiframe.ui.TestTask2;


import com.cyxk.wrframelibrary.base.BaseModel;
import com.cyxk.wrframelibrary.base.BasePresenter2;
import com.cyxk.wrframelibrary.base.BaseView;

/**
 * 作者：Created by wr
 * 时间: 2017/11/29 09:45
 */
interface TestContract2 {
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
