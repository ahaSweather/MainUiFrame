package com.example.a51425.mainuiframe.ui.TestTask2;

import com.cyxk.wrframelibrary.net.HttpCallBack;
import com.cyxk.wrframelibrary.net.HttpHelper;
import com.example.a51425.mainuiframe.constant.Constants;

import java.util.HashMap;

/**
 * 作者：Created by wr
 * 时间: 2018/1/9 18:16
 */
class TestLogic2 implements TestContract2.Model{
    public void getData(HttpCallBack callBack) {
        String url = "http://112.126.69.243:9080/app/findTopic";
        HashMap<String, Object> map = new HashMap<>();
        HttpHelper.obtain().get(url, null, "", Constants.net_text, callBack);

    }
}
