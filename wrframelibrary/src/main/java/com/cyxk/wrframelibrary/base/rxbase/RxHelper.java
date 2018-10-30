package com.cyxk.wrframelibrary.base.rxbase;

import com.cyxk.wrframelibrary.base.BaseResult;
import com.cyxk.wrframelibrary.data.api.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wonder on 2018/1/30.
 */

public class RxHelper {
    public static <T> ObservableTransformer<BaseResult<T>, T> transform() {
        return new ObservableTransformer<BaseResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResult<T>> upstream) {
                return upstream.map(new Function<BaseResult<T>, T>() {
                    @Override
                    public T apply(BaseResult<T> tBaseResult) throws Exception {
                        if (tBaseResult.code == 1) {
                            if (tBaseResult.obj != null)
                                return tBaseResult.obj;
                            else if (tBaseResult.data != null)
                                return tBaseResult.data;
                            else {
                                return (T) tBaseResult.msg;
                            }
                        } else {
                            if (tBaseResult.code == -4) {
//                                MarsApplication.saveUser(LocalUser.getInstance());
//                                SPUtils.setSharedBooleanData(MarsApplication.getApplication(), AppConstants.HAS_LOGIN, false);
//                                try{
//                                    Intent intent = new Intent(MarsApplication.getApplication(), LoginActivity.class);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    MarsApplication.getApplication().startActivity(intent);
//                                }catch (Exception e){
//                                    LogUtils.loge(e.toString());
//                                }
                            }
                            throw new ApiException(tBaseResult.msg);
                        }

                    }
                }).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


}
