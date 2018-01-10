package com.cyxk.wrframelibrary.net;

import android.text.TextUtils;


import com.cyxk.wrframelibrary.base.APP;
import com.cyxk.wrframelibrary.utils.GsonUtil;
import com.cyxk.wrframelibrary.utils.LogUtil;
import com.cyxk.wrframelibrary.utils.SharedPreferanceUtils;
import com.cyxk.wrframelibrary.utils.ToastUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 处理同步请求,配合rxjava使用
 * Created by wr on 2017/7/2.
 */

public class HttpCallBack3<T>  {


    /**
     *
     * @param tag
     * @param result
     * @param tClass
     * @param <T>
     * @return
     */
    public<T> T getData(Object tag, String result, Class<T> tClass) {
//

        if (TextUtils.isEmpty(result)){
            LogUtil.e(tag+"result 为null");
            return null;
        }
        String status = GsonUtil.getFieldValue(result, "status");
        if (status == null ){
            LogUtil.e(tag+"status 为null");
            return null;
        }

        T t = GsonUtil.parseJsonToBean(result, tClass);
        return  t;
    }

    /**
     * 正常同步请求的回调
     * @param tag
     * @param cacheName
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T getData2(Object tag, final String cacheName, final String json, Class<T> tClass) {

        if (TextUtils.isEmpty(json)){
            //走了这里   相当于从服务器请求失败 直接结束解析，弹出网络异常Tosat，并回调onJsonError
            ToastUtil.showToast(APP.getContext(), "与服务器建立连接失败");
            LogUtil.e("result 为null");
            return null;
        }
        T bean = null;
        String status = GsonUtil.getFieldValue(json, "status");
        //先判断status 是为了防止 后端在数据成功返回和失败返回这两种情况下 json格式不一样 导致解析错误
        if (TextUtils.isEmpty(status)){
            /**
             *  如果解析过程中没有status 一样回调onJsonError，这里就需要和后端的哥们配合了，在项目开始前先定义下返回数据的格式
             *  如果不商量下，那就等着开发过程中遭罪吧
             *  1. 返回的结果不管有没数据都要给 status
             *      1.1 status == 0 表示当前的请求成功，其它表示失败
             *        举个例子：
             *          1.(签到功能：如果签到成功了，后端返回数据中status 一定要为0 ，不管哪种原因签到失败了status 一定要不为0 )
             *          2.(登陆功能：如果传过去的用户信息是正确的，那么返回数据中 status 一定要为0 ，剩下不管什么愿因status 一定不要为0）
             *
             *  2.desc 请求的描述，一般情况下 成功不展示，失败了展示告诉用户为什么这次请求会失败 ，例如登陆时密码错误
             */
            LogUtil.e("status 为null");
            ToastUtil.showToast(APP.getContext(), "服务器返回数据格式异常");
            return null;
        }

        if ("0".equals(status)){
            Class<?> clz  = analysisClassInfo(this);
            bean = (T) GsonUtil.parseJsonToBean(json, clz);
            if (bean == null){
                //解析都失败了还展示啥，直接就告诉用户当前网络异常。。。然后找后端吧
                LogUtil.e("bean 为null");
                ToastUtil.showToast(APP.getContext(), "解析失败");
                return null;
            }else{
                //如果解析成功再返回，并根据cacheName进行缓存，如果传入的cacheName 不为null或""就代表要缓存
                if (!TextUtils.isEmpty(cacheName)){
                    //证明要缓存
                    SharedPreferanceUtils sharedPreferanceUtils = SharedPreferanceUtils.getSp();
                    sharedPreferanceUtils.putString(cacheName,json);
                }
                //请求成功
                return bean;
            }

        }else{
            /**
             *  如果status 不为0走这里， 注意status 只要有值那代表 网络请求是成功的，status 是用来判断业务的
             *  所以我们要把desc 这个内容传出去，这里只解析这一个字段也是为了防止 status 不为0 的情况 和status 为0 的情况
             *  后端返回的json 格式不一致，这里只是简单的弹个toast，能应对绝大部分情况
             *  如果还需要在status 的值来进行其他的操作 可以自己重写 onResolveStatusError 里面是请求下来的原始 json，
             *  这个方法默认是实现的
             */
            String desc = GsonUtil.getFieldValue(json, "desc");
            //将是否弹出desc描述通过onFailure 传出去交由我们自己来决定，而原始的网络请求回调onFailure
            if (!TextUtils.isEmpty(desc)){
                ToastUtil.showToast(APP.getContext(), desc);
            }
            return null;
        }

    }


    /**
     * 获取bean对应的class
     * @param object
     * @return
     */
    public static Class<?> analysisClassInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }
}
