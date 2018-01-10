package com.cyxk.wrframelibrary.net;//package com.cyxk.keyuanwuyou.net;
//
//import android.text.TextUtils;
//
//
//import com.cyxk.keyuanwuyou.base.APP;
//import com.cyxk.keyuanwuyou.config.Constants;
//import com.cyxk.keyuanwuyou.utils.GsonUtil;
//import com.cyxk.keyuanwuyou.utils.LogUtil;
//import com.cyxk.keyuanwuyou.utils.SharedPreferanceUtils;
//import com.cyxk.keyuanwuyou.utils.ToastUtil;
//
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//
///**
// * 解析类,注意onFailure的回调时机，与正常情况不相符
// * Created by wr on 2017/7/2.
// */
//
//public abstract class HttpCallBack2<Result> implements ICallback2 {
//    @Override
//    public void Success(final String result, final String cacheName,final boolean successToast) {
//        Success(result,cacheName,successToast,true);
//
//    }
//
//    /**
//     * 请求成功并且 解析的status 为0 的情况下走这里， 代表业务逻辑上真正的成功
//     * @param bean
//     */
//    public abstract void onSuccess(Result bean);
//
//    /**
//     * 如果我们在解析status不为0的情况下仍然想做些处理，那么就重写onResolveError，默认是已经实现的
//     * @param result
//     */
//    public void onResolveStatusError(String result){};
//
//    /**
//     * 当请求服务器获取数据失败，或解析json后结果为null 回调这个方法
//     */
//    public abstract void onJsonError();
//
//    /**
//     * 单点登录强制退出
//     * @param needShow
//     */
//    public abstract void mustExit(String needShow);
//
//
//    /**
//     * 获取bean对应的class
//     * @param object
//     * @return
//     */
//    public static Class<?> analysisClassInfo(Object object) {
//        Type genType = object.getClass().getGenericSuperclass();
//        Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
//        return (Class<?>) params[0];
//    }
//
//    @Override
//    public void Success(final String result, final String cacheName, boolean successToast, boolean errorToast) {
//        if (TextUtils.isEmpty(result)){
//            //走了这里   相当于从服务器请求失败 直接结束解析，弹出网络异常Tosat，并回调onJsonError
//            LogUtil.e("result 为null");
//            onJsonError();
//            ToastUtil.showToast(APP.getContext(), "与服务器建立连接失败");
////            ToastUtil.showToast(APP.getContext(), Constants.NER_ERROR_DESC);
//            return;
//        }
//        Result bean = null;
//        String status = GsonUtil.getFieldValue(result, "status");
//        //先判断status 是为了防止 后端在数据成功返回和失败返回这两种情况下 json格式不一样 导致解析错误
//        if (TextUtils.isEmpty(status)){
//            /**
//             *  如果解析过程中没有status 一样回调onJsonError，这里就需要和后端的哥们配合了，在项目开始前先定义下返回数据的格式
//             *  如果不商量下，那就等着开发过程中遭罪吧
//             *  1. 返回的结果不管有没数据都要给 status
//             *      1.1 status == 0 表示当前的请求成功，其它表示失败
//             *        举个例子：
//             *          1.(签到功能：如果签到成功了，后端返回数据中status 一定要为0 ，不管哪种原因签到失败了status 一定要不为0 )
//             *          2.(登陆功能：如果传过去的用户信息是正确的，那么返回数据中 status 一定要为0 ，剩下不管什么愿因status 一定不要为0）
//             *
//             *  2.desc 请求的描述，一般情况下 成功不展示，失败了展示告诉用户为什么这次请求会失败 ，例如登陆时密码错误
//             */
//            LogUtil.e("status 为null");
//            ToastUtil.showToast(APP.getContext(), "服务器返回数据格式异常");
//            onJsonError();
//            return;
//        }
//        if ("0".equals(status)){
//            Class<?> clz  = analysisClassInfo(this);
//            bean = (Result) GsonUtil.parseJsonToBean(result, clz);
//            if (bean == null){
//                //解析都失败了还展示啥，直接就告诉用户当前网络异常。。。然后找后端吧
//                ToastUtil.showToast(APP.getContext(), "解析失败");
//                onJsonError();
//                return;
//            }else{
//                if (successToast){
//                    String desc = GsonUtil.getFieldValue(result, "desc");
//                    if (!TextUtils.isEmpty(desc)){
//                        ToastUtil.showToast(APP.getContext(), desc);
//                    }
//                }
//
//                //如果解析成功再返回，并根据cacheName进行缓存，如果传入的cacheName 不为null或""就代表要缓存
//                onSuccess(bean);
//                if (!TextUtils.isEmpty(cacheName)){
//                    //证明要缓存
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            SharedPreferanceUtils sharedPreferanceUtils = SharedPreferanceUtils.getSp();
//                            sharedPreferanceUtils.putString(cacheName,result);
//                        }
//                    }).start();
//                }
//            }
//
//        }else{
//            /**
//             *  如果status 不为0走这里， 注意status 只要有值那代表 网络请求是成功的，status 是用来判断业务的
//             *  所以我们要把desc 这个内容传出去，这里只解析这一个字段也是为了防止 status 不为0 的情况 和status 为0 的情况
//             *  后端返回的json 格式不一致，这里只是简单的弹个toast，能应对绝大部分情况
//             *  如果还需要在status 的值来进行其他的操作 可以自己重写 onResolveStatusError 里面是请求下来的原始 json，
//             *  这个方法默认是实现的
//             */
//            String desc = GsonUtil.getFieldValue(result, "desc");
//            //将是否弹出desc描述通过onFailure 传出去交由我们自己来决定，而原始的网络请求回调onFailure
//
//            if ("99".equals(status)){
//                //退出登录
//                if (TextUtils.isEmpty(desc)){
//                    desc = "有人登录您的账号，您需要退出";
//                }
//                mustExit(desc);
//            }else{
//                if (TextUtils.isEmpty(desc)){
//                    desc = "";
//                }
//                onFailure(desc);
//            }
//            onResolveStatusError(result);
//
//        }
//    }
//}
