package com.example.a51425.mainuiframe.ui.ShareTask;


import com.cyxk.wrframelibrary.base.BaseModel;
import com.cyxk.wrframelibrary.base.BasePresenter;
import com.cyxk.wrframelibrary.base.BaseView;

/**
 * 作者：Created by wr
 * 时间: 2017/11/29 09:45
 */
interface ShareContract {

    interface View<T> extends BaseView<T> {

    }

    interface Model extends BaseModel {

    }

    abstract class Presenter<T> extends BasePresenter<T> {

    }
}
