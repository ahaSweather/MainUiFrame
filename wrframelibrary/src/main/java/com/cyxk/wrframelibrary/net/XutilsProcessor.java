package com.cyxk.wrframelibrary.net;//package com.sharemall.net;
//
//import android.util.Log;
//
//
//import com.lidroid.xutils.HttpUtils;
//import com.lidroid.xutils.exception.HttpException;
//import com.lidroid.xutils.http.RequestParams;
//import com.lidroid.xutils.http.ResponseInfo;
//import com.lidroid.xutils.http.callback.RequestCallBack;
//import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
//import com.sharemall.APP;
//import com.sharemall.config.Constants;
//import com.sharemall.utils.LogUtil;
//import com.sharemall.utils.ToastUtil;
//
//import java.util.Map;
//import java.util.Set;
//
///**
// * Created by 51425 on 2017/7/2.
// */
//
//public class XutilsProcessor implements IHttpProcessor {
//
//    private final HttpUtils httpUtils;
//
//    public XutilsProcessor(){
////        httpUtils = new HttpUtils(10000);
//        httpUtils = APP.getHttpUtilsInstance();
//    }
//
//    @Override
//    public void post(String url, Map<String, Object> params, final String cacheName, final Object tag, final ICallback callback) {
//        RequestParams requestParams = new RequestParams();
//        Set<Map.Entry<String, Object>> entrySet = params.entrySet();
//        for (Map.Entry<String, Object> entry: entrySet
//             ) {
//            requestParams.addBodyParameter(entry.getKey(),(String) entry.getValue());
//        }
//        httpUtils.send(HttpMethod.POST, url, requestParams, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                LogUtil.e(tag+"__resulet__"+responseInfo.result);
//                callback.Success(responseInfo.result,cacheName);
//            }
//            /**
//             *
//             */
//            @Override
//            public void onFailure(HttpException e, String s) {
//
//                LogUtil.e(Log.getStackTraceString(e)+e.getExceptionCode());
////                callback.onFailure(s);
//                ToastUtil.showToast(APP.getContext(), Constants.NER_ERROR_DESC);
//                callback.Success(null,cacheName);
//            }
//        });
//    }
//
//    @Override
//    public void get(String url, Map<String, Object> params,String cacheName,Object tag, ICallback callback) {
//
//    }
//}
