package com.cyxk.wrframelibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cyxk.wrframelibrary.base.APP;
import com.cyxk.wrframelibrary.framework.CallBackListener;

import java.io.ByteArrayOutputStream;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


/**
 * 像图片框架，网络框架最好不要直接使用，否则当你发现原先的框架不能解决你现在的问题时，就会有很大的麻烦！
 * test
 */
public class ImageUtils {

    /**
     * 重载可以传context
     * @param context
     * @param url
     * @param iv
     * @param tag
     */
    public static void newGlideLoader(Context context, Object url, ImageView iv, int tag) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                ;
        RequestManager requestManager = getRequestManager(context);
        if (0 == tag) {
            requestManager
                    .load(url)
                    .apply(options)
                    .apply(bitmapTransform(new MultiTransformation<>(new CenterCrop())))
                    .into(iv);
        } else if (1 == tag) {
            requestManager
                    .load(url)
                    .into(iv);
        } else if (3 == tag) {
            requestManager
                    .load(url)
                    .apply(options)
                    .into(iv);
        }
    }


    /**
     * 禁止缓存
     *
     * @param url
     * @param mShowPhoto
     */
    public static void loaderNoCache(Context context,final String url, final ImageView mShowPhoto, final CallBackListener listener) {
        final RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                ;
        getRequestManager(context)
                .load(url)
                .apply(options)
                .into(new SimpleTarget<Drawable>() {

                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
//                        LogUtil.e("二维码 resource --"+resource);
                        mShowPhoto.setImageDrawable(resource);
                        listener.onSuccess("");
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        listener.onFailure();
                    }
                });


    }



    /**
     * 禁止缓存
     * @param url
     * @param mShowPhoto
     */
    public static void loaderNoCache(Context context,Object url, final ImageView mShowPhoto) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                ;
        getRequestManager(context)
                .load(url)
                .apply(options)
                .into(mShowPhoto);
    }

    /**
     * 加载bitmap到ImageView
     *
     * @param context
     * @param bitmap
     * @param iv
     */
    public static void glideLoaderBitmap(Context context, Bitmap bitmap, ImageView iv) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, baos);
        byte[] bytes = baos.toByteArray();
        Glide.with(APP.getContext())
                .load(bytes)
                .into(iv);
    }

    public static void clearCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(APP.getContext()).clearDiskCache();
            }
        }).start();
        Glide.get(APP.getContext()).clearMemory();
    }

    private static RequestManager getRequestManager(Context context) {
        RequestManager with = null;
        if (context == null){
            with = Glide.with(APP.getContext());
        }else{
            with = Glide.with(context);
        }

        return with;

    }

}
